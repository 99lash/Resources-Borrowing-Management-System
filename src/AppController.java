import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import custom.utils.Clrscr;
import custom.utils.Getch;
import custom.utils.Title;

public class AppController {
  private static Scanner in = new Scanner(System.in);
  private LogController logController;
  private TransactionController transactionController;
  private AccountController accountController;
  private StudentController studentController;
  private InventoryController inventoryController;
  private User currentUser;

  AppController() {
    this.logController = new LogController();
    this.transactionController = new TransactionController();
    this.accountController = new AccountController();
    this.studentController = new StudentController();
    this.inventoryController = new InventoryController();
  }

  // SETTERS
  public void setTransactionController(TransactionController transactionController) {
    this.transactionController = transactionController;
  }

  public void setStudentController(StudentController studentController) {
    this.studentController = studentController;
  }

  public void setAccountController(AccountController accountController) {
    this.accountController = accountController;
  }

  public void setLogController(LogController logController) {
    this.logController = logController;
  }

  public void setInventoryController(InventoryController inventoryManager) {
    this.inventoryController = inventoryManager;
  }

  public void setCurrentUser(User currentUser) {
    this.currentUser = currentUser;
  }

  // GETTERS
  public TransactionController getTransactionController() {
    return transactionController;
  }

  public StudentController getStudentController() {
    return studentController;
  }

  public AccountController getAccountController() {
    return accountController;
  }

  public LogController getLogController() {
    return logController;
  }

  public InventoryController getInventoryController() {
    return inventoryController;
  }

  public User getCurrentUser() {
    return currentUser;
  }

  public List<AuditLog> viewAuditLog() {
    return new ArrayList<>();
  }

  public User authenticate(String username, String password) {
    accountController.fetchAccountsFromDatabase();
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

  public void dashboard(String username, String role) {
    new Clrscr();
    System.out.println("Greetings, " + username + "!");

    if (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("super admin")) {
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
    boolean show = true;
    while (running) {
      new Clrscr();
      new Title();
      String switchEvent = show ? " Hide " : " Show ";
      System.out.println("Welcome back, " + username + "!");
      System.out.println();
      if (show) {
        logController.fetchTransactionLogFromDatabase();
        logController.displayAllTranscationLogs();
        logController.clearTransactionLog();
      } else {
        System.out.println("Recent Transactions [Log] (Hidden)\n\n\n\n\n\n\n\n");
      }
      System.out.println("1. Borrow an Item");
      System.out.println("2. Return an Item");
      System.out.println("3. View Borrower Log");
      System.out.println("4. View Audit Log");
      System.out.println("5. Admin Operations");
      System.out.println("6." + switchEvent + "Transactions Log");
      System.out.println("7. Log out");
      System.out.print("Select: ");
      int ch = in.nextInt();

      switch (ch) {

        case 1:
          borrowingForm();
          break;

        case 2:
          ReturningForm();
          break;

        case 5:
          administrativeOperations();
          break;

        case 6:
          show = !show;
          break;

        case 7:
          running = false;
          break;
        default:
          break;
      }
    }

  }

  public void administrativeOperations() {
    while (true) {
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
          break;
      }
    }
  }

  public void ManageAccounts() {
    boolean show = true;

    while (true) {
      String switchEvent = show ? " Hide " : " Show ";
      Boolean success = false;
      new Clrscr();
      System.out.println("---------------------------------------");
      System.out.println("Administrative Panel -> Manage Accounts");
      System.out.println("---------------------------------------\n");
      if (show) {
        accountController.fetchAccountsFromDatabase();
        accountController.displayAllAccounts();
        accountController.clearAccounts();
      } else {
        System.out.println("List of Accounts Detail (Hidden)");
      }
      System.out.println("1. Create an Account");
      System.out.println("2. Update an Account");
      System.out.println("3. Search an Account");
      System.out.println("4. Delete an Account");
      System.out.println("5." + switchEvent + "list of accounts");
      System.out.println("6. Back to Administrative Panel");
      System.out.print("Select: ");
      int ch = in.nextInt();

      switch (ch) {
        case 1:
          accountController.fetchAccountsFromDatabase();
          success = accountController.createAccount();
          if (success)
            accountController.updateAccountDatabase(accountController.getAccounts());
          accountController.clearAccounts();
          new Getch();
          break;

        case 2:
          accountController.fetchAccountsFromDatabase();
          success = accountController.updateAccount();
          if (success)
            accountController.updateAccountDatabase(accountController.getAccounts());
          accountController.clearAccounts();
          new Getch();
          break;

        case 3:
          accountController.fetchAccountsFromDatabase();
          success = accountController.searchAccount();
          accountController.clearAccounts();
          new Getch();
          break;

        case 4:
          accountController.fetchAccountsFromDatabase();
          success = accountController.deleteAccount(getCurrentUser());
          if (success == null) {
            accountController.updateAccountDatabase(accountController.getAccounts());
            accountController.clearAccounts();
            new Getch();
            new Main().main(null);
          } else if (success)
            accountController.updateAccountDatabase(accountController.getAccounts());
          accountController.clearAccounts();
          new Getch();
          break;

        case 5:
          show = !show;
          break;

        case 6:
          return;

        default:
          System.out.println("Invalid input! Please select between 1-5.");
          break;
      }
    }
  }

  public void staffDashboard(String username) {
    boolean running = true;
    boolean show = true;
    while (running) {
      new Clrscr();
      new Title();
      String switchEvent = show ? " Hide " : " Show ";
      if (show) {
        logController.fetchTransactionLogFromDatabase();
        logController.displayAllTranscationLogs();
        logController.clearTransactionLog();
      } else {
        System.out.println("Recent Transactions [Log] (Hidden)\n\n\n\n\n\n\n\n");
      }

      System.out.println("1. Borrow an Item");
      System.out.println("2. Return an Item");
      System.out.println("3. View Borrower Log");
      System.out.println("4. View Audit Log");
      System.out.println("5. View Inventory");
      System.out.println("6." + switchEvent + "Transactions Log");
      System.out.println("7. Log out");
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
          show = !show;
          break;

        case 7:
          running = false;
          break;

        default:
          break;
      }
    }

  }

