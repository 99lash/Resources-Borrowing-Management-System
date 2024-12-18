import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import custom.utils.Clrscr;
import custom.utils.Getch;
import custom.utils.Title;

public class TransactionController {
  private List<TransactionHeader> transactionsHeader;
  private List<ComputerTransaction> computerTransactions;
  private List<LaptopTransaction> laptopTransactions;
  private List<EquipmentTransaction> equipmentTransactions;

  public TransactionController() {
    transactionsHeader = new ArrayList<>();
    computerTransactions = new ArrayList<>();
    laptopTransactions = new ArrayList<>();
    equipmentTransactions = new ArrayList<>();
  }

  /*
   * public void displayComputerTransactionsList() {
   * System.out.println("List of Computer Borrowers");
   * System.out.println(
   * "+----------+----------------------+--------------------------------+------------+-----------------+----------------------+----------------------+------------+------------+----------+"
   * );
   * System.out.
   * printf("| %-8s | %-20s | %-30s | %-10s | %-15s | %-20s | %-20s | %-10s | %-10s | %-8s |\n"
   * , "Trans ID", "Borrower Student No.", "Borrower Name", "Collateral",
   * "Item Borrowed", "Borrowed Date & Time", "Returned Date & Time", "Issuer",
   * "Reciever", "Status");
   * System.out.println(
   * "+----------+----------------------+--------------------------------+------------+-----------------+----------------------+----------------------+------------+------------+----------+"
   * );
   * if (computerTransactions.isEmpty()) {
   * System.out.
   * println("| No Transaction Records :D \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t     |"
   * );
   * System.out.println(
   * "+----------+----------------------+--------------------------------+------------+-----------------+----------------------+----------------------+------------+------------+----------+"
   * );
   * } else {
   * final int displayCountPerPage = 2;
   * int size = computerTransactions.size();
   * final int displayPages = size / displayCountPerPage;
   * for (int i = 0; i < displayPages;) {
   * int displayCount = 0;
   * for (int j = size-1; j >= 0; j--) {
   * if (displayCount < displayCountPerPage) {
   * computerTransactions.get(j).getTransactionId();
   * int transactionId = computerTransactions.get(j).getTransactionId();
   * String borrowerStudentNo = computerTransactions.get(j).getStudentNo();
   * String borrowerStudentName = computerTransactions.get(j).getStudentName();
   * String collateral = computerTransactions.get(j).getCollateral();
   * String itemName = String.format("%s (CL%d)",
   * computerTransactions.get(j).getItemName(),
   * computerTransactions.get(j).getDestination());
   * DateTimeFormatter formatter =
   * DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
   * String borrowDateTime =
   * computerTransactions.get(j).getBorrowDateTime().format(formatter);
   * String returnDateTime = computerTransactions.get(j).getReturnDateTime() ==
   * null? "N/A" :
   * computerTransactions.get(j).getReturnDateTime().format(formatter);
   * String issuer = computerTransactions.get(j).getIssuer();
   * String reciever = computerTransactions.get(j).getReciever();
   * String status = computerTransactions.get(j).getStatus() == null ? "N/A" :
   * computerTransactions.get(j).getStatus();
   * System.out.
   * printf("| %-8d | %-20s | %-30s | %-10s | %-15s | %-20s | %-20s | %-10s | %-10s | %-8s |\n"
   * , transactionId, borrowerStudentNo, borrowerStudentName, collateral,
   * itemName, borrowDateTime, returnDateTime, issuer, reciever, status);
   * System.out.println(
   * "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
   * );
   * displayCount++;
   * }
   * }
   * }
   * }
   * System.out.println("\n\n");
   * }
   */
  public void displayComputerTransactionsList() {
    Scanner in = new Scanner(System.in);
    final int displayCountPerPage = 10;
    int totalRecords = computerTransactions.size();
    int totalPages = (int) Math.ceil((double) totalRecords / displayCountPerPage);
    int currentPage = 1;
    
    while (true) {  
      try {
        new Clrscr();
        new Title();
        
        int start = (currentPage - 1) * displayCountPerPage;
        int end = Math.min(start + displayCountPerPage, totalRecords);
        System.out.print("\nList of Computer Borrowers");
      System.out.printf("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tShowing %d - %d entries  Page %d of %d\n", start+1, end, currentPage, totalPages);
        System.out.println(
            "+----------+----------------------+--------------------------------+------------+-----------------+----------------------+----------------------+------------+------------+----------+");
        System.out.printf("| %-8s | %-20s | %-30s | %-10s | %-15s | %-20s | %-20s | %-10s | %-10s | %-8s |\n",
            "Trans ID", "Borrower Student No.", "Borrower Name", "Collateral", "Item Borrowed", "Borrowed Date & Time",
            "Returned Date & Time", "Issuer", "Receiver", "Status");
        System.out.println(
            "+----------+----------------------+--------------------------------+------------+-----------------+----------------------+----------------------+------------+------------+----------+");

        if (computerTransactions.isEmpty()) {
          System.out.println(
              "| No Transaction Records :D                                                                                                                     |");
        } else {
          // System.out.println(end);
          for (int i = start; i < end; i++) {
            int transactionId = computerTransactions.get(i).getTransactionId();
            String borrowerStudentNo = computerTransactions.get(i).getStudentNo();
            String borrowerStudentName = computerTransactions.get(i).getStudentName();
            String collateral = computerTransactions.get(i).getCollateral();
            String itemName = String.format("%s (CL%d)", computerTransactions.get(i).getItemName(),
                computerTransactions.get(i).getDestination());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
            String borrowDateTime = computerTransactions.get(i).getBorrowDateTime().format(formatter);
            String returnDateTime = computerTransactions.get(i).getReturnDateTime() == null ? "N/A"
                : computerTransactions.get(i).getReturnDateTime().format(formatter);
            String issuer = computerTransactions.get(i).getIssuer();
            String receiver = computerTransactions.get(i).getReceiver();
            String status = computerTransactions.get(i).getStatus() == null ? "N/A"
                : computerTransactions.get(i).getStatus();
            System.out.printf("| %-8d | %-20s | %-30s | %-10s | %-15s | %-20s | %-20s | %-10s | %-10s | %-8s |\n",
                transactionId, borrowerStudentNo, borrowerStudentName, collateral, itemName, borrowDateTime,
                returnDateTime, issuer, receiver, status);
          }
        }

        System.out.println(
            "+----------+----------------------+--------------------------------+------------+-----------------+----------------------+----------------------+------------+------------+----------+");
        // System.out.printf("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  Page %d of %d\n", currentPage, totalPages);
        if (totalPages == 1) {
          System.out.println("[Q] Quit");
        } else if (currentPage == 1) {
          System.out.println("[N] Next Page | [Q] Quit");
        } else if (currentPage == totalPages) {
          System.out.println("[P] Previous Page | [Q] Quit");
        } else {
          System.out.println("[N] Next Page | [P] Previous Page | [Q] Quit");
        }

        String choice = in.nextLine().trim().toUpperCase();

        if (choice.equals("N")) {
          if (currentPage < totalPages) {
            currentPage++;
          }
        } else if (choice.equals("P")) {
          if (currentPage > 1) {
            currentPage--;
          }
        } else if (choice.equals("Q")) {
          break;
        }
      } catch (Exception e) {
        in.nextLine();
      }
    }
  }

