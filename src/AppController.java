import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import custom.utils.Clrscr;
import custom.utils.Getch;
import custom.utils.Title;

public class AppController {
  private List<BorrowTransaction> transactions;
  private List<Student> students;
  private List<Account> accounts;
  private List<AuditLog> auditLogs;
  private InventoryController inventoryController;
  private Account currentUser;
  static Scanner in = new Scanner(System.in);

  AppController() {
    this.transactions = new ArrayList<>();
    this.students = new ArrayList<>();
    this.accounts = new ArrayList<>();
    this.auditLogs = new ArrayList<>();
    this.inventoryController = new InventoryController();
  }

  // SETTERS
  public void setTransactions(List<BorrowTransaction> transactions) {
    this.transactions = transactions;
  }

  public void setStudents(List<Student> students) {
    this.students = students;
  }

  public void setAccounts(List<Account> accounts) {
    this.accounts = accounts;
  }

  public void setAuditLogs(List<AuditLog> auditLogs) {
    this.auditLogs = auditLogs;
  }

  public void setInventoryController(InventoryController inventoryManager) {
    this.inventoryController = inventoryManager;
  }

  public void setCurrentUser(Account currentUser) {
    this.currentUser = currentUser;
  }

  // GETTERS
  public List<BorrowTransaction> getTransactions() {
    return transactions;
  }

  public List<Student> getStudents() {
    return students;
  }

  public List<Account> getAccounts() {
    return accounts;
  }

  public List<AuditLog> getAuditLogs() {
    return auditLogs;
  }

  public InventoryController getInventoryController() {
    return inventoryController;
  }

  public Account getCurrentUser() {
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
  public void createAccount(Account account) {

  }

  public void updateAccount(int accountId, Account newDetails) {

  }

  public void deleteAccount(int accountId) {

  }

  public void manageInventory(Resource resource, String operation) {

  }

  public void manageStudent(Student student, String operation) {

  }

  // login methods
  public Account authenticate(String username, String password) {
    fetchAccounts();
    for (Account account : accounts) {
      if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
        setCurrentUser(account);
        accounts.clear();
        return account;
      }
    }
    return null;
  }

  // fetch methods
  public void fetchAccounts() {
    String filepath = "../res/data/accounts/users.csv";

    try {
      Scanner reader = new Scanner(new File(filepath));
      boolean header = false;
      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        String[] fields = line.split(",");

        header = fields[0].equalsIgnoreCase("id") ? true : false;

        if (!header) {
          int id = Integer.parseInt(fields[0]);
          String username = fields[1];
          String password = fields[2];
          String role = fields[3];
          accounts.add(new Account(id, username, password, role));
        }
      }
      reader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void fetchStudents() {
    String filepath = "../res/data/masterlist/BSCS_Masterlist_Orig.csv";

    try {
      Scanner reader = new Scanner(new File(filepath));
      boolean header = false;
      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        String[] fields = line.split(",");

        header = fields[0].equalsIgnoreCase("id") ? true : false;

        if (!header) {
          String lastName = fields[1];
          String firstName = fields[2];
          String studentId = fields[3];
          String course = fields[4];
          int year = Integer.parseInt(fields[5]);
          char section = fields[6].charAt(0);
          String department = fields[7];
          students.add(new Student(studentId, lastName, firstName, course, year, section, department));
        }
      }
      reader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void dashboard(String username, String role) {
    new Clrscr();
    System.out.println("Welcome back, " + username + "!");
    if (role.equalsIgnoreCase("admin")) {
      adminDashboard();
    } else if (role.equalsIgnoreCase("staff")) {
      staffDashboard();
    } else {
      new Clrscr();
      System.out.println(
          "Hello, " + username + "! Your role is invalid.\nPlease reach out to your administrator to fix your role.");
      new Getch();
    }
  }

  public void adminDashboard() {
    boolean running = true;

    while (running) {
      new Title();
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

  public void staffDashboard() {
    boolean running = true;

    while (running) {
      new Title();
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

  private void borrowingForm() {
    System.out.println("--Borrowing Form--");
    boolean running = true;

    while (running) {
      System.out.println("Type of Resources");
      System.out.println("1. PC");
      System.out.println("2. Laptop");
      System.out.println("3. Other Equipments");
      System.out.println("4. Cancel");
      System.out.print("Select: ");
      int ch = in.nextInt();

      switch (ch) {
        case 1:
          System.out.print("Select Computer Lab (1-3): ");
          int destination = in.nextInt();
          inventoryController.initializeComputerList(destination);
          List<Computer> computers = inventoryController.getComputers();

          System.out.println("\n-------------------------------------------");
          System.out.print("Select a Computer: ");
          String pc = "PC" + String.valueOf(in.nextInt());

          for (Computer computer : computers) {
            if (computer.getName().equals(pc)) {
              inventoryController.borrowComputer(computer);
              inventoryController.updateComputerInventory(computers);
              break;
            }
          }
          computers.clear();
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
 * List<BorrowTransaction>
 * - viewAuditLog(): List<AuditLog>
 * - Administrative Functions:
 * - createAccount(account: Account): void
 * - updateAccount(accountId: int, newDetails: Account): void
 * - deleteAccount(accountId: int): void
 * - manageStudent(student: Student, operation: String): void
 */