  // public Boolean SubmitVerification(String studentId, String collateral,
  // Resource borrowed) {
  // studentController.fetchStudentDatabase();
  // Map<Integer, Student> students = studentController.getStudents();
  // Student studentFound = null;
  // char ch = ' ';
  // boolean recordFound = false;
  // while (ch != 'Y' || ch != 'N') {
  // new Clrscr();
  // System.out.println("Review Details");
  // System.out.println("-------------------------------");

  // for (Student student : students.values()) {
  // if (student.getStudentId().equalsIgnoreCase(studentId)) {
  // recordFound = true;
  // studentFound = student;
  // }
  // }
  // if (recordFound) {
  // System.out.println("Borrowed Resource: " + borrowed.getName());
  // System.out.println("Student ID: " + studentId);
  // System.out.println("Full Name: " + studentFound.getFullName());
  // System.out.println("Course/Year/Section: " + studentFound.getCourse() + " - "
  // + studentFound.getYear()
  // + studentFound.getSection());
  // System.out.println("Department: " + studentFound.getDepartment());
  // System.out.println("Collateral: " + collateral);
  // System.out.print("Submit Verification [Y/N]: ");
  // ch = in.nextLine().toUpperCase().charAt(0);

  // if (ch == 'Y') {
  // return true;
  // } else if (ch == 'N') {
  // return false;
  // }
  // } else {
  // break;
  // }
  // }
  // return null;
  // }

