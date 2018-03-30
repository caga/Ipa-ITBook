package com.nealford.art.history.servletemotherearth;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.nealford.art.history.servletemotherearth.lib.DbPool;
import com.nealford.art.history.servletemotherearth.lib.HtmlSQLResult;

public class Catalog extends HttpServlet {
    static final private String CONTENT_TYPE = "text/html";
    static final private String CONNECTION_POOL_ID = "DbPool";
    private String dbUrl;
    private String driverClass;
    private String user;
    private String password;


    public void init() throws ServletException {
        getPropertiesFromServletContext();
        addPoolToApplication(createConnectionPool());
    }

    private void getPropertiesFromServletContext() {
        ServletContext sc = getServletContext();
        dbUrl = sc.getInitParameter("dbUrl");
        driverClass = sc.getInitParameter("driverClass");
        user = sc.getInitParameter("user");
        password = sc.getInitParameter("password");
    }

    private DbPool createConnectionPool() {
        DbPool p = null;
        try {
            p = new DbPool(driverClass, dbUrl, user, password);
        } catch (SQLException sqlx) {
            getServletContext().log("Connectin Pool Error", sqlx);
        }
        return p;
    }


    private void addPoolToApplication(DbPool dbPool) {
        getServletContext().setAttribute(CONNECTION_POOL_ID, dbPool);
    }


    public void doGet(HttpServletRequest request,
                      HttpServletResponse
                      response) throws ServletException,
            IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse
                       response) throws ServletException,
            IOException {
        PrintWriter out = generatePagePrelude(response);
        String userName = validateUser(request, out);
        DbPool pool = getConnectionPool();
        Connection con = null;
        try {
            con = pool.getConnection();
            handleReturnOrNewUser(out, userName, con);
            out.println("</h3><p>");
            addUserToSession(request, userName);
            displayCatalog(out, con);
            generatePagePostlude(out);
        } catch (SQLException sqlx) {
            getServletContext().log("SQL error", sqlx);
        } finally {
            pool.release(con);
        }
    }

    private void handleReturnOrNewUser(PrintWriter out,
                                       String userName,
                                       Connection con) throws
            SQLException {
        if (isNewUser(con, userName))
            out.println("Welcome back to the store!");
        else {
            addUser(con, userName);
            out.println("Welcome to the store! We'll add you to the user database");
        }
    }

    private void displayCatalog(PrintWriter out, Connection con) {
        HtmlSQLResult output = new HtmlSQLResult(
                "select * from products", con);
        output.setShoppingForm(true);
        out.println("<h1>Products</h1><p>");
        out.println(output.toString());
    }

    private void addUserToSession(HttpServletRequest request,
                                  String userName) {
        HttpSession session = request.getSession(true);
        session.setAttribute("user", userName);
    }

    private void generatePagePostlude(PrintWriter out) {
        out.println("</body></html>");
    }

    private void addUser(Connection c, String userName) throws
            SQLException {
        PreparedStatement psi = c.prepareStatement(
                "insert into users (name) values (?)");
        psi.setString(1, userName);
        psi.executeUpdate();
    }

    private boolean isNewUser(Connection c, String userName) throws
            SQLException {
        PreparedStatement ps = c.prepareStatement(
                "select name from users where name = ?");
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    private DbPool getConnectionPool() {
        DbPool pool = (DbPool) getServletContext().getAttribute(
                CONNECTION_POOL_ID);
        if (pool == null)
            getServletContext().log("Pool cannot be loaded");
        return pool;
    }

    private String validateUser(HttpServletRequest request,
                                PrintWriter out) {
        String userName = request.getParameter("username");
        if (userName.equals("")) {
            out.println(
                    "<h1>Error! You must enter a user name!<p>Use the 'Back' button ");
            out.println(
                    "to return to the previous page and enter a name!</h2>");
        }

        out.println("<h3>Hello, " + userName + ".");
        return userName;
    }

    private PrintWriter generatePagePrelude(HttpServletResponse
            response) throws IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Logon</title></head>");
        out.println("<body>");
        return out;
    }
}