  // public void displayLaptopTransactionsList() {
  //   System.out.println("List of Laptop Borrowers");
  //   System.out.println(
  //       "+----------+----------------------+--------------------------------+------------+-----------------+----------------------+----------------------+------------+------------+----------+");
  //   System.out.printf("| %-8s | %-20s | %-30s | %-10s | %-15s | %-20s | %-20s | %-10s | %-10s | %-8s |\n", "Trans ID",
  //       "Borrower Student No.", "Borrower Name", "Collateral", "Item Borrowed", "Borrowed Date & Time",
  //       "Returned Date & Time", "Issuer", "Reciever", "Status");
  //   System.out.println(
  //       "+----------+----------------------+--------------------------------+------------+-----------------+----------------------+----------------------+------------+------------+----------+");
  //   if (laptopTransactions.isEmpty()) {
  //     System.out.println("| No Transaction Records :D \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t     |");
  //     System.out.println(
  //         "+----------+----------------------+--------------------------------+------------+-----------------+----------------------+----------------------+------------+------------+----------+");
  //   } else {
  //     int size = laptopTransactions.size() - 1;
  //     int displayCount = 1;
  //     for (int i = size; i >= 0; i--) {
  //       if (displayCount > 5)
  //         break;
  //       displayCount++;
  //       laptopTransactions.get(i).getTransactionId();
  //       int transactionId = laptopTransactions.get(i).getTransactionId();
  //       String borrowerStudentNo = laptopTransactions.get(i).getStudentNo();
  //       String borrowerStudentName = laptopTransactions.get(i).getStudentName();
  //       String collateral = laptopTransactions.get(i).getCollateral();
  //       String itemName = laptopTransactions.get(i).getItemName();
  //       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
  //       String borrowDateTime = laptopTransactions.get(i).getBorrowDateTime().format(formatter);
  //       String returnDateTime = laptopTransactions.get(i).getReturnDateTime() == null ? "N/A"
  //           : laptopTransactions.get(i).getReturnDateTime().format(formatter);
  //       String issuer = laptopTransactions.get(i).getIssuer();
  //       String reciever = laptopTransactions.get(i).getReceiver();
  //       String status = laptopTransactions.get(i).getStatus() == null ? "N/A" : laptopTransactions.get(i).getStatus();
  //       System.out.printf("| %-8d | %-20s | %-30s | %-10s | %-15s | %-20s | %-20s | %-10s | %-10s | %-8s |\n",
  //           transactionId, borrowerStudentNo, borrowerStudentName, collateral, itemName, borrowDateTime, returnDateTime,
  //           issuer, reciever, status);
  //       System.out.println(
  //           "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
  //     }
  //   }
  //   System.out.println("\n\n");
  // }
  public void displayLaptopTransactionsList() {
    Scanner in = new Scanner(System.in);
    final int displayCountPerPage = 10;
    int totalRecords = laptopTransactions.size();
    int totalPages = (int) Math.ceil((double) totalRecords / displayCountPerPage);
    int currentPage = 1;
    while (true) {
      try {
        new Clrscr();
        new Title();
        int start = (currentPage - 1) * displayCountPerPage;
        int end = Math.min(start + displayCountPerPage, totalRecords);
        System.out.print("\nList of Laptop Borrowers");
        System.out.printf("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tShowing %d - %d entries  Page %d of %d\n", start+1, end, currentPage, totalPages);
        System.out.println(
            "+----------+----------------------+--------------------------------+------------+-----------------+----------------------+----------------------+------------+------------+----------+");
        System.out.printf("| %-8s | %-20s | %-30s | %-10s | %-15s | %-20s | %-20s | %-10s | %-10s | %-8s |\n",
            "Trans ID", "Borrower Student No.", "Borrower Name", "Collateral", "Item Borrowed", "Borrowed Date & Time",
            "Returned Date & Time", "Issuer", "Receiver", "Status");
        System.out.println(
            "+----------+----------------------+--------------------------------+------------+-----------------+----------------------+----------------------+------------+------------+----------+");

        if (laptopTransactions.isEmpty()) {
          System.out.println(
              "| No Transaction Records :D                                                                                                                     |");
        } else {
          for (int i = start; i < end; i++) {
            int transactionId = laptopTransactions.get(i).getTransactionId();
            String borrowerStudentNo = laptopTransactions.get(i).getStudentNo();
            String borrowerStudentName = laptopTransactions.get(i).getStudentName();
            String collateral = laptopTransactions.get(i).getCollateral();
            String itemName = laptopTransactions.get(i).getItemName();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
            String borrowDateTime = laptopTransactions.get(i).getBorrowDateTime().format(formatter);
            String returnDateTime = laptopTransactions.get(i).getReturnDateTime() == null ? "N/A"
                : laptopTransactions.get(i).getReturnDateTime().format(formatter);
            String issuer = laptopTransactions.get(i).getIssuer();
            String receiver = laptopTransactions.get(i).getReceiver();
            String status = laptopTransactions.get(i).getStatus() == null ? "N/A"
                : laptopTransactions.get(i).getStatus();
            System.out.printf("| %-8d | %-20s | %-30s | %-10s | %-15s | %-20s | %-20s | %-10s | %-10s | %-8s |\n",
                transactionId, borrowerStudentNo, borrowerStudentName, collateral, itemName, borrowDateTime,
                returnDateTime, issuer, receiver, status);
          }
        }

        System.out.println(
            "+----------+----------------------+--------------------------------+------------+-----------------+----------------------+----------------------+------------+------------+----------+");
        // System.out.printf("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  Page %d of %d\n", currentPage, totalPages);
        if (totalPages == 1) {
          System.out.println("[Q] Quit");
        } else if (currentPage == 1) {
          System.out.println("[N] Next Page | [Q] Quit");
        } else if (currentPage == totalPages) {
          System.out.println("[P] Previous Page | [Q] Quit");
        } else {
          System.out.println("[N] Next Page | [P] Previous Page | [Q] Quit");
        }

        String choice = in.nextLine().trim().toUpperCase();

        if (choice.equals("N")) {
          if (currentPage < totalPages) {
            currentPage++;
          }
        } else if (choice.equals("P")) {
          if (currentPage > 1) {
            currentPage--;
          }
        } else if (choice.equals("Q")) {
          break;
        }
      } catch (Exception e) {
        in.nextLine();
      }
    }
  }
  // public void displayEquipmentTransactionsList() {
  //   System.out.println("List of Equipment Borrowers");
  //   System.out.println(
  //       "+----------+----------------------+--------------------------------+------------+----------------------+----------+----------------------+----------------------+------------+------------+----------+");
  //   System.out.printf("| %-8s | %-20s | %-30s | %-10s | %-20s | %-8s | %-20s | %-20s | %-10s | %-10s | %-8s |\n",
  //       "Trans ID", "Borrower Student No.", "Borrower Name", "Collateral", "Item Borrowed", "Quantity",
  //       "Borrowed Date & Time", "Returned Date & Time", "Issuer", "Reciever", "Status");
  //   System.out.println(
  //       "+----------+----------------------+--------------------------------+------------+----------------------+----------+----------------------+----------------------+------------+------------+----------+");
  //   if (equipmentTransactions.isEmpty()) {
  //     System.out.println("| No Transaction Records :D \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t      |");
  //     System.out.println(
  //         "+----------+----------------------+--------------------------------+------------+----------------------+----------+----------------------+----------------------+------------+------------+----------+");
  //   } else {
  //     int size = equipmentTransactions.size() - 1;
  //     int displayCount = 1;
  //     for (int i = size; i >= 0; i--) {
  //       if (displayCount > 5)
  //         break;
  //       displayCount++;
  //       equipmentTransactions.get(i).getTransactionId();
  //       int transactionId = equipmentTransactions.get(i).getTransactionId();
  //       String borrowerStudentNo = equipmentTransactions.get(i).getStudentNo();
  //       String borrowerStudentName = equipmentTransactions.get(i).getStudentName();
  //       String collateral = equipmentTransactions.get(i).getCollateral();
  //       String itemName = equipmentTransactions.get(i).getItemName();
  //       int borrowQuantity = equipmentTransactions.get(i).getBorrowQuantity();
  //       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
  //       String borrowDateTime = equipmentTransactions.get(i).getBorrowDateTime().format(formatter);
  //       String returnDateTime = equipmentTransactions.get(i).getReturnDateTime() == null ? "N/A"
  //           : equipmentTransactions.get(i).getReturnDateTime().format(formatter);
  //       String issuer = equipmentTransactions.get(i).getIssuer();
  //       String reciever = equipmentTransactions.get(i).getReceiver();
  //       String status = borrowQuantity != 0 ? "Borrowed" : "Returned";
  //       System.out.printf("| %-8d | %-20s | %-30s | %-10s | %-20s | %-8d | %-20s | %-20s | %-10s | %-10s | %-8s |\n",
  //           transactionId, borrowerStudentNo, borrowerStudentName, collateral, itemName, borrowQuantity, borrowDateTime,
  //           returnDateTime, issuer, reciever, status);
  //       System.out.println(
  //           "+----------+----------------------+--------------------------------+------------+----------------------+----------+----------------------+----------------------+------------+------------+----------+");
  //     }
  //   }
  //   System.out.println("\n\n");
  // }
  public void displayEquipmentTransactionsList() {
    Scanner in = new Scanner(System.in);
    final int displayCountPerPage = 10;
    int totalRecords = equipmentTransactions.size();
    int totalPages = (int) Math.ceil((double) totalRecords / displayCountPerPage);
    int currentPage = 1;
    while (true) {
      try {
        new Clrscr();
        new Title();
        int start = (currentPage - 1) * displayCountPerPage;
        int end = Math.min(start + displayCountPerPage, totalRecords);
        System.out.print("\nList of Equipment Borrowers");
        System.out.printf("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tShowing %d - %d entries  Page %d of %d\n", start+1, end, currentPage, totalPages);
        System.out.println(
            "+----------+----------------------+--------------------------------+------------+----------------------+----------+----------------------+----------------------+------------+------------+----------+");
        System.out.printf("| %-8s | %-20s | %-30s | %-10s | %-20s | %-8s | %-20s | %-20s | %-10s | %-10s | %-8s |\n","Trans ID", "Borrower Student No.", "Borrower Name", "Collateral", "Item Borrowed", "Quantity",
            "Borrowed Date & Time", "Returned Date & Time", "Issuer", "Receiver", "Status");
        System.out.println("+----------+----------------------+--------------------------------+------------+----------------------+----------+----------------------+----------------------+------------+------------+----------+");

        if (equipmentTransactions.isEmpty()) {
          System.out.println(
              "| No Transaction Records :D                                                                                                                     |");
        } else {
          for (int i = start; i < end; i++) {
            int transactionId = equipmentTransactions.get(i).getTransactionId();
            String borrowerStudentNo = equipmentTransactions.get(i).getStudentNo();
            String borrowerStudentName = equipmentTransactions.get(i).getStudentName();
            String collateral = equipmentTransactions.get(i).getCollateral();
            String itemName = equipmentTransactions.get(i).getItemName();
            int borrowQuantity = equipmentTransactions.get(i).getBorrowQuantity();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
            String borrowDateTime = equipmentTransactions.get(i).getBorrowDateTime().format(formatter);
            String returnDateTime = equipmentTransactions.get(i).getReturnDateTime() == null ? "N/A"
                : equipmentTransactions.get(i).getReturnDateTime().format(formatter);
            String issuer = equipmentTransactions.get(i).getIssuer();
            String receiver = equipmentTransactions.get(i).getReceiver();
            String status = borrowQuantity != 0 ? "Borrowed" : "Returned";
            System.out.printf("| %-8d | %-20s | %-30s | %-10s | %-20s | %-8d | %-20s | %-20s | %-10s | %-10s | %-8s |\n",
                transactionId, borrowerStudentNo, borrowerStudentName, collateral, itemName, borrowQuantity, borrowDateTime,
                returnDateTime, issuer, receiver, status);
          }
        }

        System.out.println(
            "+----------+----------------------+--------------------------------+------------+----------------------+----------+----------------------+----------------------+------------+------------+----------+");
        // System.out.printf("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  Page %d of %d\n", currentPage, totalPages);
        if (totalPages == 1) {
          System.out.println("[Q] Quit");
        } else if (currentPage == 1) {
          System.out.println("[N] Next Page | [Q] Quit");
        } else if (currentPage == totalPages) {
          System.out.println("[P] Previous Page | [Q] Quit");
        } else {
          System.out.println("[N] Next Page | [P] Previous Page | [Q] Quit");
        }

        String choice = in.nextLine().trim().toUpperCase();

        if (choice.equals("N")) {
          if (currentPage < totalPages) {
            currentPage++;
          }
        } else if (choice.equals("P")) {
          if (currentPage > 1) {
            currentPage--;
          }
        } else if (choice.equals("Q")) {
          break;
        }
      } catch (Exception e) {
        in.nextLine();
      }
    }
  }

