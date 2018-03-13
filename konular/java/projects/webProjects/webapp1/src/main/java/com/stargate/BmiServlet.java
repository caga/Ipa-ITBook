package com.stargate;
import com.stargate.BMI;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
public class BmiServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request,HttpServletResponse response)
	throws ServletException,IOException{
	request.setCharacterEncoding("UTF-8");
	String name=request.getParameter("name");
	String surname=request.getParameter("surname");
	double kilo=Double.parseDouble(request.getParameter("kilo"));
	double boy=Double.parseDouble(request.getParameter("boy"));
	BMI bmi=new BMI(kilo,boy);
	String result=bmi.toString();
	System.out.println("boy: "+boy+" kilo: "+kilo+" result: "+result);
	request.setAttribute("bmi",result);	
	System.out.println(request.getAttributeNames());
	System.out.println(request.getAttribute("bmi"));
	RequestDispatcher view=request.getRequestDispatcher("WEB-INF/result.jsp");	
	view.forward(request,response);
	}
}	
