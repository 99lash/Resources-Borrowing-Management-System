import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        // dashboard("staff");
    }

    public static void dashboard(String role) {
        Scanner in = new Scanner(System.in);
        boolean isRunning = true;
        if (role.equalsIgnoreCase("admin")) {
            while (isRunning) {
                System.out.println("----------------------------------------------------");
                System.out.println("\tResources Borrowing Management System");
                System.out.println("----------------------------------------------------");

                System.out.println("1.Borrow an Item");
                System.out.println("2.Return an Item");
                System.out.println("3.View Borrower Log");
                System.out.println("4.View Audit Log");
                System.out.println("5.Admin Panel");
                System.out.println("6.Log out");
                System.out.print("Select: ");
                int ch = in.nextInt();                
            }
        } else if (role.equalsIgnoreCase("staff")) {
            while (isRunning) {
                System.out.println("----------------------------------------------------");
                System.out.println("\tResources Borrowing Management System");
                System.out.println("----------------------------------------------------");

                System.out.println("1.Borrow an Item");
                System.out.println("2.Return an Item");
                System.out.println("3.View Borrower Log");
                System.out.println("4.View Audit Log");
                System.out.println("5.Log out");
                System.out.print("Select: ");
                int ch = in.nextInt();
            }
        }
    }
}