  public TransactionHeader searchTransactionHeaderById(int transactionId) {
    for (TransactionHeader transheader : transactionsHeader) {
      if (transheader.getTransactionId() == transactionId) {
        return transheader;
      }
    }
    return null;
  }

  public ComputerTransaction searchComputerTransactionById(int transactionId) {
    for (ComputerTransaction computerTransaction : computerTransactions) {
      if (computerTransaction.getTransactionId() == transactionId) {
        return computerTransaction;
      }
    }
    return null;
  }

  public LaptopTransaction searchLaptopTransactionById(int transactionId) {
    for (LaptopTransaction laptopTransaction : laptopTransactions) {
      if (laptopTransaction.getTransactionId() == transactionId) {
        return laptopTransaction;
      }
    }
    return null;
  }

  public EquipmentTransaction searchEquipmentTransactionById(int transactionId) {
    for (EquipmentTransaction equipmentTransaction : equipmentTransactions) {
      if (equipmentTransaction.getTransactionId() == transactionId) {
        return equipmentTransaction;
      }
    }
    return null;
  }

  public void clearTransactionsHeader() {
    transactionsHeader.clear();
  }

  public void clearComputerTransactions() {
    computerTransactions.clear();
  }

  public void clearLaptopTransactions() {
    laptopTransactions.clear();
  }

