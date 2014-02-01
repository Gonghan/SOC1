package com.gonghan.javawebservice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Request
 */
public class Request extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Request() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WeatherAPIHelper w=new WeatherAPIHelper();
		String from=request.getParameter("fromCity");
		String to=request.getParameter("toCity");
		String date=request.getParameter("date");
		Weather fromW=w.getWeather(request.getParameter("fromCity"));
		Weather toW=w.getWeather(request.getParameter("toCity"));
		FlightAPIHelper f=new FlightAPIHelper();
		Flight flight=f.generateFlight(from,to, date);
		PrintWriter out=response.getWriter();
		out.write("<html><title>Result</title><body>");
		out.write("Weather information:<br/>");
		out.write(fromW.toString());
		out.write(toW.toString());
		out.write("<br/>Flight information:<br/>");
		out.write(flight.toString());
		out.write("</html></body>");
	}

}
