package JDBC_BANKING_APP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class AccountManager {

	Scanner sc = new Scanner(System.in);
	Scanner sc1 = new Scanner(System.in);

	private Connection connection;

	public AccountManager(Connection con) {
		this.connection = con;
	}

	void debit(long account_num) throws Exception {

		System.out.print("Enter amount");
		double Debit_amount = sc.nextDouble();

		System.out.print("Enter pin ");
		String pin = sc1.nextLine();
		try {
			connection.setAutoCommit(false);
			String sQuery = "select * from Account where account_num = ? and pin=? ";
			PreparedStatement pre = connection.prepareStatement(sQuery);
			pre.setLong(1, account_num);
			pre.setString(2, pin);
			ResultSet rSet = pre.executeQuery();

			if (rSet.next()) {
				double current_bal = rSet.getDouble("balance");
				if (Debit_amount <= current_bal) {
					String sQuery2 = "update Account set balance = balance - ? where account_num = ?";
					PreparedStatement pre2 = connection.prepareStatement(sQuery2);
					pre2.setDouble(1, Debit_amount);
					pre2.setLong(2, account_num);
					int rowCount = pre2.executeUpdate();
					if (rowCount > 0) {
						System.out.println(Debit_amount + " amount debited successfully");
						connection.commit();
						connection.setAutoCommit(true);
					} else {
						System.out.println("transaction failed");
						connection.rollback();
						connection.setAutoCommit(true);
					}
				} else {
					System.out.println("you don't have enough money");
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	void credit(long account_num) throws Exception {
		System.out.print("Enter amount");
		double Credit_amount = sc.nextDouble();

		System.out.print("Enter pin ");
		String pin = sc1.nextLine();
		try {
			connection.setAutoCommit(false);
			String sQuery = "select * from Account where account_num = ? and pin=? ";
			PreparedStatement pre = connection.prepareStatement(sQuery);
			pre.setLong(1, account_num);
			pre.setString(2, pin);
			ResultSet rSet = pre.executeQuery();

			if (rSet.next()) {
				String sQuery2 = "update Account set balance = balance + ? where account_num = ?";
				PreparedStatement pre2 = connection.prepareStatement(sQuery2);
				pre2.setDouble(1, Credit_amount);
				pre2.setLong(2, account_num);
				int rowCount = pre2.executeUpdate();
				if (rowCount > 0) {
					System.out.println(Credit_amount + " amount Credited successfully");
					connection.commit();
					connection.setAutoCommit(true);
				} else {
					System.out.println("transaction failed");
					connection.rollback();
					connection.setAutoCommit(true);
				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		connection.setAutoCommit(true);
	}

	void getBalance(long account_num) {
		System.out.print("Enter pin ");
		String pin = sc1.nextLine();

		try {
			String sQuery = "select balance from Account where account_num = ? and pin = ? ";
			PreparedStatement pre = connection.prepareStatement(sQuery);
			pre.setLong(1, account_num);
			pre.setString(2, pin);

			ResultSet rSet = pre.executeQuery();
			if (rSet.next()) {
				double currentBalance = rSet.getDouble("balance");
				System.out.println("Your currentbalance is " + currentBalance);
			} else {
				System.out.println("invalid pin or balance is 0 ");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
