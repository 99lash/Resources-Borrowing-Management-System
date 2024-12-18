import java.util.Scanner;

import custom.utils.Clrscr;
import custom.utils.Getch;
import custom.utils.Title;

public class Main {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    AppController app = new AppController();
    boolean login = true;
    while (login) {
      try {
        new Clrscr();
        new Title();
        System.out.println("[1] Sign in");
        System.out.println("[2] Exit");
        System.out.print("Select: ");
        int choice = in.nextInt();
        in.nextLine();

        if (choice == 1) {
          while (true) {
            new Clrscr();
            new Title();
            System.out.print("username: ");
            String username = in.nextLine();
            char[] password = System.console().readPassword("password: ");
            // in.nextLine();
            User account = app.authenticate(username, password);

            if (account != null) {
              String role = account.getRole();
              app.dashboard(username, role);
              break;
            } else {
              System.out.println("Invalid credentials!");
              new Getch();
              break;
            }
          }
        } else if (choice == 2) {
          login = false;
          System.out.println("Exited.");
          break;
        }
      } catch (Exception e) {
        in.nextLine();
      }
    }
  }
}

/*
 * 
 * 
 * GAGAWIN BUKAS:
 * 1. TryCatch,
 * 2. Display Borrower List [Next, Previous],
 * 3. Hide password input sa Manage Account
 * 
 * TODO LIST: [6/11]
 * PROJECT PROGRESS: 8/11 = 72%
 * 
 * !! Major Features (Needed ASAP) [6/8]
 * ✅ Borrow an Item
 * ✅ Return an Item
 * ✅ Borrower Log
 * ✅ Borrower List
 * 🖕 Audit Log
 * ✅ Manage Student Master list
 * ✅ Manage Inventory
 * ✅ Manage Account
 * 
 * !! Nothing major but needed [1/3]
 * ✅ change the role of default admin to super admin
 * 🖕 Hide password input
 * 🟠 Try catch for input (para hindi mag exit and program)
 * 
 * 
 * LEGEND:
 * 🖕TODO
 * 🟠IN PROGRESS
 * ✅DONE
 * 
 * 
 * 
 */