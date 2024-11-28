
import java.util.List;
import java.util.Scanner;

import custom.utils.Clrscr;

import java.util.ArrayList;

public class AccountController {
  private List<User> accounts;

  AccountController() {
    accounts = new ArrayList<>();
  }

  // GETTERS
  public List<User> getAccounts() {
    return accounts;
  }

  // SETTERS
  public void setAccounts(List<User> accounts) {
    this.accounts = accounts;
  }

  public void addAccount(User user) {
    accounts.add(user);
  }

  public void clearAccounts() {
    accounts.clear();
  }

  public boolean createAccount() {
    new Clrscr();
    Scanner in = new Scanner(System.in);
    System.out.println("---------------------------------------------------");
    System.out.println("Administrative Panel -> Manage Account (Creating)");
    System.out.println("---------------------------------------------------\n");

    System.out.print("Enter username: ");
    String username = in.nextLine();
    System.out.print("Enter password: ");
    String password = in.nextLine();
    System.out.print("Enter role: ");
    String role = in.nextLine();

    User account = searchAccount(username, role);
    if (account == null) {
      addAccount(new User(username, password, role));
      System.out.println("Account successfully created.");
      return true;
    }
    System.out.println("Account already existing.");
    return false;
  }

  public boolean updateAccount() {
    new Clrscr();
    Scanner in = new Scanner(System.in);
    System.out.println("---------------------------------------------------");
    System.out.println("Administrative Panel -> Manage Account (Updating)");
    System.out.println("---------------------------------------------------\n");

    System.out.print("Enter an account id to update: ");
    int id = in.nextInt();
    in.nextLine();

    User account = searchAccount(id);
    if (account != null) {
      System.out.println("1.Username");
      System.out.println("2.Password");
      System.out.println("3.Role");
      System.out.println("4.Cancel");
      System.out.print("Select account information to update: ");
      int ch = in.nextInt();
      in.nextLine();

      switch (ch) {
        case 1:
          System.out.print("Enter new username: ");
          String newUsername = in.nextLine();
          if (account.getUsername().equals(newUsername)) {
            System.out.println("You cannot replace your old username with the same username. ");
          } else {
            account.setUsername(newUsername);
          }
          break;

        case 2:
          System.out.print("Enter new password: ");
          String newPassword = in.nextLine();
          if (account.getPassword().equals(newPassword)) {
            System.out.println("You cannot replace your old password with the same password. ");
          } else {
            account.setPassword(newPassword);
          }
          break;

        case 3:
          System.out.print("Enter new role: ");
          String newRole = in.nextLine();
          if (account.getRole().equals(newRole)) {
            System.out.println("You cannot replace your old role with the same role. ");
          } else {
            account.setRole(newRole);
          }
          break;

        case 4:
          return false;

        default:
          System.out.println("Invalid input! Please select between 1-4.");
          break;
      }
      System.out.println("Account successfully updated.");
      return true;
    }
    System.out.println("Account does not exist.");
    return false;
  }

  public boolean searchAccount() {
    new Clrscr();
    Scanner in = new Scanner(System.in);
    System.out.println("---------------------------------------------------");
    System.out.println("Administrative Panel -> Manage Account (Searching)");
    System.out.println("---------------------------------------------------\n");

    System.out.print("Enter an account id to search: ");
    int id = in.nextInt();
    in.nextLine();

    User user = searchAccount(id);

    if (user == null) {
      System.out.println("Account does not exist.");
      return false;
    }
    System.out.println(user.getAccountInfo());
    return true;
  }

  public boolean deleteAccount() {
    new Clrscr();
    Scanner in = new Scanner(System.in);
    System.out.println("---------------------------------------------------");
    System.out.println("Administrative Panel -> Manage Account (Deleting)");
    System.out.println("---------------------------------------------------\n");

    System.out.print("Enter an account id to delete: ");
    int id = in.nextInt();

    User user = searchAccount(id);
    if (user == null) {
      System.out.println("Account does not exist.");
      return false;
    }
    System.out.println(user.getAccountInfo());
    // TODO: verification to remove [Y/N]
    accounts.remove(accounts.indexOf(user));
    System.out.println("Account deleted successfully.");
    return true;
  }

  public void displayAllAccounts() {
    System.out.println("List of Accounts Detail (Visible)");
    System.out.println("--------------------------------------------");
    System.out.printf("| %-4s | %-15s | %-15s |\n", "ID", "Username", "Role");
    System.out.println("--------------------------------------------");

    for (User user : accounts) {
      System.out.printf("| %-4d | %-15s | %-15s |\n", user.getId(), user.getUsername(), user.getRole());
      System.out.println("--------------------------------------------");
    }
  }

  private User searchAccount(int id) {
    for (User account : accounts) {
      if (account.getId() == id) {
        return account;
      }
    }
    return null;
  }

  private User searchAccount(String username, String role) {
    for (User account : accounts) {
      if (account.getUsername().equals(username) && account.getRole().equals(role)) {
        return account;
      }
    }
    return null;
  }
}