  public void clearEquipmentTransactions() {
    equipmentTransactions.clear();
  }

  public void fetchTransactionsHeader() {
    String filepath = "./res/data/transaction/transaction_header.csv";
    try {
      Scanner reader = new Scanner(new File(filepath));
      boolean header = true;

      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        String[] fields = line.split(",");

        if (!header) {
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
          int transactionId = Integer.parseInt(fields[0]);
          LocalDateTime transDateTime = LocalDateTime.parse(fields[1], formatter);
          String borrowerName = fields[2];
          String itemType = fields[3];
          transactionsHeader.add(new TransactionHeader(transactionId, transDateTime, borrowerName, itemType));
        } else {
          header = false;
        }
      }
      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void fetchComputerTransactionsFromDatabase() {
    String filepath = "./res/data/transaction/computer_transaction.csv";
    try {
      Scanner reader = new Scanner(new File(filepath));
      boolean header = true;

      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        String[] fields = line.split(",");

        if (!header) {
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
          int transactionId = Integer.parseInt(fields[0]);
          String studentNo = fields[1];
          String studentName = fields[2];
          String collateral = fields[3];
          int itemId = Integer.parseInt(fields[4]);
          String itemName = fields[5];
          int itemDestination = Integer.parseInt(fields[6]);
          LocalDateTime borrowDateTime = LocalDateTime.parse(fields[7], formatter);
          LocalDateTime returnDateTime = fields[8].equalsIgnoreCase("N/A") ? null
              : LocalDateTime.parse(fields[8], formatter);
          String issuer = fields[9];
          String reciever = fields[10];
          String status = fields[11];
          computerTransactions.add(new ComputerTransaction(transactionId, studentNo, studentName, collateral, itemId,
              itemName, borrowDateTime, returnDateTime, issuer, reciever, itemDestination, status));
        } else {
          header = false;
        }
      }
      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void fetchLaptopTransactionsFromDatabase() {
    String filepath = "./res/data/transaction/laptop_transaction.csv";

    try {
      Scanner reader = new Scanner(new File(filepath));
      boolean header = true;

      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        String[] fields = line.split(",");

        if (!header) {
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
          int transactionId = Integer.parseInt(fields[0]);
          String studentNo = fields[1];
          String studentName = fields[2];
          String collateral = fields[3];
          int itemId = Integer.parseInt(fields[4]);
          String itemName = fields[5];
          LocalDateTime borrowDateTime = LocalDateTime.parse(fields[6], formatter);
          LocalDateTime returnDateTime = fields[7].equalsIgnoreCase("N/A") ? null
              : LocalDateTime.parse(fields[7], formatter);
          String issuer = fields[8];
          String reciever = fields[9];
          String status = fields[10];
          laptopTransactions.add(new LaptopTransaction(transactionId, studentNo, studentName, collateral, itemId,
              itemName, borrowDateTime, returnDateTime, issuer, reciever, status));
        } else {
          header = false;
        }
      }
      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
  }

  public void fetchEquipmentTransactionsFromDatabase() {
    String filepath = "./res/data/transaction/equipment_transaction.csv";
    try {
      Scanner reader = new Scanner(new File(filepath));
      boolean header = true;

      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        String[] fields = line.split(",");

        if (!header) {
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
          int transactionId = Integer.parseInt(fields[0]);
          String studentNo = fields[1];
          String studentName = fields[2];
          String collateral = fields[3];
          int itemId = Integer.parseInt(fields[4]);
          String itemName = fields[5];
          int borrowQuantity = Integer.parseInt(fields[6]);
          LocalDateTime borrowDateTime = LocalDateTime.parse(fields[7], formatter);
          LocalDateTime returnDateTime = fields[8].equalsIgnoreCase("N/A") ? null
              : LocalDateTime.parse(fields[8], formatter);
          String issuer = fields[9];
          String reciever = fields[10];
          String status = fields[11];
          equipmentTransactions.add(new EquipmentTransaction(transactionId, studentNo, studentName, collateral, itemId,
              itemName, borrowDateTime, returnDateTime, issuer, reciever, status, borrowQuantity));
        } else {
          header = false;
        }
      }
      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void updateTransactionsHeader() {
    String origFilepath = "./res/data/transaction/transaction_header.csv";
    String tempFilepath = "./res/data/transaction/transaction_header_temp.csv";

    try {
      Scanner reader = new Scanner(new File(origFilepath));
      PrintWriter writer = new PrintWriter(new File(tempFilepath));

      String line = reader.nextLine();
      writer.print(line);

      for (TransactionHeader transactionHeader : transactionsHeader) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
        int transactionId = transactionHeader.getTransactionId();
        String borrowDateTime = transactionHeader.getTransDateTime().format(formatter);
        String borrowerName = transactionHeader.getBorrowerName();
        String itemType = transactionHeader.getItemType();
        writer.printf("\n%d,%s,%s,%s", transactionId, borrowDateTime, borrowerName, itemType);
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
      } else {
        // 404R: Couldn't rename file
        System.out.println("[HEADER UPDATE STATUS: 404R]");
      }
    } else {
      // 404D: Couldn't delete file
      System.out.println("[HEADER UPDATE STATUS: 404D]");
    }
  }

  public void updateComputerTransactionsDatabase() {
    String origFilepath = "./res/data/transaction/computer_transaction.csv";
    String tempFilepath = "./res/data/transaction/computer_transaction_temp.csv";

    try {
      Scanner reader = new Scanner(new File(origFilepath));
      PrintWriter writer = new PrintWriter(new File(tempFilepath));

      String line = reader.nextLine();
      writer.print(line);

      for (ComputerTransaction computerTransaction : computerTransactions) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
        int transactionId = computerTransaction.getTransactionId();
        String studentNo = computerTransaction.getStudentNo();
        String studentName = computerTransaction.getStudentName();
        String collateral = computerTransaction.getCollateral();
        int itemId = computerTransaction.getItemId();
        String itemName = computerTransaction.getItemName();
        int itemDestination = computerTransaction.getDestination();
        String borrowDateTime = computerTransaction.getBorrowDateTime().format(formatter);
        String returnDateTime = (computerTransaction.getReturnDateTime() != null)
            ? computerTransaction.getReturnDateTime().format(formatter)
            : "N/A";
        String issuer = computerTransaction.getIssuer();
        String reciever = computerTransaction.getReceiver();
        String status = computerTransaction.getStatus();
        writer.printf("\n%d,%s,%s,%s,%d,%s,%d,%s,%s,%s,%s,%s", transactionId, studentNo, studentName, collateral,
            itemId, itemName, itemDestination, borrowDateTime, returnDateTime, issuer, reciever, status);
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
      } else {
        // 404R: Couldn't rename file
        System.out.println("[COMPUTER TRANSAC STATUS: 404R]");
      }
    } else {
      // 404D: Couldn't delete file
      System.out.println("[COMPUTER TRANSAC STATUS: 404D]");
    }
  }

  public void updateLaptopTransactionsDatabase() {
    String origFilepath = "./res/data/transaction/laptop_transaction.csv";
    String tempFilepath = "./res/data/transaction/laptop_transaction_temp.csv";

    try {
      Scanner reader = new Scanner(new File(origFilepath));
      PrintWriter writer = new PrintWriter(new File(tempFilepath));

      String line = reader.nextLine();
      writer.print(line);

      for (LaptopTransaction laptopTransaction : laptopTransactions) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
        int transactionId = laptopTransaction.getTransactionId();
        String studentNo = laptopTransaction.getStudentNo();
        String studentName = laptopTransaction.getStudentName();
        String collateral = laptopTransaction.getCollateral();
        int itemId = laptopTransaction.getItemId();
        String itemName = laptopTransaction.getItemName();
        String borrowDateTime = laptopTransaction.getBorrowDateTime().format(formatter);
        String returnDateTime = (laptopTransaction.getReturnDateTime() != null)
            ? laptopTransaction.getReturnDateTime().format(formatter)
            : "N/A";
        String issuer = laptopTransaction.getIssuer();
        String reciever = laptopTransaction.getReceiver();
        String status = laptopTransaction.getStatus();
        writer.printf("\n%d,%s,%s,%s,%d,%s,%s,%s,%s,%s,%s", transactionId, studentNo, studentName, collateral, itemId,
            itemName, borrowDateTime, returnDateTime, issuer, reciever, status);
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
      } else {
        // 404R: Couldn't rename file
        System.out.println("[LAPTOP TRANSAC STATUS: 404R]");
      }
    } else {
      // 404D: Couldn't delete file
      System.out.println("[LAPTOP TRANSACSTATUS: 404D]");
    }
  }

