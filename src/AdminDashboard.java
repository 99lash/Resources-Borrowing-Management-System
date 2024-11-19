import java.util.Scanner;

public class AdminDashboard extends Dashboard {

  AdminDashboard() {
    super.title = "Resources Borrowing Management System";
  }

  public void displayDashboard() {
    Scanner in = new Scanner(System.in);
    boolean running = true;
    while (running) {

      System.out.println("----------------------------------------------------");
      System.out.println("\t" + super.title);
      System.out.println("----------------------------------------------------");
      System.out.println("1.Borrow an Item");
      System.out.println("2.Return an Item");
      System.out.println("3.View Borrower Log");
      System.out.println("4.View Audit Log");
      System.out.println("5.Admin Panel");
      System.out.println("6.Log out");
      System.out.print("Select: ");
      int ch = in.nextInt();
      switch (ch) {
        case 1:

          break;

        case 6:
          running = !running;
          break;

        default:
          break;
      }
    }
  }
}
