package com;

import java.io.*;
import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Counter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	AtomicInteger counter = new AtomicInteger(0);
	Connection connection = ConnectionFactory.getConnection();
	PrintWriter out;

	@Override
	public void init(ServletConfig config) throws ServletException {
		Integer value = 0;
		super.init(config);
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("select * from emp");
			while (rs.next()) {
				value = rs.getInt("counter");
			}
			counter = new AtomicInteger(value);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		out = response.getWriter();
		counter.addAndGet(1);
		out.print(counter);
	}

	@Override
	public void destroy() {
		try {
			Statement st = connection.createStatement();
			st.executeUpdate("update counter_value set counter = counter.get()");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		super.destroy();
	}

}