  public void updateEquipmentTransactionsDatabase() {
    String origFilepath = "./res/data/transaction/equipment_transaction.csv";
    String tempFilepath = "./res/data/transaction/equipment_transaction_temp.csv";

    try {
      Scanner reader = new Scanner(new File(origFilepath));
      PrintWriter writer = new PrintWriter(new File(tempFilepath));

      String line = reader.nextLine();
      writer.print(line);

      for (EquipmentTransaction equipmentTransaction : equipmentTransactions) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
        int transactionId = equipmentTransaction.getTransactionId();
        String studentNo = equipmentTransaction.getStudentNo();
        String studentName = equipmentTransaction.getStudentName();
        String collateral = equipmentTransaction.getCollateral();
        int itemId = equipmentTransaction.getItemId();
        String itemName = equipmentTransaction.getItemName();
        int borrowQuantity = equipmentTransaction.getBorrowQuantity();
        String borrowDateTime = equipmentTransaction.getBorrowDateTime().format(formatter);
        String returnDateTime = (equipmentTransaction.getReturnDateTime() != null)
            ? equipmentTransaction.getReturnDateTime().format(formatter)
            : "N/A";
        String issuer = equipmentTransaction.getIssuer();
        String reciever = equipmentTransaction.getReceiver();
        String status = equipmentTransaction.getStatus();
        writer.printf("\n%d,%s,%s,%s,%d,%s,%d,%s,%s,%s,%s,%s", transactionId, studentNo, studentName, collateral,
            itemId, itemName, borrowQuantity, borrowDateTime, returnDateTime, issuer, reciever, status);
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
      } else {
        // 404R: Couldn't rename file
        System.out.println("[EQUIPMENT TRANSAC STATUS: 404R]");
      }
    } else {
      // 404D: Couldn't delete file
      System.out.println("[EQUIPMENT TRANSAC STATUS: 404D]");
    }
  }

  // GETTERS and SETTERS
  public List<TransactionHeader> getTransactionsHeader() {
    return transactionsHeader;
  }

  public void setTransactionsHeader(List<TransactionHeader> transactionsHeader) {
    this.transactionsHeader = transactionsHeader;
  }

  public List<ComputerTransaction> getComputerTransactions() {
    return computerTransactions;
  }

  public void setComputerTransactions(List<ComputerTransaction> computerTransactions) {
    this.computerTransactions = computerTransactions;
  }

  public List<LaptopTransaction> getLaptopTransactions() {
    return laptopTransactions;
  }

  public void setLaptopTransactions(List<LaptopTransaction> laptopTransactions) {
    this.laptopTransactions = laptopTransactions;
  }

  public List<EquipmentTransaction> getEquipmentTransactions() {
    return equipmentTransactions;
  }

  public void setEquipmentTransactions(List<EquipmentTransaction> equipmentTransactions) {
    this.equipmentTransactions = equipmentTransactions;
  }
}