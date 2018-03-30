package com.nealford.art.parameterizedcommands;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class MainController extends HttpServlet {
    private Properties mappings;

    public void init(ServletConfig config) throws
            ServletException {
        super.init(config);
        InputStream is = null;
        try {
            String location =
                    config.getInitParameter("mapping");
            is = getServletContext().getResourceAsStream(
                    location);
            mappings = new Properties();
            mappings.load(is);
        } catch (IOException iox) {
            getServletContext().log("I/O Error", iox);
              iox.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException ignored) {
            }
        }
    }

    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws
            ServletException, IOException {
        String command = request.getParameter("cmd");
        String actionClass = (String) mappings.get(command);
        Action action = null;
        try {
            action =
              (Action) Class.forName(actionClass).newInstance();
        } catch (ClassNotFoundException cnfx) {
            getServletContext().log("Class Not Found", cnfx);
            cnfx.printStackTrace();
        } catch (IllegalAccessException iax) {
            getServletContext().log("Security Exception", iax);
        } catch (InstantiationException ix) {
            getServletContext().log("Instantiation Exception",
                    ix);
        }
        action.setRequest(request);
        action.setResponse(response);
        action.setServletContext(getServletContext());
        action.execute();
    }

    public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws
            ServletException, IOException {
        doGet(request, response);
    }
}