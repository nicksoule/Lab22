package com.gc.controller;

// Step # 1
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/*
 * author: Nick Soule
 *
 */

@Controller
public class HomeController {

	private Connection getDVConnection() throws ClassNotFoundException, SQLException {
		String url = "jdbc:mysql://localhost:3306/CoffeeShopDB";
		String userName = "root";
		String password = "admin123";

		// Step #2: Load and Register Driver
		Class.forName("com.mysql.jdbc.Driver");

		// Step #3: Create Connection
		Connection con = DriverManager.getConnection(url, userName, password);
		return con;
	}
	
	@RequestMapping("/welcome")
	public ModelAndView registerUser() throws ClassNotFoundException, SQLException {
		Connection con = getDVConnection();
		return new ModelAndView();
		
	}
	
	@RequestMapping("update")
	public ModelAndView registerUser2(@RequestParam("name") String name, @RequestParam("address") String address, @RequestParam("phone") String phone,
			@RequestParam("email") String email) throws ClassNotFoundException, SQLException {
		Connection con = getDVConnection();
		String s = "insert into Users(Name, Address, Phone, Email)" + "values(?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(s);
		ps.setString(1, name);
		ps.setString(2, address);
		ps.setString(3, phone);
		ps.setString(4, email);
		
		ps.execute();
		
		String query = "SELECT * FROM Items";
	
		PreparedStatement st = con.prepareStatement(query);
		ResultSet rs = st.executeQuery();
		ArrayList<String> list = new ArrayList<String>();
		while (rs.next()) {
			String itemName = rs.getString(1);
			String itemDesc = rs.getString(2);
			String itemPrice = rs.getString(3);
			list.add(itemName + "\n" + itemDesc + "\n$" + itemPrice);
		}
		
			con.close();
		
		return new ModelAndView("updatesuccess", "itemz", list);
	}

	
}
