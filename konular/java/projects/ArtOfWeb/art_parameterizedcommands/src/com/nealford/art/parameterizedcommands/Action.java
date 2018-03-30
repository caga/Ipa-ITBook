package com.nealford.art.parameterizedcommands;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

abstract public class Action {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ServletContext servletContext;

    abstract public void execute();

    public void forward(String forwardResource) {
        try {
            RequestDispatcher rd =
                getRequest().getRequestDispatcher(
                forwardResource);
            rd.forward(getRequest(), getResponse());
        } catch (IOException iox) {
            servletContext.log("Forward Error", iox);
        } catch (ServletException sx) {
            servletContext.log("Servlet Error", sx);
        }
    }

    public void setRequest(HttpServletRequest newRequest) {
        request = newRequest;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setResponse(HttpServletResponse newResponse) {
        response = newResponse;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setServletContext(ServletContext newContext) {
        servletContext = newContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }
}