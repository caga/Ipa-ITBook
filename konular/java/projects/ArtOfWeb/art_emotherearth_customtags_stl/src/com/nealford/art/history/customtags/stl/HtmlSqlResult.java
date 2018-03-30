package com.nealford.art.history.customtags.stl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;


public class HtmlSqlResult extends TagSupport {

    private String sql;
    private String dbPool;
    private String formActionDestination;
    private String shoppingForm;

    public HtmlSqlResult() {
    }

    public int doStartTag() throws javax.servlet.jsp.JspException {
        JspWriter jspOut = pageContext.getOut();
        StringBuffer out = new StringBuffer();
        Connection con = null;
        try {
            con = getConnection();
            Statement stmt = con.createStatement();
            stmt.execute(sql);
            ResultSet rs = stmt.getResultSet();
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();

            setupTable(out);
            generateHeaders(out, rsmd, numCols);

            while (rs.next()) {
                generateStandardRow(rs, rsmd, numCols, out);
                generateShoppingForm(out, rs.getInt("id"));
                endRow(out);
            }

            endTable(out);
            jspOut.write(out.toString());
        } catch (SQLException e) {
            out.append("</table><h1>ERROR:</h1> " + e.getMessage());
        } catch (IOException ex) {
            pageContext.getServletContext().log(
                    "Error generating output", ex);
        } finally {
            releaseConnection(con);
        }

        return SKIP_BODY;
    }


    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setDbPool(String dbPool) {
        this.dbPool = dbPool;
    }

    public int doEndTag() throws javax.servlet.jsp.JspException {
        return -1;
    }

    public void setFormActionDestination(String
            formActionDestination) {
        this.formActionDestination = formActionDestination;
    }


    private Connection getConnection() throws SQLException,
            JspException {
        DbPool dbPool = (DbPool) pageContext.getServletContext().
                        getAttribute(this.dbPool);
        Connection c = dbPool.getConnection();
        if (c == null)
            throw new JspException(
                    "Couldn't get connection from application");
        return c;
    }

    private void releaseConnection(Connection con) {
        DbPool dbPool = (DbPool) pageContext.getServletContext().
                        getAttribute(this.dbPool);
        dbPool.release(con);
    }


    private void endTable(StringBuffer out) {
        out.append("</table>\n");
    }

    private void endRow(StringBuffer out) {
        out.append("</tr>\n");
    }

    private void generateShoppingForm(StringBuffer b,
                                      int currentId) {
        if (shoppingForm.equalsIgnoreCase("true")) {
            b.append("<td>");
            b.append("<form action='" + formActionDestination +
                     "' method='post'>");
            b.append("Qty: <input type='text' size='3' " +
                     "name='quantity'>");
            b.append("<input type='hidden' name='id' " + "value='" +
                     currentId + "'>");
            b.append("<input type='submit' name='submit' " +
                     "value='Add to cart'>");
            b.append("</form>");
        }
    }

    private void generateStandardRow(ResultSet rs,
                                     ResultSetMetaData rsmd,
                                     int numCols, StringBuffer out) throws
            SQLException {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        out.append("<tr>");
        for (int i = 1; i <= numCols; i++) {
            Object obj = rs.getObject(i);
            if ((obj != null) &&
                (rsmd.getColumnType(i) == java.sql.Types.DOUBLE))
                out.append("<td align='right'> " +
                           formatter.format(rs.getDouble(i)));
            else if (obj == null)
                out.append("<td>&nbsp;");
            else
                out.append("<td>" + obj.toString());
            out.append("</td>");
        }
    }

    private void generateHeaders(StringBuffer out,
                                 ResultSetMetaData rsmd,
                                 int numcols) throws SQLException {
        for (int i = 1; i <= numcols; i++) {
            out.append("<th>");
            out.append(rsmd.getColumnLabel(i));
        }

        if (shoppingForm.equalsIgnoreCase("true"))
            out.append("<th>" + "Buy");

        out.append("</tr>\n");
    }

    private void setupTable(StringBuffer out) {
        out.append("<table border=1>\n");
        out.append("<tr>");
    }


    public void setShoppingForm(String value) {
        shoppingForm = value;
    }

}