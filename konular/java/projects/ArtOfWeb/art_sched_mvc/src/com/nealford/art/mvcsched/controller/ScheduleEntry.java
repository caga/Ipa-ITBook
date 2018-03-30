package com.nealford.art.mvcsched.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class ScheduleEntry extends HttpServlet {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws
            ServletException, IOException {
        request.getRequestDispatcher("/ScheduleEntryView.jsp").
                forward(request, response);
    }
}