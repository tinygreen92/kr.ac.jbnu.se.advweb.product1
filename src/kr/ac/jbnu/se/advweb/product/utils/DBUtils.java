package kr.ac.jbnu.se.advweb.product.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.ac.jbnu.se.advweb.product.model.Product;
import kr.ac.jbnu.se.advweb.product.model.UserAccount;

public class DBUtils {

	public static UserAccount findUser(Connection conn, //
			String userName, String password) throws SQLException {

		String sql = "Select a.User_Name, a.Password, a.Gender from User_Account a " //
				+ " where a.User_Name = ? and a.password= ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		pstm.setString(2, password);
		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			String gender = rs.getString("Gender");
			UserAccount user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			user.setGender(gender);
			return user;
		}
		return null;
	}
	
	public static UserAccount recoveringID(Connection conn, //
			String gender, String password) throws SQLException {
		
		String sql = "Select a.User_Name, a.Password, a.Gender from User_Account a " //
				+ " where a.Gender = ? and a.password= ?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, gender);
		pstm.setString(2, password);
		ResultSet rs = pstm.executeQuery();
		
		if (rs.next()) {
			String userName = rs.getString("User_Name");
			UserAccount user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			user.setGender(gender);
			return user;
		}
		return null;
	}
	
	public static UserAccount recoveringPW(Connection conn, //
			String gender, String username) throws SQLException {
		
		String sql = "Select a.User_Name, a.Password, a.Gender from User_Account a " //
				+ " where a.Gender = ? and a.User_Name= ?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, gender);
		pstm.setString(2, username);
		ResultSet rs = pstm.executeQuery();
		
		if (rs.next()) {
			String password = rs.getString("password");
			UserAccount user = new UserAccount();
			user.setUserName(username);
			user.setPassword(password);
			user.setGender(gender);
			return user;
		}
		return null;
	}	

	public static UserAccount findUser(Connection conn, String userName) throws SQLException {

		String sql = "Select a.User_Name, a.Password, a.Gender from User_Account a "//
				+ " where a.User_Name = ? ";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);

		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			String password = rs.getString("Password");
			String gender = rs.getString("Gender");
			UserAccount user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			user.setGender(gender);
			return user;
		}
		return null;
	}

	public static List<Product> queryProduct(Connection conn) throws SQLException {
		String sql = "Select a.Code, a.Name, a.Price from Product a ";

		PreparedStatement pstm = conn.prepareStatement(sql);

		ResultSet rs = pstm.executeQuery();
		List<Product> list = new ArrayList<Product>();
		while (rs.next()) {
			String code = rs.getString("Code");
			String name = rs.getString("Name");
			float price = rs.getFloat("Price");
			Product product = new Product();
			product.setCode(code);
			product.setName(name);
			product.setPrice(price);
			list.add(product);
		}
		return list;
	}

	public static Product findProduct(Connection conn, String code) throws SQLException {
		String sql = "Select a.Code, a.Name, a.Price from Product a where a.Code=?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, code);

		ResultSet rs = pstm.executeQuery();

		while (rs.next()) {
			String name = rs.getString("Name");
			float price = rs.getFloat("Price");
			Product product = new Product(code, name, price);
			return product;
		}
		return null;
	}

	public static void updateProduct(Connection conn, Product product) throws SQLException {
		String sql = "Update Product set Name =?, Price=? where Code=? ";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, product.getName());
		pstm.setFloat(2, product.getPrice());
		pstm.setString(3, product.getCode());
		pstm.executeUpdate();
	}

	public static void insertProduct(Connection conn, Product product) throws SQLException {
		String sql = "Insert into Product(Code, Name,Price) values (?,?,?)";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, product.getCode());
		pstm.setString(2, product.getName());
		pstm.setFloat(3, product.getPrice());

		pstm.executeUpdate();
	}
	
	public static void insertUser(Connection conn, UserAccount useraccount) throws SQLException {
		String sql = "Insert into USER_ACCOUNT(USER_NAME, GENDER,PASSWORD) values (?,?,?)";

		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, useraccount.getUserName());
		pstm.setString(2, "M");
		pstm.setString(3, useraccount.getPassword());

		pstm.executeUpdate();
	}

	public static void deleteProduct(Connection conn, String code) throws SQLException {
		String sql = "Delete From Product where Code= ?";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, code);

		pstm.executeUpdate();
	}
	
	// Check newest created user account will occur PRIMARY KEY Collapse Error.
	public static void checkAccount(Connection conn, UserAccount useraccount) throws SQLException {
		
		String sql = "Select a.User_Name, a.Password, a.Gender from User_Account a " //
				+ " where a.User_Name = ?";
		
		// 회원가입할 때 어떤 값을 입력 받을 지에 따라 추가하여 사용할 수 잇다.
		String username = useraccount.getUserName();
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, username);
	}

}