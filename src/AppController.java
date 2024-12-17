import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import custom.utils.Clrscr;
import custom.utils.Getch;
import custom.utils.Title;

public class AppController {
  public static final String RESET = "\u001B[0m"; // Reset color
  public static final String RED = "\u001B[31m";
  public static final String GREEN = "\u001B[32m";
  public static final String YELLOW = "\u001B[33m";
  public static final String BLUE = "\u001B[34m";
  public static final String PURPLE = "\u001B[35m";
  public static final String CYAN = "\u001B[36m";
  public static final String WHITE = "\u001B[37m";

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

  public User authenticate(String username, char[] charPassword) {
    String password = "";
    for (char c : charPassword) {
      password += c;
    }
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

  public String stringMenu(String switchEvent, String role) {
    String s = "";
    if (role.equalsIgnoreCase("admin")) {
      s += String.format("[%sMain Menu%s]\n\n", GREEN, RESET);
      s += String.format("[%s1%s] Borrow an Item\n", GREEN, RESET);
      s += String.format("[%s2%s] Return an Item\n", GREEN, RESET);
      s += String.format("[%s3%s] View List of Computer Borrower\n", GREEN, RESET);
      s += String.format("[%s4%s] View List of Laptop Borrower\n", GREEN, RESET);
      s += String.format("[%s5%s] View List of Equipment Borrower\n", GREEN, RESET);
      s += String.format("[%s6%s] View Audit Log\n", GREEN, RESET);
      s += String.format("[%s7%s] Administrative Operations\n", GREEN, RESET);
      s += String.format("[%s8%s] %s Transactions Log\n", GREEN, RESET, switchEvent);
      s += String.format("[%s9%s] Log out\n", GREEN, RESET);
    } else {
      s += String.format("[%sMain Menu%s]\n\n", GREEN, RESET);
      s += String.format("[%s1%s] Borrow an Item\n", GREEN, RESET);
      s += String.format("[%s2%s] Return an Item\n", GREEN, RESET);
      s += String.format("[%s3%s] View List of Computer Borrower\n", GREEN, RESET);
      s += String.format("[%s4%s] View List of Laptop Borrower\n", GREEN, RESET);
      s += String.format("[%s5%s] View List of Equipment Borrower\n", GREEN, RESET);
      s += String.format("[%s6%s] View Audit Log\n", GREEN, RESET);
      s += String.format("[%s7%s] View Inventory\n", GREEN, RESET);
      s += String.format("[%s8%s] %s Transactions Log\n", GREEN, RESET, switchEvent);
      s += String.format("[%s9%s] Log out\n", GREEN, RESET);
    }

    return s;
  }

  public void adminDashboard(String username) {
    boolean running = true;
    boolean show = true;
    while (running) {
      new Clrscr();
      new Title();
      String switchEvent = show ? "Hide" : "Show";
      // System.out.println("\t\t\t\t\t\t\t\t\t\tWelcome back, " + username + "!");
      System.out.println("Welcome Back, " + username + "!\n\n");
      if (show) {
        logController.fetchTransactionLogsFromDatabase();
        logController.displayTranscationLogs();
        logController.clearTransactionLog();
      } else {
        System.out.println("Transactions Log (Hidden)\n\n\n\n\n\n\n\n");
      }
      System.out.print(stringMenu(switchEvent, "admin"));
      System.out.print("Select: ");
      int ch = in.nextInt();
      in.nextLine();
      switch (ch) {

        case 1:
          borrowingForm();
          break;

        case 2:
          returningForm();
          break;

        case 3:
          computerBorrowersList();
          break;

        case 4:
          laptopBorrowersList();
          break;

        case 5:
          equipmentBorrowersList();
          break;

        case 6:
          System.out.println("View audit log");
          break;

        case 7:
          administrativeOperations();
          break;

        case 8:
          show = !show;
          break;

        case 9:
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
      new Title();
      System.out.println("----------------------------");
      System.out.println("Administrative Panel");
      System.out.println("----------------------------\n");
      System.out.println("[1] Manage Accounts");
      System.out.println("[2] Manage Students Masterlist");
      System.out.println("[3] Manage Inventory");
      System.out.println("[4] Back to Dashboard");
      System.out.print("Select: ");
      int ch = in.nextInt();
      in.nextLine();

      switch (ch) {
        case 1:
          ManageAccounts();
          break;

        case 2:
          ManageStudents();
          break;

        case 3:
          ManageInventory();
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
      new Title();
      System.out.println("-----------------------------------------");
      System.out.println("Administrative Panel -> Manage Accounts");
      System.out.println("-----------------------------------------\n");
      if (show) {
        accountController.fetchAccountsFromDatabase();
        accountController.displayAllAccounts();
        accountController.clearAccounts();
      } else {
        System.out.println("List of Accounts Detail (Hidden)");
      }
      System.out.println("[1] Create an Account");
      System.out.println("[2] Update an Account");
      System.out.println("[3] Search an Account");
      System.out.println("[4] Delete an Account");
      System.out.println("[5]" + switchEvent + "list of accounts");
      System.out.println("[6] Back to Administrative Panel");
      System.out.print("Select: ");
      int ch = in.nextInt();

      switch (ch) {
        case 1:
          accountController.fetchAccountsFromDatabase();
          success = accountController.createAccount();
          if (success)
            accountController.updateAccountsDatabase(accountController.getAccounts());
          accountController.clearAccounts();
          new Getch();
          break;

        case 2:
          accountController.fetchAccountsFromDatabase();
          success = accountController.updateAccount();
          if (success)
            accountController.updateAccountsDatabase(accountController.getAccounts());
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
            accountController.updateAccountsDatabase(accountController.getAccounts());
            accountController.clearAccounts();
            new Getch();
            new Main().main(null); // may bug dito. kapag nadelete mo currentuser tas nag [2] exit. Babalik ka dito sa function ManageAccount()
                                   // na ito.
            // return;
          } else if (success)
            accountController.updateAccountsDatabase(accountController.getAccounts());
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

  public void ManageStudents() {
    boolean show = true;
    while (true) {
      Boolean success = false;
      new Clrscr();
      new Title();
      System.out.println("-----------------------------------------");
      System.out.println("Administrative Panel -> Manage Students");
      System.out.println("-----------------------------------------\n");
      if (show) {
        // accountController.fetchAccountsFromDatabase();
        // accountController.displayAllAccounts();
        // accountController.clearAccounts();
        System.out.println("List of Students Detail (Visible)");
      } else {
        System.out.println("List of Students Detail (Hidden)");
      }
      System.out.println("[1] Add a student");
      System.out.println("[2] Update a student");
      System.out.println("[3] Search a student");
      System.out.println("[4] Display all students");
      System.out.println("[5] Delete a student");
      System.out.println("[6] Back to Administrative Panel");
      System.out.print("Select: ");
      int ch = in.nextInt();

      studentController.fetchStudentsFromDatabase();
      switch (ch) {
        case 1:
          success = studentController.addStudentRecord();
          new Getch();
          break;

        case 2:
          success = studentController.updateStudentRecord();
          new Getch();
          break;

        case 3:
          success = studentController.displayStudentRecord();
          new Getch();
          break;

        case 4:
          new Clrscr();
          new Title();
          studentController.displayAllStudentsRecord();
          break;

        case 5:
          success = studentController.deleteStudentRecord();
          new Getch();
          break;

        case 6:
          return;

        default:
          System.out.println("Invalid input! Please select between 1-5.");
          break;
      }
      if (success)
        studentController.updateStudentsDatabase();
      studentController.clearStudents();
    }
  }

  public void ManageInventory() {
    while (true) {
      Boolean success = false;
      new Clrscr();
      new Title();
      System.out.println("-----------------------------------------");
      System.out.println("Administrative Panel -> Manage Inventory");
      System.out.println("-----------------------------------------\n");
      System.out.println("[1] Add a computer");
      System.out.println("[2] Add a laptop");
      System.out.println("[3] Add an equipment");
      System.out.println("[4] Update a computer details");
      System.out.println("[5] Update a laptop details");
      System.out.println("[6] Update an equipment details");
      System.out.println("[7] Search a computer");
      System.out.println("[8] Search a laptop");
      System.out.println("[9] Search an equipment");
      System.out.println("[10] Delete a computer");
      System.out.println("[11] Delete a laptop");
      System.out.println("[12] Delete an equipment");
      System.out.println("[13] Display all computers");
      System.out.println("[14] Display all laptops");
      System.out.println("[15] Display all equipments");
      System.out.println("[16] Back to Administrative Panel");
      System.out.print("Select: ");
      int ch = in.nextInt(); in.nextLine();

      switch (ch) {
        case 1:
          success = inventoryController.addComputer();
          if (success) inventoryController.updateComputerDatabase();
          break;

        case 2:
          success = inventoryController.addLaptop();
          if (success) inventoryController.updateLaptopDatabase();
          break;

        case 3:
          success = inventoryController.addEquipment();
          if (success) inventoryController.updateEquipmentDatabase();
          break;

          case 4:
          success = inventoryController.updateComputerDetails();
          if (success) inventoryController.updateComputerDatabase();
          break;

        case 5:
          success = inventoryController.updateLaptopDetails();
          if (success) inventoryController.updateLaptopDatabase();
          break;

        case 6:
          success = inventoryController.updateEquipmentDetails();
          if (success) inventoryController.updateEquipmentDatabase();
          break;

        case 7:
          inventoryController.displayComputerDetails();
          break;

        case 8:
          inventoryController.displayLaptopDetails();
          break;
        
        case 9:
          inventoryController.displayEquipmentDetails();
          break;

        case 10:
          success = inventoryController.deleteComputer();
          if (success) inventoryController.updateComputerDatabase();
          break;

        case 11:
          success = inventoryController.deleteLaptop();
          if (success) inventoryController.updateLaptopDatabase();
          break;

        case 12:
          success = inventoryController.deleteEquipment();
          if (success) inventoryController.updateEquipmentDatabase();
          break;

        case 13:
          inventoryController.displayAllComputers();
          break;
        
        case 14:
          inventoryController.displayAllLaptops();
          break;

        case 15:
          inventoryController.displayAllEquipments();
          break; 
          
        case 16:
          return;
      }
      inventoryController.clearComputers();
      inventoryController.clearLaptops();
      inventoryController.clearEquipments();
      new Getch();
    }
  }

  public void staffDashboard(String username) {
    boolean running = true;
    boolean show = true;
    while (running) {
      new Clrscr();
      new Title();
      String switchEvent = show ? "Hide" : "Show";
      if (show) {
        logController.fetchTransactionLogsFromDatabase();
        logController.displayTranscationLogs();
        logController.clearTransactionLog();
      } else {
        System.out.println("Transactions [Log] (Hidden)\n\n\n\n\n\n\n\n");
      }
      System.out.print(stringMenu(switchEvent, "staff"));
      System.out.print("Select: ");
      int ch = in.nextInt();

      switch (ch) {

        case 1:
          borrowingForm();
          break;

        case 2:
          returningForm();
          break;

        case 3:
          computerBorrowersList();
          break;

        case 4:
          laptopBorrowersList();
          break;

        case 5:
          equipmentBorrowersList();
          break;

        case 6:
          System.out.println("View audit log");
          break;

        case 7:
          viewInventory();
          break;

        case 8:
          show = !show;
          break;

        case 9:
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

  private void borrowingSubmitConfirmation(String studentId, String collateral, Integer destination, Resource resource) {
    Student borrower = null;
    char ch = ' ';
    studentController.fetchStudentsFromDatabase();

    while (ch != 'Y' || ch != 'N') {
      new Clrscr();
      new Title();
      borrower = studentController.searchStudent(studentId);
      if (borrower == null) {
        System.out.println("No record found.");
        break;
      }
      
      Computer computer = null;
      Laptop laptop = null;
      Equipment equipment = null;
      int borrowQuantity = 0;
      transactionController.fetchTransactionsHeader();
      List<TransactionHeader> transactionsHeader = transactionController.getTransactionsHeader();
      TransactionHeader newTransactionHeader = new TransactionHeader(LocalDateTime.now(),borrower.getFullName("fn-mi-ln"), resource.getClass().getSimpleName());
      transactionsHeader.add(newTransactionHeader);
      String reviewDetailsStr = String.format("Transaction ID: %d\nBorrowed Date & Time: %s", newTransactionHeader.getTransactionId(), newTransactionHeader.getTransDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")));
      String borrowerDetailsStr = "";
      String itemName = "";

      logController.fetchTransactionLogsFromDatabase();
      List<TransactionLog> transactionLogs = logController.getTransactionsLog();      
      // reviewDetailsStr = reviewDetailsStr.concat(String.format("Transaction ID: %d\nBorrowed Date & Time: %s", newTransactionHeader.getTransactionId(), newTransactionHeader.getTransDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"))));
      if (resource instanceof Computer) {
        computer = ((Computer) resource);
        itemName = String.format("%s (CL%d)", computer.getName(), destination);
        reviewDetailsStr =  reviewDetailsStr.concat(String.format("\nBorrowed Resource: %s (CL%d)", computer.getName(), computer.getDestination()));
      } else if (resource instanceof Laptop) {
        laptop = ((Laptop) resource);
        itemName = laptop.getName();
        reviewDetailsStr = reviewDetailsStr.concat(String.format("\nBorrowed Resource: %s", laptop.getName()));
      } else if (resource instanceof Equipment) {
        equipment = ((Equipment) resource);
        itemName = equipment.getName();
        borrowQuantity = Character.getNumericValue(equipment.getName().charAt(equipment.getName().length() - 1));
        String originalEquipmentName = equipment.getName().replace(String.valueOf(borrowQuantity), "");
        equipment.setName(originalEquipmentName);
        reviewDetailsStr = reviewDetailsStr.concat(String.format("\nBorrowed Resource: %s\nBorrowed Quantity: %d", equipment.getName(), borrowQuantity, currentUser.getUsername()));
      }
      reviewDetailsStr = reviewDetailsStr.concat(String.format("\nIssuer: %s\n", currentUser.getUsername()));
      System.out.println("-----------------------------------------\n--- Review Details ---\n-----------------------------------------\n");
      System.out.println(reviewDetailsStr);
      System.out.println("-----------------------------------------\n--- Borrower Details ---\n-----------------------------------------\n");
      borrowerDetailsStr = borrowerDetailsStr.concat(String.format("Student ID: %s\nFull Name: %s\nCourse Year & Section: %s %d %c\nCollateral: %s\nDepartment: %s\n", borrower.getStudentId(), borrower.getFullName(), borrower.getCourse(), borrower.getYear(), borrower.getSection(), collateral , borrower.getDepartment()));
      System.out.println(borrowerDetailsStr);
      System.out.print("Submit Verification [Y/N]: ");
      ch = in.nextLine().toUpperCase().charAt(0);
      if (ch == 'N') {
        System.out.println("Borrow Transaction Cancelled.");
        transactionController.clearTransactionsHeader();
        transactionsHeader.clear();
        transactionLogs.add(new TransactionLog(newTransactionHeader.getTransDateTime(), borrower.getStudentId(),borrower.getFullName("fn-mi-ln"), "Borrowed", newTransactionHeader.getTransactionId(), resource.getId(), itemName, "Canceled"));
        logController.updateTransactionLogDatabase();
        break;
      }

      if (ch == 'Y' && computer != null) {
        // FETCH
        transactionController.fetchComputerTransactionsFromDatabase(); // * Nireretrieve nito yung mga data from
                                                                       // computer transactions database then isasalin
                                                                       // sa array

        // GET LIST
        List<ComputerTransaction> computerTransactions = transactionController.getComputerTransactions(); // * Dito
                                                                                                          // isinalin na
                                                                                                          // natin sa
                                                                                                          // array

        // ADD
        inventoryController.borrowItem(computer); // * borrow a computer, pinalitan lang status ng computer na napili
                                              // (Available -> Not Available)
        ComputerTransaction newComputerTransaction = new ComputerTransaction(newTransactionHeader.getTransactionId(),borrower.getStudentId(), borrower.getFullName("fn-mi-ln"), collateral, computer.getId(), computer.getName(),newTransactionHeader.getTransDateTime(), null, currentUser.getUsername(), "N/A", destination, "borrowed");
        computerTransactions.add(newComputerTransaction); // * Here we add a new record of successful computer
                                                          // transactions pero sa array muna.
        // transactionLogs.add(new
        // TransactionLog(newComputerTransaction.getTransactionId(),
        // borrower.getStudentId(), borrower.getFullName("fn-mi-ln"), collateral,
        // itemNameAndDestination, newComputerTransaction.getBorrowDateTime(), null,
        // newComputerTransaction.getIssuer(), "N/A"));
        transactionLogs.add(new TransactionLog(newTransactionHeader.getTransDateTime(), borrower.getStudentId(),
            borrower.getFullName("fn-mi-ln"), "Borrowed", newTransactionHeader.getTransactionId(), computer.getId(),
            itemName, "Success"));
        // UPDATE
        inventoryController.updateComputerDatabase(); // !!! dito ibang way ang ginamit ko sa pag update ko ng computer
                                                      // inventory sa database
        transactionController.updateTransactionsHeader();
        transactionController.updateComputerTransactionsDatabase(); // * Dito i-update na natin yung database ni
                                                                    // computer transactions, yung data na mapupunta sa
                                                                    // databasae ay galing sa array
        logController.updateTransactionLogDatabase();

        // CLEAR
        inventoryController.clearComputers();
        transactionController.clearComputerTransactions(); // * Clear the array kung saan natin inisstore yung mga data
                                                           // from database. Ito ay para sa next update natin hindi mag
                                                           // duplicate.
        logController.clearTransactionLog();
        transactionController.clearTransactionsHeader();
        break;
      } else if (ch == 'Y' && laptop != null) {
        // FETCH
        transactionController.fetchLaptopTransactionsFromDatabase();

        // GET LIST
        List<LaptopTransaction> laptopTransactions = transactionController.getLaptopTransactions();

        // ADD
        inventoryController.borrowItem(laptop);
        LaptopTransaction newLaptopTransaction = new LaptopTransaction(newTransactionHeader.getTransactionId(),
            borrower.getStudentId(), borrower.getFullName("fn-mi-ln"), collateral, laptop.getId(), laptop.getName(),
            newTransactionHeader.getTransDateTime(), null, currentUser.getUsername(), "N/A", "Borrowed");
        // LaptopTransaction newLaptopTransaction = new
        // LaptopTransaction(newTransactionHeader.getTransactionId(),
        // borrower.getStudentId(), borrower.getFullName("fn-mi-ln"), collateral,
        // laptop.getId(), laptop.getName(), newTransactionHeader.getTransDateTime(),
        // null, currentUser.getUsername(), "N/A", "Borrowed");
        laptopTransactions.add(newLaptopTransaction);
        // transactionLogs.add(new
        // TransactionLog(newLaptopTransaction.getTransactionId(),
        // borrower.getStudentId(), borrower.getFullName("fn-mi-ln"), collateral,
        // laptop.getName(), newLaptopTransaction.getBorrowDateTime(), null,
        // newLaptopTransaction.getIssuer(), "N/A"));
        transactionLogs.add(new TransactionLog(newTransactionHeader.getTransDateTime(), borrower.getStudentId(),
            borrower.getFullName("fn-mi-ln"), "Borrowed", newTransactionHeader.getTransactionId(), laptop.getId(),
            laptop.getName(), "Success"));
        // UPDATE
        inventoryController.updateLaptopDatabase();
        transactionController.updateTransactionsHeader();
        transactionController.updateLaptopTransactionsDatabase();
        logController.updateTransactionLogDatabase();

        // CLEAR
        inventoryController.clearLaptops();
        transactionController.clearLaptopTransactions();
        logController.clearTransactionLog();
        transactionController.clearTransactionsHeader();

        break;
      } else if (ch == 'Y' && equipment != null) {
        // FETCH
        transactionController.fetchEquipmentTransactionsFromDatabase();

        // GET LIST
        List<EquipmentTransaction> equipmentTransactions = transactionController.getEquipmentTransactions();

        // ADD
        inventoryController.borrowItem(equipment, borrowQuantity);
        EquipmentTransaction newEquipmentTransaction = new EquipmentTransaction(newTransactionHeader.getTransactionId(),
            borrower.getStudentId(), borrower.getFullName("fn-mi-ln"), collateral, equipment.getId(),
            equipment.getName(), newTransactionHeader.getTransDateTime(), null, currentUser.getUsername(), "N/A",
            "Borrowed", borrowQuantity);
        equipmentTransactions.add(newEquipmentTransaction);
        // transactionLogs.add(new
        // TransactionLog(newEquipmentTransaction.getTransactionId(),
        // borrower.getStudentId(), borrower.getFullName("fn-mi-ln"), collateral,
        // equipment.getName(), newEquipmentTransaction.getBorrowDateTime(), null,
        // newEquipmentTransaction.getIssuer(), "N/A"));
        transactionLogs.add(new TransactionLog(newTransactionHeader.getTransDateTime(), borrower.getStudentId(),
            borrower.getFullName("fn-mi-ln"), "Borrowed", newTransactionHeader.getTransactionId(), equipment.getId(),
            equipment.getName(), "Success"));

        // UPDATE
        inventoryController.updateEquipmentDatabase();
        transactionController.updateTransactionsHeader();
        transactionController.updateEquipmentTransactionsDatabase();
        logController.updateTransactionLogDatabase();

        // CLEAR
        inventoryController.clearEquipments();
        transactionController.clearEquipmentTransactions();
        logController.clearTransactionLog();
        transactionController.clearTransactionsHeader();

        break;
      }
    }
  }

  private void borrowingForm() {
    boolean running = true;
    while (running) {
      new Clrscr();
      new Title();
      System.out.println("=== Borrowing ===");
      System.out.println("[1] PC");
      System.out.println("[2] Laptop");
      System.out.println("[3] Other Equipments");
      System.out.println("[4] Cancel");
      System.out.print("Select a type of resources: ");
      int ch = in.nextInt();
      in.nextLine();
      switch (ch) {
        case 1:
          computerBorrowForm();
          running = false;
          break;

        case 2:
          laptopBorrowForm();
          running = false;
          break;

        case 3:
          equipmentBorrowForm();
          running = false;
          break;

        case 4:
          return;
      }
    }
  }

  private void computerBorrowForm() {
    int destination = 0;
    while (destination < 1 || destination > 3) {
      System.out.print("Select a Computer Lab (1-3): ");
      destination = in.nextInt();
    }
    new Clrscr();
    new Title();
    inventoryController.showAvailableComputers(destination);
    List<Computer> computers = inventoryController.getComputers();

    System.out.println("\n-------------------------------------------");
    System.out.print("Select a Computer: ");
    int selected = in.nextInt();
    String selectedComputer = "PC" + String.valueOf(selected);
    in.nextLine();

    Computer computerFound = inventoryController.searchComputer(selectedComputer);
    if (computerFound != null && computerFound.getName().equalsIgnoreCase(selectedComputer)
        && computerFound.getStatus().equalsIgnoreCase("available")) {
      System.out.print("Enter Student ID: ");
      String studentId = in.nextLine();
      System.out.print("Enter Collateral: ");
      String collateral = in.nextLine();
      borrowingSubmitConfirmation(studentId, collateral, destination, computerFound);
    } else if (computerFound != null && computerFound.getName().equalsIgnoreCase(selectedComputer)
        && computerFound.getStatus().equalsIgnoreCase("not available")) {
      System.out.println("PC" + selected + " isn't available at the moment.");
    } else {
      System.out.println("PC" + selected + " does not exist.");
    }
    new Getch();
    computers.clear();
  }

  private void laptopBorrowForm() {
    new Clrscr();
    new Title();
    inventoryController.showAvailableLaptops();
    List<Laptop> laptops = inventoryController.getLaptops();
    System.out.print("Select a Laptop: ");
    int selected = in.nextInt();
    in.nextLine();
    String selectedLaptop = String.format("LAPTOP%d", selected);

    Laptop laptopFound = inventoryController.searchLaptop(selectedLaptop);

    if (laptopFound != null && laptopFound.getStatus().equalsIgnoreCase("available")) {
      System.out.print("Enter Student ID: ");
      String studentId = in.nextLine();
      System.out.print("Enter Collateral: ");
      String collateral = in.nextLine();
      borrowingSubmitConfirmation(studentId, collateral, null, laptopFound);
    } else if (laptopFound != null && laptopFound.getStatus().equalsIgnoreCase("not available")) {
      System.out.println("LAPTOP" + selected + " isn't available at the moment.");
    } else {
      System.out.println("LAPTOP" + selected + " doesn't exist.");
    }
    new Getch();
    laptops.clear();
  }

  private void equipmentBorrowForm() {
    new Clrscr();
    new Title();
    inventoryController.showAvailableEquipments();
    List<Equipment> equipments = inventoryController.getEquipments();
    System.out.print("Select an Equipment by ID: ");
    int selected = in.nextInt();

    Equipment equipmentFound = inventoryController.searchEquipment(selected);
    if (equipmentFound != null && equipmentFound.getStatus().equalsIgnoreCase("available")) {

      int quantity = 0;
      while (quantity < 1 || quantity > equipmentFound.getAvailableQuantity()) {
        System.out.print("Quantity to borrow: ");
        quantity = in.nextInt();
      }
      in.nextLine();
      String tempEquipmentName = equipmentFound.getName() + quantity;
      equipmentFound.setName(tempEquipmentName);
      System.out.print("Enter Student ID: ");
      String studentId = in.nextLine();
      System.out.print("Enter Collateral: ");
      String collateral = in.nextLine();
      borrowingSubmitConfirmation(studentId, collateral, null, equipmentFound);
    } else if (equipmentFound != null && equipmentFound.getStatus().equalsIgnoreCase("not available")) {
      System.out.println(equipmentFound.getName() + " isn't available at the moment.");
    } else {
      System.out.println("\nEquipment ID doesn't exist.");
    }
    new Getch();
    equipments.clear();
  }

  public void returningSubmitConfirmation(Resource resource, Transaction transaction, TransactionHeader transactionHeader) {
    /* 
      TODO: RETURNING FLOW
    * 1. Return item
    * 2. Record new transaction log
    * 3. Update item transaction
    */
    Computer computer = null;
    Laptop laptop = null;
    Equipment equipment = null;
    ComputerTransaction computerTransaction = null;
    LaptopTransaction laptopTransaction = null;
    EquipmentTransaction equipmentTransaction = null;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
    LocalDateTime returnDateTime = LocalDateTime.now();
    String reviewDetailsStr = String.format("Transaction ID: %d\nBorrowed Date & Time: %s", transactionHeader.getTransactionId(), transactionHeader.getTransDateTime().format(formatter));
    String borrowerDetailsStr = "";
    int returningQuantity = 0;
    
    studentController.fetchStudentsFromDatabase();
    Student borrower = studentController.searchStudent(transaction.getStudentNo());
    studentController.clearStudents();

    logController.fetchTransactionLogsFromDatabase();
    List<TransactionLog> transactionLogs = logController.getTransactionsLog();
    TransactionLog newTransactionLog = new TransactionLog(returnDateTime, borrower.getStudentId(), borrower.getFullName("fn-mi-ln"), "Returned", transactionHeader.getTransactionId(), transaction.getItemId(), transaction.getItemName(), "Success");
    
    reviewDetailsStr = reviewDetailsStr.concat(String.format("\nReturned Date & Time: %s", returnDateTime.format(formatter)));
    if (resource instanceof Computer) {
      computer = (Computer) resource; 
      computerTransaction = (ComputerTransaction) transaction;
      computerTransaction.setReciever(currentUser.getUsername());
      reviewDetailsStr =  reviewDetailsStr.concat(String.format("\nBorrowed Resource: %s (CL%d)", computerTransaction.getItemName(), computerTransaction.getDestination()));
    } else if (resource instanceof Laptop) {
      laptop = (Laptop) resource;
      laptopTransaction = (LaptopTransaction) transaction;
      laptopTransaction.setReciever(currentUser.getUsername());
      reviewDetailsStr = reviewDetailsStr.concat(String.format("\nBorrowed Resource: %s", laptopTransaction.getItemName()));
    } else if (resource instanceof Equipment) {
      equipment = (Equipment) resource;
      equipmentTransaction = (EquipmentTransaction) transaction;
      equipmentTransaction.setReciever(currentUser.getUsername());
      System.out.printf("Enter returning quantity: ");
      returningQuantity = in.nextInt(); in.nextLine();
      if (returningQuantity < 1 || returningQuantity > equipmentTransaction.getBorrowQuantity()) {
        System.out.println("You cannot return greater than the quantity you borrowed.");
        newTransactionLog.setStatus("Failed");
    
        transactionLogs.add(newTransactionLog);
        logController.setTransactionsLog(transactionLogs);
        
        logController.updateTransactionLogDatabase();
        transactionLogs.clear();
        logController.clearTransactionLog();
        // TODO: auditLog
        return;
      } 
      reviewDetailsStr = reviewDetailsStr.concat(String.format("\nBorrowed Resource: %s\nBorrowed Quantity: %d\nReturning Quantity: %d\n", equipmentTransaction.getItemName(), equipmentTransaction.getBorrowQuantity(), returningQuantity));
    }
    while (true) {
      new Clrscr(); new Title();
      reviewDetailsStr = reviewDetailsStr.concat(String.format("\nIssuer: %s\nReceiver: %s\n", transaction.getIssuer(), transaction.getReceiver()));
      System.out.println("-----------------------------------------\n--- \t   Review Detailst\t      ---\n-----------------------------------------\n");
      System.out.println(reviewDetailsStr);
      System.out.println("-----------------------------------------\n--- \t   Borrower Details\t      ---\n-----------------------------------------\n");
      borrowerDetailsStr = borrowerDetailsStr.concat(String.format("Student ID: %s\nFull Name: %s\nCourse Year & Section: %s %d-%c\nCollateral: %s\nDepartment: %s\n", transaction.getStudentNo(), transaction.getStudentName(), borrower.getCourse(), borrower.getYear(), borrower.getSection(), transaction.getCollateral() , borrower.getDepartment()));
      System.out.println(borrowerDetailsStr);
      System.out.print("Submit Verification [Y/N]: ");
      char ch = in.nextLine().toUpperCase().charAt(0);
      if (ch == 'N') {
        System.out.println("Borrow Transaction Canceled.");
        newTransactionLog.setStatus("Canceled");
        // transactionLogs.add(new TransactionLog(returnDateTime, borrower.getStudentId(), borrower.getFullName("fn-mi-ln"), "Returned", transactionHeader.getTransactionId(), transaction.getItemId(), transaction.getItemName(), "Canceled"))
        transactionLogs.add(newTransactionLog);
        logController.setTransactionsLog(transactionLogs);

        logController.updateTransactionLogDatabase();
        transactionLogs.clear();
        logController.clearTransactionLog();
        // transactionController.clearTransactionsHeader();
        // transactionsHeader.clear();
        // TODO: auditLog
        return;
      }
      if (ch == 'Y' && computer != null) {
        inventoryController.returnItem(computer);
        computerTransaction.setReturnDateTime(returnDateTime);
        computerTransaction.setReturned(true);
        transactionLogs.add(newTransactionLog);
        logController.setTransactionsLog(transactionLogs);
        
        
        // UPDATE
        inventoryController.updateComputerDatabase();
        logController.updateTransactionLogDatabase();
        transactionController.updateComputerTransactionsDatabase();

        // CLEAR
        inventoryController.clearComputers();
        transactionController.clearComputerTransactions();
        logController.clearTransactionLog();
        break;
      } else if (ch == 'Y' && laptop != null) {
        inventoryController.returnItem(laptop);
        laptopTransaction.setReturnDateTime(returnDateTime);
        laptopTransaction.setReturned(true);
        transactionLogs.add(newTransactionLog);
        logController.setTransactionsLog(transactionLogs);

        // UPDATE
        inventoryController.updateLaptopDatabase();
        transactionController.updateLaptopTransactionsDatabase();
        logController.updateTransactionLogDatabase();
        
        // CLEAR
        inventoryController.clearLaptops();
        transactionController.clearLaptopTransactions();
        logController.clearTransactionLog();
        break;
      } else if (ch == 'Y' && equipment != null) {
        inventoryController.returnItem(equipment, returningQuantity);
        equipmentTransaction.setBorrowQuantity(equipmentTransaction.getBorrowQuantity() - returningQuantity);
        equipmentTransaction.setReturnDateTime(returnDateTime);
        transactionLogs.add(newTransactionLog);
        logController.setTransactionsLog(transactionLogs);
        if (equipmentTransaction.getBorrowQuantity() == 0) {
          equipmentTransaction.setReturned(true);
        }
        // UPDATE
        inventoryController.updateEquipmentDatabase();
        transactionController.updateEquipmentTransactionsDatabase();
        logController.updateTransactionLogDatabase();

        // CLEAR
        inventoryController.clearEquipments();
        transactionController.clearEquipmentTransactions();
        logController.clearTransactionLog();
        break;
      }
    }
  }

  private void returningForm() {
    new Clrscr();
    new Title();
    System.out.print("Return a resource by transaction ID: ");
    int transactionId = in.nextInt(); in.nextLine();
    
    transactionController.fetchTransactionsHeader();
    TransactionHeader transHeader = transactionController.searchTransactionHeaderById(transactionId);
    transactionController.clearTransactionsHeader();
    if (transHeader == null) {
      System.out.println("Transaction ID does not found.");
      new Getch();
      return;
    }
    if (transHeader.getItemType().equalsIgnoreCase("Computer")) {
      transactionController.fetchComputerTransactionsFromDatabase();
      ComputerTransaction computerTransaction = transactionController.searchComputerTransactionById(transactionId);
      if (computerTransaction.isReturned()) {
        System.out.println("This transaction is completed since " + computerTransaction.getReturnDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")));
        new Getch();
        return;
      }
      int destination = computerTransaction.getDestination();
      int computerId = computerTransaction.getItemId();
      inventoryController.fetchComputersFromDatabase(destination);
      Computer  computer = inventoryController.searchComputer(computerId);
      returningSubmitConfirmation(computer, computerTransaction, transHeader);

    } else if (transHeader.getItemType().equalsIgnoreCase("Laptop")) {
      transactionController.fetchLaptopTransactionsFromDatabase();
      LaptopTransaction laptopTransaction = transactionController.searchLaptopTransactionById(transactionId);
      if (laptopTransaction.isReturned()) {
        System.out.println("This transaction is completed since." + laptopTransaction.getReturnDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")));
        return;
      }
      int laptopId = laptopTransaction.getItemId();
      inventoryController.fetchLaptopsFromDatabase();
      Laptop laptop = inventoryController.searchLaptop(laptopId);
      returningSubmitConfirmation(laptop, laptopTransaction, transHeader);
    
    } else if (transHeader.getItemType().equalsIgnoreCase("Equipment")) {
      transactionController.fetchEquipmentTransactionsFromDatabase();
      EquipmentTransaction equipmentTransaction = transactionController.searchEquipmentTransactionById(transactionId);
      if (equipmentTransaction.isReturned()) {
        System.out.println("This transaction is completed since." + equipmentTransaction.getReturnDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")));
        return;
      }
      int equipmentId = equipmentTransaction.getItemId();
      inventoryController.fetchEquipmentsFromDatabase();
      Equipment equipment = inventoryController.searchEquipment(equipmentId);
      returningSubmitConfirmation(equipment, equipmentTransaction, transHeader);
      
    }
    new Getch();
    inventoryController.clearComputers();
    inventoryController.clearLaptops();
    inventoryController.clearEquipments();
    transactionController.clearComputerTransactions();
    transactionController.clearLaptopTransactions();
    transactionController.clearEquipmentTransactions();
    transactionController.clearTransactionsHeader();
  }
  
  // private void computerReturnForm() {
  //   int destination = 0;
  //   while (destination < 1 || destination > 3) {
  //     System.out.print("Select a Computer Lab (1-3): ");
  //     destination = in.nextInt();
  //   }
  //   new Clrscr();
  //   new Title();
  //   inventoryController.showAvailableComputers(destination);
  //   List<Computer> computers = inventoryController.getComputers();

  //   System.out.println("\n-------------------------------------------");
  //   System.out.print("Select a Computer: ");
  //   int selected = in.nextInt();
  //   String selectedComputer = "PC" + String.valueOf(selected);
  //   in.nextLine();

  //   Computer computerFound = inventoryController.searchComputer(selectedComputer);
  //   if (computerFound != null && computerFound.getName().equalsIgnoreCase(selectedComputer)
  //       && computerFound.getStatus().equalsIgnoreCase("available")) {
  //     System.out.print("Enter Student ID: ");
  //     String studentId = in.nextLine();
  //     System.out.print("Enter Collateral: ");
  //     String collateral = in.nextLine();
  //     submitVerification(studentId, collateral, destination, computerFound);
  //   } else if (computerFound != null && computerFound.getName().equalsIgnoreCase(selectedComputer)
  //       && computerFound.getStatus().equalsIgnoreCase("not available")) {
  //     System.out.println("PC" + selected + " isn't available at the moment.");
  //   } else {
  //     System.out.println("PC" + selected + " does not exist.");
  //   }
  //   new Getch();
  //   computers.clear();
  // }

  // private void laptopReturnForm() {
  //   new Clrscr();
  //   new Title();
  //   inventoryController.showAvailableLaptops();
  //   List<Laptop> laptops = inventoryController.getLaptops();
  //   System.out.print("Select a Laptop: ");
  //   int selected = in.nextInt();
  //   in.nextLine();
  //   String selectedLaptop = String.format("LAPTOP%d", selected);

  //   Laptop laptopFound = inventoryController.searchLaptop(selectedLaptop);

  //   if (laptopFound != null && laptopFound.getStatus().equalsIgnoreCase("available")) {
  //     System.out.print("Enter Student ID: ");
  //     String studentId = in.nextLine();
  //     System.out.print("Enter Collateral: ");
  //     String collateral = in.nextLine();
  //     submitVerification(studentId, collateral, null, laptopFound);
  //   } else if (laptopFound != null && laptopFound.getStatus().equalsIgnoreCase("not available")) {
  //     System.out.println("LAPTOP" + selected + " isn't available at the moment.");
  //   } else {
  //     System.out.println("LAPTOP" + selected + " doesn't exist.");
  //   }
  //   new Getch();
  //   laptops.clear();
  // }

  // private void equipmentReturnForm() {
  //   new Clrscr();
  //   new Title();
  //   inventoryController.showAvailableEquipments();
  //   List<Equipment> equipments = inventoryController.getEquipments();
  //   System.out.print("Select an Equipment by ID: ");
  //   int selected = in.nextInt();

  //   Equipment equipmentFound = inventoryController.searchEquipment(selected);
  //   if (equipmentFound != null && equipmentFound.getStatus().equalsIgnoreCase("available")) {

  //     int quantity = 0;
  //     while (quantity < 1 || quantity > equipmentFound.getAvailableQuantity()) {
  //       System.out.print("Quantity to borrow: ");
  //       quantity = in.nextInt();
  //     }
  //     in.nextLine();
  //     String tempEquipmentName = equipmentFound.getName() + quantity;
  //     equipmentFound.setName(tempEquipmentName);
  //     System.out.print("Enter Student ID: ");
  //     String studentId = in.nextLine();
  //     System.out.print("Enter Collateral: ");
  //     String collateral = in.nextLine();
  //     submitVerification(studentId, collateral, null, equipmentFound);
  //   } else if (equipmentFound != null && equipmentFound.getStatus().equalsIgnoreCase("not available")) {
  //     System.out.println(equipmentFound.getName() + " isn't available at the moment.");
  //   } else {
  //     System.out.println("\nEquipment ID doesn't exist.");
  //   }
  //   new Getch();
  //   equipments.clear();
  // }

  private void viewInventory() {
    while (true) {
      new Clrscr();
      new Title();
      System.out.println("[1] View Computer Inventory");
      System.out.println("[2] View Laptop Inventory");
      System.out.println("[3] View Equipment Inventory");
      System.out.println("[4] Back to Dashboard");
      System.out.print("Select: ");
      int ch = in.nextInt(); in.nextLine();
      
      switch (ch) {
        case 1:
          inventoryController.displayAllComputers();
          inventoryController.clearComputers();
          break;
      
        case 2:
          inventoryController.displayAllLaptops();
          inventoryController.clearLaptops();
          break;
          
        case 3:
          inventoryController.displayAllEquipments();
          inventoryController.clearEquipments();
          break;

        case 4:
          return;

        default:
          System.out.println("Invalid selection! Please select between 1-4.");
          break;
      }
      
    }
  }

  private void computerBorrowersList() {
    new Clrscr();
    new Title();
    transactionController.fetchComputerTransactionsFromDatabase();
    transactionController.displayComputerTransactionsList();
    transactionController.clearComputerTransactions();
  }

  private void laptopBorrowersList() {
    new Clrscr();
    new Title();
    transactionController.fetchLaptopTransactionsFromDatabase();
    transactionController.displayLaptopTransactionsList();
    transactionController.clearLaptopTransactions();
    new Getch();
  }

  private void equipmentBorrowersList() {
    new Clrscr();
    new Title();
    transactionController.fetchEquipmentTransactionsFromDatabase();
    transactionController.displayEquipmentTransactionsList();
    transactionController.clearEquipmentTransactions();
    new Getch();
  }
}