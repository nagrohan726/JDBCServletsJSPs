package com.rohan.user.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class DeleteUserServlet
 */
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) {
		try {
			System.out.println("init()");
			ServletContext context = config.getServletContext();
			Class.forName(context.getInitParameter("driverClass"));
			connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
					context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("doPost()");
		String email = request.getParameter("email");

		try {
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate("delete from user where email='" + email + "'");
			PrintWriter out = response.getWriter();
			if (result > 0) {
				out.print("<h1>User Deleted</h1>");
			} else {
				out.print("<h1>User not found in the database</h1>");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		try {
			System.out.println("destroy()");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
