import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import custom.utils.Clrscr;
import custom.utils.Getch;
import custom.utils.Title;

public class AppController {
  private List<BorrowTransaction> transactions;
  private List<AuditLog> auditLogs;
  private AccountController accountController;
  private StudentController studentController;
  private InventoryController inventoryController;
  private User currentUser;
  static Scanner in = new Scanner(System.in);

  AppController() {
    this.transactions = new ArrayList<>();
    this.auditLogs = new ArrayList<>();
    this.accountController = new AccountController();
    this.studentController = new StudentController();
    this.inventoryController = new InventoryController();
  }

  // SETTERS
  public void setTransactions(List<BorrowTransaction> transactions) {
    this.transactions = transactions;
  }

  public void setStudentController(StudentController studentController) {
    this.studentController = studentController;
  }

  public void setAccountController(AccountController accountController) {
    this.accountController = accountController;
  }

  public void setAuditLogs(List<AuditLog> auditLogs) {
    this.auditLogs = auditLogs;
  }

  public void setInventoryController(InventoryController inventoryManager) {
    this.inventoryController = inventoryManager;
  }

  public void setCurrentUser(User currentUser) {
    this.currentUser = currentUser;
  }

  // GETTERS
  public List<BorrowTransaction> getTransactions() {
    return transactions;
  }

  public StudentController getStudentController() {
    return studentController;
  }

  public AccountController getAccountController() {
    return accountController;
  }

  public List<AuditLog> getAuditLogs() {
    return auditLogs;
  }

  public InventoryController getInventoryController() {
    return inventoryController;
  }

  public User getCurrentUser() {
    return currentUser;
  }

  // manager methods
  public void borrowResource(String studentId, int resourceId, int quantity) {

  }

  public void returnResource(int transactionId) {

  }

  public List<BorrowTransaction> viewBorrowerLog() {
    return new ArrayList<>();
  }

  public List<AuditLog> viewAuditLog() {
    return new ArrayList<>();
  }

  // administrative functions
  public void createAccount(User account) {

  }

  public void updateAccount(int accountId, User newDetails) {

  }

  public void deleteAccount(int accountId) {

  }

  public void manageInventory(Resource resource, String operation) {

  }

  public void manageStudent(Student student, String operation) {

  }

