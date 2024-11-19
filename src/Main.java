import java.util.Scanner;

public class Main {
    private static LoginController loginController = new LoginController();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while (true) {      
            System.out.println("----------------------------------------------------");
            System.out.println("\tResources Borrowing Management System");
            System.out.println("----------------------------------------------------");
            System.out.println("===== \t\t\tLogin \t\t       =====\n\n\n");
            System.out.print("Enter username: ");
            String username = in.nextLine();

            System.out.print("Enter password: ");
            String password = in.nextLine();
            if (loginController.authenticate(username, password)) {
                System.out.println("Login successful!");
                loginController.initializeDashboard();
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        }
    }
}
