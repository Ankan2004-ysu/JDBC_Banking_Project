package JDBC_BANKING_APP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingApp {
	static final String password = "Ankan@123Sql";
	static final String user = "root";
	static final String url = "jdbc:mysql://localhost:3306/Banking_System";

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("ATM Machine Booting... Please wait.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			Connection con = DriverManager.getConnection(url, user, password);
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);

			User userObj = new User(con);
			AccountManager acm = new AccountManager(con);
			Accounts acc = new Accounts(con);

			while (true) {
				printBorder();
				System.out.println("|        🏦  WELCOME TO JAVA ATM MACHINE        |");
				printBorder();
				System.out.println("| 1. 📝 Register                                |");
				System.out.println("| 2. 💳 Insert Card (Login)                     |");
				System.out.println("| 3. ❌ Exit                                     |");
				printBorder();
				System.out.print("Select Option: ");
				int choice = sc.nextInt();
				sc.nextLine();

				switch (choice) {
				case 1:
					clearScreen();
					System.out.println("=== New User Registration ===");
					userObj.register();
					break;

				case 2:
					clearScreen();
					System.out.println("=== Please Insert Your Card ===");
					String email = userObj.login();
					if (email != null) {
						System.out.println("✅ Card Accepted. Welcome!");
						System.out.println(" ");
						System.out.println(" ");

						long accNum;
						
						try {
							accNum = acc.get_Account(email);
						
						} catch (Exception e) {
							System.out.println("=== ⚠️  No linked account found ===");
							System.out.println("Creating new account...");
							accNum = acc.open_Account(email);
							System.out.println("✅ Account Created! Your Account No: " + accNum);
							System.out.println(" ");
							System.out.println(" ");
						}
						
						boolean atmSession = true;
						
						while (atmSession) {
							printBorder();
							System.out.println("|         🧾 ATM TRANSACTION MENU              |");
							printBorder();
							System.out.println("| 1. 💰 Check Balance                          |");
							System.out.println("| 2. ➕ Deposit (Credit)                        |");
							System.out.println("| 3. ➖ Withdraw (Debit)                        |");
							System.out.println("| 4. 🔚 Exit ATM                                |");
							printBorder();
							System.out.print("Choose: ");
							int ch = sc.nextInt();
							sc.nextLine();

							switch (ch) {
							case 1:
								clearScreen();
								System.out.println("=== BALANCE ENQUIRY ===");
								acm.getBalance(accNum);
								break;
							case 2:
								clearScreen();
								System.out.println("=== DEPOSIT MONEY ===");
								acm.credit(accNum);
								break;
							case 3:
								clearScreen();
								System.out.println("=== WITHDRAW MONEY ===");
								acm.debit(accNum);
								break;
							case 4:
								atmSession = false;
								clearScreen();
								System.out.println("Card Ejected. Thank you for using Java ATM 💳");
								break;
							default:
								System.out.println("❌ Invalid Option.");
							}
						}
					} else {
						System.out.println("❌ Authentication Failed. Please try again.");
					}
					break;

				case 3:
					System.out.println("👋 Thank you! Visit again.");
					return;

				default:
					System.out.println("❌ Invalid Option. Please try again.");
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static void printBorder() {
		System.out.println("+-----------------------------------------------+");
	}

	static void clearScreen() {
		System.out.println("\n\n");
	}
}
