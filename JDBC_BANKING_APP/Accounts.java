package JDBC_BANKING_APP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.util.Scanner;

public class Accounts {

	private Connection connection;

	public Accounts(Connection con) {
		this.connection = con;
	}

	Scanner sc = new Scanner(System.in);
	Scanner sc1 = new Scanner(System.in);

	long open_Account(String Email) throws Exception {
		if (!Account_Exist(Email)) {
			try {
				String sQuery = "insert into Account  values(?,?,?,?,?)";
				PreparedStatement pre = connection.prepareStatement(sQuery);

				long acc = genarate_account_num();
				pre.setLong(1, acc);

				System.out.print("Enter Full Name =");
				String name = sc.nextLine();
				pre.setString(2, name);

				pre.setString(3, Email);

				System.out.print("Enter initial balance =");
				Double bal = sc1.nextDouble();
				pre.setDouble(4, bal);

				System.out.print("Enter 4 digit pin =");
				String pin = sc.nextLine();
				pre.setString(5, pin);

				int count = pre.executeUpdate();
				if (count > 0) {
					return acc;
				} else {
					throw new RuntimeException("account creation failed");
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		throw new RuntimeException("account already avelable");

	}

	long get_Account(String Email) throws Exception {
		try {
			String sQuery = "select * from Account where email = ? ";
			PreparedStatement pre = connection.prepareStatement(sQuery);
			pre.setString(1, Email);
			ResultSet rSet = pre.executeQuery();
			if (rSet.next()) {
				Long account = rSet.getLong("account_num");
				return account;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		throw new RuntimeException("account does't exsist");
	}

	
	long genarate_account_num()  throws Exception{
		Random random = new Random();

		int min = 10000000;
		int max = 99999999;

		int eightDigitNumber = random.nextInt(max - min + 1) + min;

		return eightDigitNumber;

	}

	boolean Account_Exist(String Email) throws Exception {
		try {
			String sqlQuert = "select * from Account where email = ? ";
			PreparedStatement pre = connection.prepareStatement(sqlQuert);
			pre.setString(1, Email);
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
