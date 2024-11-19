import java.util.Scanner;

public class StaffDashboard extends Dashboard {

  StaffDashboard() {
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
      System.out.println("5.Log out");
      System.out.print("Select: ");
      int ch = in.nextInt();
      
      switch (ch) {
        case 1:

          break;

        case 5:
          running = !running;
          break;

        default:
          break;
      }
    }
  }
}