  // login methods
  public User authenticate(String username, String password) {
    fetchAccountsDatabase();
    List<User> accounts = accountController.getAccounts();
    for (User account : accounts) {
      if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
        setCurrentUser(account);
        accounts.clear();
        return account;
      }
    }
    accountController.clearAccounts();
    return null;
  }

  // fetch methods

  public void fetchAccountsDatabase() {
    String filepath = "../res/data/account/users.csv";

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
          accountController.addAccount(new User(id, username, password, role));
        } else
          header = false;
      }
      reader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void updateAccountDatabase(List<User> accounts) {
    String origFilePath = "../res/data/account/users.csv";
    String tempFilePath = "../res/data/account/temp.csv";

    try {
      Scanner reader = new Scanner(new File(origFilePath));
      PrintWriter writer = new PrintWriter(new File(tempFilePath));
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

    File originalFile = new File(origFilePath);
    File updatedFile = new File(tempFilePath);

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

  public void dashboard(String username, String role) {
    new Clrscr();
    System.out.println("Welcome back, " + username + "!");
    if (role.equalsIgnoreCase("admin")) {
      adminDashboard(username);
    } else if (role.equalsIgnoreCase("staff")) {
      staffDashboard(username);
    } else {
      new Clrscr();
      System.out.println(
          "Hello, " + username + "! Your role is invalid.\nPlease reach out to your administrator to fix your role.");
      new Getch();
    }
  }

  public void adminDashboard(String username) {
    boolean running = true;
    while (running) {
      try {
        new Clrscr();
        System.out.println("Welcome back, " + username + "!");
        new Title();
        System.out.println("1.Borrow an Item");
        System.out.println("2.Return an Item");
        System.out.println("3.View Borrower Log");
        System.out.println("4.View Audit Log");
        System.out.println("5.Admin Operations");
        System.out.println("6.Log out");
        System.out.print("Select: ");
        int ch = in.nextInt();

        switch (ch) {
          case 1:
            borrowingForm();
            break;
          case 2:
            ReturningForm();
            break;
          case 3:
            break;
          case 4:
            break;
          case 5:
            administrativeOperations();
            break;
          case 6:
            running = false;
            break;
          default:
            System.out.println("Invalid choice. Please try again.");
            new Getch();
            break;
        }
      } catch (InputMismatchException e) {
        System.out.println("\nInvalid Input! Use Integer only");
        new Getch();
        in.nextLine();
      } catch (Exception e) {
        System.out.println("An error occurred: " + e.getMessage());
      }
    }
  }

  public void administrativeOperations() {
    while (true) {
      try {
        new Clrscr();
        System.out.println("----------------------------");
        System.out.println("Administrative Panel");
        System.out.println("----------------------------\n");
        System.out.println("1. Manage Accounts");
        System.out.println("2. Manage Students Masterlist");
        System.out.println("3. Manage Inventory");
        System.out.println("4. Back to Dashboard");
        System.out.print("Select: ");

        int ch = in.nextInt();
        in.nextLine();

        switch (ch) {
          case 1:
            ManageAccounts();
            break;
          case 2:

            break;
          case 3:

            break;
          case 4:
            return;
          default:
            System.out.println("Invalid input! Please select between 1-4.");
            new Getch();
            break;
        }
      } catch (InputMismatchException e) {
        System.out.println("\nInvalid Input! Use Integer only");
        new Getch();
        in.nextLine();
      } catch (Exception e) {
        System.out.println("An unexpected error occurred: " + e.getMessage());
      }
    }
  }

  public void ManageAccounts() {
    boolean show = true;

    while (true) {
      try {
        String s = show ? " Hide " : " Show ";
        Boolean success = false;
        new Clrscr();
        System.out.println("---------------------------------------");
        System.out.println("Administrative Panel -> Manage Accounts");
        System.out.println("---------------------------------------\n");

        if (show) {
          fetchAccountsDatabase();
          accountController.displayAllAccounts();
          accountController.clearAccounts();
        } else {
          System.out.println("List of Accounts Detail (Hidden)");
        }

        System.out.println("1. Create an Account");
        System.out.println("2. Update an Account");
        System.out.println("3. Search an Account");
        System.out.println("4. Delete an Account");
        System.out.println("5." + s + "list of accounts");
        System.out.println("6. Back to Administrative Panel");
        System.out.print("Select: ");

        int ch = in.nextInt();

        switch (ch) {
          case 1:
            fetchAccountsDatabase();
            success = accountController.createAccount();
            if (success) {
              updateAccountDatabase(accountController.getAccounts());
            }
            accountController.clearAccounts();
            new Getch();
            break;

          case 2:
            fetchAccountsDatabase();
            success = accountController.updateAccount();
            if (success) {
              updateAccountDatabase(accountController.getAccounts());
            }
            accountController.clearAccounts();
            new Getch();
            break;

          case 3:
            fetchAccountsDatabase();
            success = accountController.searchAccount();
            accountController.clearAccounts();
            new Getch();
            break;

          case 4:
            fetchAccountsDatabase();
            success = accountController.deleteAccount(getCurrentUser());
            if (success == null) {
              updateAccountDatabase(accountController.getAccounts());
              accountController.clearAccounts();
              new Getch();
              new Main().main(null);
            } else if (success) {
              updateAccountDatabase(accountController.getAccounts());
            }
            accountController.clearAccounts();
            new Getch();
            break;

          case 5:
            show = !show;
            break;

          case 6:
            return;

          default:
            System.out.println("Invalid input! Please select between 1-6.");
            new Getch();
            break;
        }
      } catch (InputMismatchException e) {
        System.out.println("\nInvalid Input! Use Integer only");
        new Getch();
        in.nextLine(); // Clear invalid input
      } catch (Exception e) {
        System.out.println("An unexpected error occurred: " + e.getMessage());
      }
    }
  }

  public void staffDashboard(String username) {
    boolean running = true;

    while (running) {
      new Clrscr();
      new Title();
      System.out.println("1.Borrow an Item");
      System.out.println("2.Return an Item");
      System.out.println("3.View Borrower Log");
      System.out.println("4.View Audit Log");
      System.out.println("5.View Inventory");
      System.out.println("6.Log out");
      System.out.print("Select: ");
      int ch = in.nextInt();

      switch (ch) {

        case 1:
          borrowingForm();
          break;

        case 2:
          ReturningForm();
          break;

        case 6:
          running = false;
          break;

        default:
          break;
      }
    }

  }

  public Boolean SubmitVerification(String studentId, String collateral, String borrowed) {
    studentController.fetchStudentDatabase();
    Map<Integer, Student> students = studentController.getStudents();
    Student studentFound = null;
    char ch = ' ';
    boolean recordFound = false;
    while (ch != 'Y' || ch != 'N') {
      new Clrscr();
      System.out.println("Review Details");
      System.out.println("-------------------------------");

      for (Student student : students.values()) {
        if (student.getStudentId().equalsIgnoreCase(studentId)) {
          recordFound = true;
          studentFound = student;
        }
      }

      if (recordFound) {
        System.out.println("Borrowed Resource: " + borrowed);
        System.out.println("Student ID: " + studentId);
        System.out.println("Full Name: " + studentFound.getFullName());
        System.out.println("Course/Year/Section: " + studentFound.getCourse() + " - " + studentFound.getYear()
            + studentFound.getSection());
        System.out.println("Department: " + studentFound.getDepartment());
        System.out.println("Collateral: " + collateral);
        System.out.print("Submit Verification [Y/N]: ");
        ch = in.nextLine().toUpperCase().charAt(0);

        if (ch == 'Y') {
          return true;
        } else if (ch == 'N') {
          return false;
        }
      } else {
        System.out.println("No Record Found.");
        new Getch();
        break;
      }
    }
    return null;
  }

  public void ComputerForm() {
    System.out.print("Select Computer Lab (1-3): ");
    int destination = in.nextInt();
    inventoryController.initializeComputerList(destination);
    List<Computer> computers = inventoryController.getComputers();

    System.out.println("\n-------------------------------------------");
    System.out.print("Select a Computer: ");
    String pc = "PC" + String.valueOf(in.nextInt());
    in.nextLine();

    for (Computer computer : computers) {
      if (computer.getName().equals(pc) && !computer.getStatus().equalsIgnoreCase("available")) {
        System.out.println("PC is not available at the moment.");
        new Getch();
        break;
      } else if (computer.getName().equals(pc) && computer.getStatus().equalsIgnoreCase("available")) {
        System.out.print("Enter Student ID: ");
        String studentId = in.nextLine();
        System.out.print("Enter Collateral: ");
        String collateral = in.nextLine();
        Boolean transactionProccessed = SubmitVerification(studentId, collateral, pc);
        if (transactionProccessed == null) {
          continue;
        } else if (transactionProccessed) {
          inventoryController.borrowComputer(computer);
          inventoryController.updateComputerInventory(computers);
          // BorrowingTransactionController
          // !LEAVE POINT: Continue where I left of...
        } else {
          System.out.println("Borrow Transaction Cancelled.");
        }
        new Getch();
        break;
      }
    }
    computers.clear();
  }

  private void borrowingForm() {
    System.out.println("--Borrowing Form--");
    boolean running = true;

    while (running) {
      try {
        new Clrscr();
        new Title();
        System.out.println("Type of Resources");
        System.out.println("1. PC");
        System.out.println("2. Laptop");
        System.out.println("3. Other Equipments");
        System.out.println("4. Cancel");
        System.out.print("Select: ");

        int ch = in.nextInt();

        switch (ch) {
          case 1:
            ComputerForm();
            running = false;
            break;
          case 2:
            running = false;
            System.out.println("Laptop");
            break;
          case 3:
            running = false;
            System.out.println("Other Equipments");
            break;
          case 4:
            return;
          default:
            System.out.println("Invalid choice. Please select a valid option.");
            new Getch();
            break;
        }
      } catch (InputMismatchException e) {
        System.out.println("\nInvalid Input! Use Integer only");
        new Getch();
        in.nextLine(); // Clear the invalid input from the scanner buffer
      } catch (Exception e) {
        System.out.println("An unexpected error occurred: " + e.getMessage());
      }
    }
  }

  private void ReturningForm() {
    System.out.println("--Returning Form--");
  }
}

/*
 * 7. **ResourceManager**
 * - Attributes:
 * - transactions: List<BorrowTransaction>
 * - students: List<Student>
 * - accounts: List<Account>
 * - auditLogs: List<AuditLog>
 * - inventoryManager: InventoryManager
 * -
 * - Methods:
 * - Borrowing and Returning:
 * - borrowResource(studentId: String, resourceId: int, quantity: int): void
 * - returnResource(transactionId: int): void
 * - Logs:
 * - viewBorrowerLog(filterBy: String, sortBy: String, ascending: boolean):
 * List<BorrowTransaction>b
 * - viewAuditLog(): List<AuditLog>
 * - Administrative Functions:
 * - createAccount(account: Account): void
 * - updateAccount(accountId: int, newDetails: Account): void
 * - deleteAccount(accountId: int): void
 * - manageStudent(student: Student, operation: String): void
 */