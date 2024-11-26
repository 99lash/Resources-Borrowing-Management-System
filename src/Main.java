import java.util.Scanner;

import custom.utils.Clrscr;
import custom.utils.Getch;
import custom.utils.Title;

public class Main {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    AppController app = new AppController();
    while (true) {
      new Clrscr();
      new Title();
      System.out.print("username: ");
      String username = in.nextLine();
      System.out.print("password: ");
      String password = in.nextLine();

      Account account = app.authenticate(username, password);

      if (account != null) {
        String role = account.getRole();
        app.dashboard(username, role);
      } else {
        System.out.println("Invalid credentials!");
        new Getch();
      }
    }
  }
}