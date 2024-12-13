
import java.util.List;
import java.util.Scanner;

import custom.utils.Clrscr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
    String role = "";
    boolean roleValid = role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("staff");
    while (!roleValid) {
      System.out.print("Enter role: ");
      role = in.nextLine();
      roleValid = role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("staff");
    }

    User account = searchAccount(username, role);
    if (account == null) {
      accounts.add(new User(username, password, role)); // TODO: shoud use polymorphism ex. new Admin(); new Staff();
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
      System.out.println("[1] Username");
      System.out.println("[2] Password");
      System.out.println("[3] Role");
      System.out.println("[4] Cancel");
      System.out.print("Select account information to update: ");
      int ch = in.nextInt();
      in.nextLine();

      switch (ch) {
        case 1:
          System.out.print("Enter new username: ");
          String newUsername = in.nextLine();
          if (!account.getUsername().equals(newUsername)) {
            account.setUsername(newUsername);
            return true;
          }
          System.out.println("You cannot replace your old username with the same username. ");
          return false;

        case 2:
          System.out.print("Enter new password: ");
          String newPassword = in.nextLine();
          if (!account.getPassword().equals(newPassword)) {
            account.setPassword(newPassword);
            return true;
          }
          System.out.println("You cannot replace your old password with the same password. ");
          return false;

        case 3:
          System.out.print("Enter new role: ");
          String newRole = in.nextLine();
          if (account.getRole().equals(newRole)) {
            account.setRole(newRole);
            return true;
          }
          System.out.println("You cannot replace your old role with the same role. ");
          return false;

        case 4:
          return false;

        default:
          System.out.println("Invalid input! Please select between 1-4.");
          break;
      }
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

    Boolean confirmation = false;
    while (true) {
      boolean currentUserDeleting = currentUser.getAccountInfo().equals(user.getAccountInfo());
      boolean superAccountDeleting = user.getRole().equalsIgnoreCase("super admin");
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
      } else if (ch == 'N') {
        System.out.println("Account Deleted Unsuccessfully.");
        break;
      }
      new Clrscr();
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

  // fetch methods
  public void fetchAccountsFromDatabase() {
    String filepath = "./res/data/account/users.csv";

    try {
      Scanner reader = new Scanner(new File(filepath));
      boolean header = true;
      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        String[] fields = line.split(",");

        // header = fields[0].equalsIgnoreCase("id") ? true : false;

        if (!header) {
          int id = Integer.parseInt(fields[0]);
          String username = fields[1];
          String password = fields[2];
          String role = fields[3];
          accounts.add(new User(id, username, password, role));
        } else
          header = false;
      }
      reader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void updateAccountsDatabase(List<User> accounts) {
    String origFilepath = "./res/data/account/users.csv";
    String tempFilepath = "./res/data/account/temp.csv";

    try {
      Scanner reader = new Scanner(new File(origFilepath));
      PrintWriter writer = new PrintWriter(new File(tempFilepath));
      boolean header = true;

      while (header) {
        String line = reader.nextLine();
        writer.print(line);
        header = false;
      }
      for (User account : accounts) {
        writer.printf("\n%d,%s,%s,%s", account.getId(), account.getUsername(), account.getPassword(),
            account.getRole());
      }
      reader.close();
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }

    File originalFile = new File(origFilepath);
    File updatedFile = new File(tempFilepath);

    if (originalFile.delete()) {
      if (updatedFile.renameTo(originalFile)) {
        // 200: CSV file updated successfully
        System.out.println("[UPDATE STATUS: 200]");
      } else {
        // 404R: Couldn't rename file
        System.out.println("[UPDATE STATUS: 404R]");
      }
    } else {
      // 404D: Couldn't delete file
      System.out.println("[UPDATE STATUS: 404D]");
    }
  }
}