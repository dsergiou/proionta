package com.demos.web;

import java.sql.*;
import com.demos.model.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class Proionta extends HttpServlet
{
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
    {
        //resp.setContentType("text/html");
        //PrintWriter out = resp.getWriter();
        //out.println("Got product<br>");

	String barcode = req.getParameter("Barcode");
	String name = req.getParameter("name");
    String description = req.getParameter("Description");
	String color = req.getParameter("color");
	
	// Parse barcode into an integer
	int barcodeInt = 0;
	try{
		barcodeInt = Integer.parseInt(barcode);
	}catch(NumberFormatException e)
	{
		// Parse failed, return an error message
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println(String.format("Error: Barcode %s is not a valid integer",barcode));
		return;
	}

	
	String conStr = "jdbc:mysql://localhost:3306/proionta";	
	String user = "root";
	String pass = "D1179S1995";	
	try{
		Class.forName("com.mysql.jdbc.Driver"); 
		Connection conn = DriverManager.getConnection(conStr,user,pass);
		
		// Search if barcode already exists
		String selectQuery = "SELECT Name FROM proionta WHERE Barcode = ?";
		PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
		selectStatement.setInt(1, barcodeInt);

		ResultSet productsResultSet = selectStatement.executeQuery();
		if(productsResultSet.next()){
			// Barcode already exists print error message
			String productName = productsResultSet.getString("Name");
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			out.println(String.format("Error: Barcode %s already exists for product: %s", barcode, productName));
			return;
		}

	    String sql = "INSERT INTO proionta (Barcode, Name, Colour, Description) Values (?, ?, ?, ?);";
	    PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, barcodeInt);
		ps.setString(2,name);
	    ps.setString(3,color);
	    ps.setString(4,description);

	    ps.executeUpdate();
	}catch(Exception e){
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println(e.toString());
		return;
    }
	
	Product product = new Product(barcode, name, description, color);
	req.setAttribute("product",product);

	//out.println("<br> barcode " + c);
	RequestDispatcher view = req.getRequestDispatcher("product.jsp");
	view.forward(req,resp);
    }
}

