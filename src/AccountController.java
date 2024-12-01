
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
      System.out.println("Account Successfully Created.");
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

  public Boolean deleteAccount(User currentUser) {
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
    // TODO: verification to remove [Y/N]
    Boolean confirmation = false;

    while (true) {
      boolean currentUserDeleting = currentUser.getAccountInfo().equals(user.getAccountInfo());
      boolean superAccountDeleting = user.getId() == 101 && user.getUsername().equals("admin")
          && user.getRole().equals("admin");
      if (superAccountDeleting) {
        System.out.println("ERROR: You can't delete super admin account.");
        return false;
      }
      System.out.println("Review account details to be deleted");
      System.out.println("---------------------------------------------------");
      System.out.println(user.getAccountInfo());
      if (currentUserDeleting) {
        System.out.println("NOTICE: You will be logged out if you deleted your account!");
        System.out.print("Are you sure want to delete your account? (Y/N): ");
      } else {
        System.out.print("Are you sure you want to delete this account? (Y/N): ");
      }
      char ch = in.next().toUpperCase().charAt(0);
      if (ch == 'Y') {
        confirmation = currentUserDeleting ? null : true;
        accounts.remove(accounts.indexOf(user));
        System.out.println("Account Deleted Successfully.");
        break;
      } else {
        System.out.println("Account Deleted Unsuccessfully.");
        break;
      }
    }
    return confirmation;
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
      boolean accountExisting = account.getUsername().equals(username) && account.getRole().equals(role);   
      if (accountExisting) return account;
    }
    return null;
  }
}
