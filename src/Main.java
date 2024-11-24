import java.io.IOException;
import java.util.Scanner;

public class Main {
	private static LoginController loginController = new LoginController();

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		while (true) {
			Boolean auth = null;

			System.out.println("----------------------------------------------------");
			System.out.println("\tResources Borrowing Management System");
			System.out.println("----------------------------------------------------");
			System.out.println("===== \t\t\tLogin \t\t       =====\n\n\n");

			System.out.print("Enter username: ");
			String username = in.nextLine();
			System.out.print("Enter password: ");
			String password = in.nextLine();
			auth = loginController.authenticate(username, password);

			if (auth) {
				System.out.println("Login successful!");
				loginController.initializeDashboard();
			} else {
				System.out.println("Invalid credentials. Please try again.");
			}
		}
	}

	public void getch() {
		try {
			System.out.print("Press any key to continue.");
			// Read a single byte (character)
			System.in.read();

		} catch (IOException e) {
			System.out.println("Unable to getch.");
		}
	}

	public void clrscr() {
		try {
			if (System.getProperty("os.name").contains("Windows")) {
				// Windows command to clear screen
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				// Unix-based OS command to clear screen
				new ProcessBuilder("clear").inheritIO().start().waitFor();
			}
		} catch (Exception e) {
			System.out.println("Error: Unable to clear screen");
		}
	}
}