  public void ComputerForm() {
    int destination = 0;
    while (destination < 1 || destination > 3) {
      System.out.print("Select Computer Lab (1-3): ");
      destination = in.nextInt();
    }
    new Clrscr();
    new Title();
    inventoryController.initializeComputerList(destination);
    List<Computer> computers = inventoryController.getComputers();

    System.out.println("\n-------------------------------------------");
    System.out.print("Select a Computer: ");
    int selected = in.nextInt();
    String pc = "PC" + String.valueOf(selected);
    in.nextLine();
    boolean isComputerFound = false;

    for (Computer computer : computers) {
      if (computer.getName().equals(pc) && !computer.getStatus().equalsIgnoreCase("available")) {
        System.out.println("PC" + selected + " isn't available at the moment.");
        isComputerFound = true;
        break;
      } else if (computer.getName().equals(pc) && computer.getStatus().equalsIgnoreCase("available")) {
        isComputerFound = true;
        System.out.print("Enter Student ID: ");
        String studentId = in.nextLine();
        System.out.print("Enter Collateral: ");
        String collateral = in.nextLine();

        // submit verification
        studentController.fetchStudentDatabase();
        Map<Integer, Student> students = studentController.getStudents();
        Student studentBorrower = null;
        char ch = ' ';
        while (ch != 'Y' || ch != 'N') {
          new Clrscr();
          for (Student student : students.values()) {
            if (student.getStudentId().equalsIgnoreCase(studentId)) {
              studentBorrower = student;
            }
          }
          if (studentBorrower != null) {
            System.out.println("Review Details");
            System.out.println("-------------------------------");
            System.out.println("Borrowed Resource: " + computer.getName());
            System.out.println("Student ID: " + studentBorrower.getStudentId());
            System.out.println("Full Name: " + studentBorrower.getFullName());
            System.out.println("Course/Year/Section: " + studentBorrower.getCourse() + " - " + studentBorrower.getYear()
                + studentBorrower.getSection());
            System.out.println("Department: " + studentBorrower.getDepartment());
            System.out.println("Collateral: " + collateral);
            System.out.print("Submit Verification [Y/N]: ");
            ch = in.nextLine().toUpperCase().charAt(0);

            if (ch == 'Y') {
              // @ Updating the inventory
              inventoryController.borrowComputer(computer); // * borrow a computer, pinalitan lang status ng computer na napili (Available -> Not Available)
              inventoryController.updateComputerInventory(computers); // !!! dito ibang way ang ginamit ko sa pag update ko ng computer inventory sa database 

              // @ Adding a new successful record of computer transactions to the database
              transactionController.fetchComputerTransactionsFromDatabase(); // * Nireretrieve nito yung mga data from computer transactions database then isasalin sa array
              List<ComputerTransaction> computerTransactions = transactionController.getComputerTransactions(); //* Dito isinalin na natin sa array
              ComputerTransaction newComputerTranscaTransaction = new ComputerTransaction(studentId, studentBorrower.getFullName("fn-mi-ln"), collateral, computer.getId(), computer.getName(), LocalDateTime.now(), currentUser.getUsername(), destination);
              computerTransactions.add(newComputerTranscaTransaction); // * Here we add a new record of successful computer transactions pero sa array muna.
              // @ Updating the database of computer transactions
              transactionController.updateComputerTranscactionsDatabase(); // * Dito i-update na natin yung database ni computer transactions, yung data na mapupunta sa databasae ay galing sa array
              transactionController.clearComputerTransactions(); // * Clear the array kung saan natin inisstore yung mga data from database. Ito ay para sa next update natin hindi mag duplicate.
              
              // @ Add a new record of transaction log  
              // TODO: log the transaction
              logController.fetchTransactionLogFromDatabase(); // * Nireretrieve nito yung mga data from computer transactions database then isasalin sa array
              List<TransactionLog> transactionLogs = logController.getTransactionsLog();
              transactionLogs.add(new TransactionLog(newComputerTranscaTransaction.getTransactionId(), studentBorrower.getStudentId(), studentBorrower.getFullName("fn-mi-ln"), collateral, computer.getName(), destination, LocalDateTime.now(), null, currentUser.getUsername(), "N/A"));
              // @ Updating the database
              logController.updateTransactionLogDatabase();
              // logController.clearTransactionLog();
              break;
            } else if (ch == 'N') {
              System.out.println("Borrow Transaction Cancelled.");
              break;
            }
          } else {
            System.out.println("No Record Found.");
            break;
          }
        }
        break;
      }
    }
    if (!isComputerFound) {
      System.out.println("PC" + selected + " does not exist.");
    }
    new Getch();
    computers.clear();
  }

  private void borrowingForm() {
    System.out.println("--Borrowing Form--");
    boolean running = true;

    while (running) {
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
      }
    }
  }

  private void ReturningForm() {
    System.out.println("--Returning Form--");
  }
}