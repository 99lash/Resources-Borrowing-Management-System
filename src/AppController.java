import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
      System.out.println("Hello, " + username + "! Your role is invalid.\nPlease reach out to your administrator to fix your role.");
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
      s += String.format("[%s7%s] View Admin Operations\n", GREEN, RESET);
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
      System.out.println("\t\tWelcome back, " + username + "!\n\n");
      if (show) {
        logController.fetchTransactionLogsFromDatabase();
        logController.displayAllTranscationLogs();
        logController.clearTransactionLog();
      } else {
        System.out.println("Transactions Log (Hidden)\n\n\n\n\n\n\n\n");
      }
      System.out.print(stringMenu(switchEvent,"admin"));
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
            new Main().main(null); // may bug dito. flow kapag nag exit babalik sa dito sa function ManageAccount() na ito. 
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

  public void staffDashboard(String username) {
    boolean running = true;
    boolean show = true;
    while (running) {
      new Clrscr();
      new Title();
      String switchEvent = show ? "Hide" : "Show";
      if (show) {
        logController.fetchTransactionLogsFromDatabase();
        logController.displayAllTranscationLogs();
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
          System.out.println("View inventory");
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

  public void submitVerification(String studentId, String collateral, Integer destination, Resource resource) {
    studentController.fetchStudentsFromDatabase();
    Map<Integer, Student> students = studentController.getStudents();
    Student borrower = null;
    char ch = ' ';

    while (ch != 'Y' || ch != 'N') {
      new Clrscr();
      for (Student student : students.values()) {
        if (student.getStudentId().equalsIgnoreCase(studentId)) {
          borrower = student;
        }
      }
      if (borrower == null) {
        System.out.println("No record found.");
        break;
      }
      Computer computer = null;
      Laptop laptop = null;
      Equipment equipment = null;
      int borrowQuantity = 0;
      System.out.println("Review Details");
      System.out.println("-------------------------------");
      // System.out.println(resource instanceof Computer);
      if (resource instanceof Computer) {
        computer = ((Computer) resource);
        System.out.println(String.format("Borrowed Resource: %s (CL%d)", computer.getName(), computer.getDestination()));
      } else if (resource instanceof Laptop) {
        laptop = ((Laptop) resource);
        System.out.println("Borrowed Resource: " + laptop.getName());
      } else if (resource instanceof Equipment) {
        equipment = ((Equipment) resource);
        borrowQuantity = Character.getNumericValue(equipment.getName().charAt(equipment.getName().length()-1));
        String originalEquipmentName = equipment.getName().replace(String.valueOf(borrowQuantity), "");
        equipment.setName(originalEquipmentName);
        System.out.println("Borrowed Resource: " + equipment.getName());
        System.out.println("Borrowed Quantity: " + borrowQuantity);
      }
      System.out.println("Student ID: " + borrower.getStudentId());
      System.out.println("Full Name: " + borrower.getFullName());
      System.out.println("Course/Year/Section: " + borrower.getCourse() + " - " + borrower.getYear() + borrower.getSection());
      System.out.println("Department: " + borrower.getDepartment());
      System.out.println("Collateral: " + collateral);
      System.out.print("Submit Verification [Y/N]: ");
      ch = in.nextLine().toUpperCase().charAt(0);
      if (ch == 'N') {
        System.out.println("Borrow Transaction Cancelled.");
        break;
      }  

      logController.fetchTransactionLogsFromDatabase();
      List<TransactionLog> transactionLogs = logController.getTransactionsLog();
      transactionController.fetchTransactionsHeader();
      List<TransactionHeader> transactionsHeader = transactionController.getTransactionsHeader();
      TransactionHeader newTransactionHeader = new TransactionHeader(LocalDateTime.now(), borrower.getFullName("fn-mi-ln"));
      transactionsHeader.add(newTransactionHeader);
      if (ch == 'Y' && computer != null) {
        // FETCH
        transactionController.fetchComputerTransactionsFromDatabase(); // * Nireretrieve nito yung mga data from computer transactions database then isasalin sa array
        
        
        // GET LIST
        List<ComputerTransaction> computerTransactions = transactionController.getComputerTransactions(); // * Dito isinalin na natin sa array
        
        // ADD
        inventoryController.borrow(computer); // * borrow a computer, pinalitan lang status ng computer na napili (Available -> Not Available)
        ComputerTransaction newComputerTransaction = new ComputerTransaction(newTransactionHeader.getTransactionId(), borrower.getStudentId(), borrower.getFullName("fn-mi-ln"), collateral, computer.getId(), computer.getName(), newTransactionHeader.getTransDateTime(), null, currentUser.getUsername(), "N/A", destination, "borrowed");
        computerTransactions.add(newComputerTransaction); // * Here we add a new record of successful computer transactions pero sa array muna.
        String itemNameAndDestination = String.format("%s (CL%d)", computer.getName(), destination);
        // transactionLogs.add(new TransactionLog(newComputerTransaction.getTransactionId(), borrower.getStudentId(), borrower.getFullName("fn-mi-ln"), collateral, itemNameAndDestination, newComputerTransaction.getBorrowDateTime(), null, newComputerTransaction.getIssuer(), "N/A"));
        transactionLogs.add(new TransactionLog(newTransactionHeader.getTransDateTime(), borrower.getStudentId(), borrower.getFullName("fn-mi-ln"), "Borrowed", newTransactionHeader.getTransactionId(), computer.getId(), itemNameAndDestination, "Success"));
        // UPDATE
        inventoryController.updateComputerDatabase(); // !!! dito ibang way ang ginamit ko sa pag update ko ng computer inventory sa database
        transactionController.updateTransactionsHeader();
        transactionController.updateComputerTransactionsDatabase(); // * Dito i-update na natin yung database ni computer transactions, yung data na mapupunta sa databasae ay galing sa array
        logController.updateTransactionLogDatabase();
        
        // CLEAR
        inventoryController.clearComputers();
        transactionController.clearComputerTransactions(); // * Clear the array kung saan natin inisstore yung mga data from database. Ito ay para sa next update natin hindi mag duplicate.
        logController.clearTransactionLog();
        transactionController.clearTransactionsHeader();
        break;
      } else if (ch == 'Y' && laptop != null) {
        // FETCH
        transactionController.fetchLaptopTransactionsFromDatabase();
        
        // GET LIST
        List<LaptopTransaction> laptopTransactions = transactionController.getLaptopTransactions();
        
        // ADD
        inventoryController.borrow(laptop);
        LaptopTransaction newLaptopTransaction = new LaptopTransaction(newTransactionHeader.getTransactionId(), borrower.getStudentId(), borrower.getFullName("fn-mi-ln"), collateral, laptop.getId(), laptop.getName(), newTransactionHeader.getTransDateTime(), null, currentUser.getUsername(), "N/A", "Borrowed");
        // LaptopTransaction  newLaptopTransaction = new LaptopTransaction(newTransactionHeader.getTransactionId(), borrower.getStudentId(), borrower.getFullName("fn-mi-ln"), collateral, laptop.getId(), laptop.getName(), newTransactionHeader.getTransDateTime(), null, currentUser.getUsername(), "N/A", "Borrowed");
        laptopTransactions.add(newLaptopTransaction);
        // transactionLogs.add(new TransactionLog(newLaptopTransaction.getTransactionId(), borrower.getStudentId(), borrower.getFullName("fn-mi-ln"), collateral, laptop.getName(), newLaptopTransaction.getBorrowDateTime(), null, newLaptopTransaction.getIssuer(), "N/A"));
        transactionLogs.add(new TransactionLog(newTransactionHeader.getTransDateTime(), borrower.getStudentId(), borrower.getFullName("fn-mi-ln"), "Borrowed", newTransactionHeader.getTransactionId(), laptop.getId(), laptop.getName(), "Success"));
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
        inventoryController.borrow(equipment, borrowQuantity);
        EquipmentTransaction newEquipmentTransaction = new EquipmentTransaction(newTransactionHeader.getTransactionId(), borrower.getStudentId(), borrower.getFullName("fn-mi-ln"), collateral, equipment.getId(), equipment.getName(), newTransactionHeader.getTransDateTime(), null, currentUser.getUsername(), "N/A", "Borrowed", borrowQuantity );
        equipmentTransactions.add(newEquipmentTransaction);
        // transactionLogs.add(new TransactionLog(newEquipmentTransaction.getTransactionId(), borrower.getStudentId(), borrower.getFullName("fn-mi-ln"), collateral, equipment.getName(), newEquipmentTransaction.getBorrowDateTime(), null, newEquipmentTransaction.getIssuer(), "N/A"));
        transactionLogs.add(new TransactionLog(newTransactionHeader.getTransDateTime(), borrower.getStudentId(), borrower.getFullName("fn-mi-ln"), "Borrowed", newTransactionHeader.getTransactionId(), equipment.getId(), equipment.getName(), "Success"));
        
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
      System.out.println("[1] PC");
      System.out.println("[2] Laptop");
      System.out.println("[3] Other Equipments");
      System.out.println("[4] Cancel");
      System.out.print("Select a type of resources: ");
      int ch = in.nextInt();
      // in.nextLine();
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

  public void computerBorrowForm() {
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
      if (computerFound != null && computerFound.getName().equalsIgnoreCase(selectedComputer) && computerFound.getStatus().equalsIgnoreCase("available")) {
        System.out.print("Enter Student ID: ");
        String studentId = in.nextLine();
        System.out.print("Enter Collateral: ");
        String collateral = in.nextLine();
        submitVerification(studentId, collateral, destination, computerFound);
      } else if (computerFound != null && computerFound.getName().equalsIgnoreCase(selectedComputer) && computerFound.getStatus().equalsIgnoreCase("not available")) {
        System.out.println("PC" + selected + " isn't available at the moment.");
      } else {
        System.out.println("PC" + selected + " does not exist.");
      }
    new Getch();
    computers.clear();
  }

  public void laptopBorrowForm() {
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
      submitVerification(studentId, collateral, null, laptopFound);
    } else if (laptopFound != null && laptopFound.getStatus().equalsIgnoreCase("not available")) {
      System.out.println("LAPTOP" + selected + " isn't available at the moment.");
    } else {
      System.out.println("LAPTOP" + selected + " doesn't exist.");
    }
    new Getch();
    laptops.clear();
  }

  public void equipmentBorrowForm() {
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
      submitVerification(studentId, collateral, null, equipmentFound);
    } else if (equipmentFound != null && equipmentFound.getStatus().equalsIgnoreCase("not available")) {
      System.out.println(equipmentFound.getName() + " isn't available at the moment.");
    } else {
      System.out.println("\nEquipment ID doesn't exist.");
    }
    new Getch();
    equipments.clear();
  }

  private void returningForm() {
    System.out.println("--Returning Form--");
  }

  public void computerBorrowersList() {
    new Clrscr();
    new Title();
    transactionController.fetchComputerTransactionsFromDatabase();
    transactionController.displayComputerTransactionsList();
    transactionController.clearComputerTransactions();
    new Getch();
  } 

  public void laptopBorrowersList() {
    new Clrscr();
    new Title();
    transactionController.fetchLaptopTransactionsFromDatabase();
    transactionController.displayLaptopTransactionsList();
    transactionController.clearLaptopTransactions();
    new Getch();
  }

  public void equipmentBorrowersList() {
    new Clrscr();
    new Title();
    transactionController.fetchEquipmentTransactionsFromDatabase();
    transactionController.displayEquipmentTransactionsList();
    transactionController.clearEquipmentTransactions();
    new Getch();
  }
}