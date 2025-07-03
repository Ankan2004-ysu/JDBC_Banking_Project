package JDBC_BANKING_APP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class User {

	private Connection connection;
	Scanner sc = new Scanner(System.in);

	public User(Connection con) {
		this.connection = con;
	}

	void register() throws Exception {
		System.out.print("Enter Full Name =");
		String name = sc.nextLine();

		System.out.print("Enter Email =");
		String REmail = sc.nextLine();

		System.out.print(" Enter Password =");
		String password = sc.nextLine();

		if (user_exist(REmail)) {
			System.out.println("User Already Exisis...");
			return;
		}

		try {
			String sqlQuert = "insert into user value(?,?,?)";
			PreparedStatement pre = connection.prepareStatement(sqlQuert);
			pre.setString(1, name);
			pre.setString(2, REmail);
			pre.setString(3, password);

			int count = pre.executeUpdate();

			if (count > 0) {
				System.out.println("User register SuccessFully");
			} else {
				System.err.println("User registration fails !!");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	String login() throws Exception {
		System.out.print("Enter Email =");
		String Email = sc.nextLine();

		System.out.print(" Enter Password =");
		String password = sc.nextLine();

		try {
			String sqlQuert = "select * from user where email = ? and  password = ?";
			PreparedStatement pre = connection.prepareStatement(sqlQuert);
			pre.setString(1, Email);
			pre.setString(2, password);
			ResultSet rSet = pre.executeQuery();

			if (rSet.next()) {
				return Email;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;

	}

	boolean user_exist(String REmail) throws Exception {

		try {
			String sqlQuert = "select * from user where email = ? ";
			PreparedStatement pre = connection.prepareStatement(sqlQuert);
			pre.setString(1, REmail);
			ResultSet rset = pre.executeQuery();
			if (rset.next()) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;

	}

}
