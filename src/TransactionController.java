import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
         transactionsHeader.add(new TransactionHeader(transactionId, transDateTime, borrowerName));
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
          LocalDateTime returnDateTime = fields[8].equalsIgnoreCase("N/A") ? null : LocalDateTime.parse(fields[8], formatter);
          String issuer = fields[9];
          String reciever = fields[10];
          String status = fields[11];
          computerTransactions.add(new ComputerTransaction(transactionId, studentNo, studentName, collateral, itemId, itemName, borrowDateTime, returnDateTime, issuer, reciever, itemDestination, status));
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
          LocalDateTime returnDateTime = fields[7].equalsIgnoreCase("N/A") ? null : LocalDateTime.parse(fields[7], formatter);
          String issuer = fields[8];
          String reciever = fields[9];
          String status = fields[10];
          laptopTransactions.add(new LaptopTransaction(transactionId, studentNo, studentName, collateral, itemId, itemName, borrowDateTime, returnDateTime, issuer, reciever, status));
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
          LocalDateTime returnDateTime = fields[8].equalsIgnoreCase("N/A") ? null : LocalDateTime.parse(fields[8], formatter);
          String issuer = fields[9];
          String reciever = fields[10];
          String status = fields[11];
          equipmentTransactions.add(new EquipmentTransaction(transactionId, studentNo, studentName, collateral, itemId, itemName, borrowDateTime, returnDateTime, issuer, reciever, status, borrowQuantity));
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
        writer.printf("\n%d,%s,%s", transactionId, borrowDateTime, borrowerName);
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
        System.out.println("[HEADER STATUS: 200]");
      } else {
        // 404R: Couldn't rename file
        System.out.println("[HEADER STATUS: 404R]");
      }
    } else {
      // 404D: Couldn't delete file
      System.out.println("[HEADER STATUS: 404D]");
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
        String returnDateTime = (computerTransaction.getReturnDateTime() != null)? computerTransaction.getReturnDateTime().format(formatter) : "N/A";
        String issuer = computerTransaction.getIssuer();
        String reciever = computerTransaction.getReciever();
        String status = computerTransaction.getStatus();
        writer.printf("\n%d,%s,%s,%s,%d,%s,%d,%s,%s,%s,%s,%s",transactionId, studentNo, studentName, collateral, itemId, itemName, itemDestination, borrowDateTime, returnDateTime, issuer, reciever, status);
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
        String returnDateTime = (laptopTransaction.getReturnDateTime() != null)? laptopTransaction.getReturnDateTime().format(formatter) : "N/A";
        String issuer = laptopTransaction.getIssuer();
        String reciever = laptopTransaction.getReciever();
        String status = laptopTransaction.getStatus();
        writer.printf("\n%d,%s,%s,%s,%d,%s,%s,%s,%s,%s,%s",transactionId, studentNo, studentName, collateral, itemId, itemName, borrowDateTime, returnDateTime, issuer, reciever, status);
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
        String returnDateTime = (equipmentTransaction.getReturnDateTime() != null)? equipmentTransaction.getReturnDateTime().format(formatter) : "N/A";
        String issuer = equipmentTransaction.getIssuer();
        String reciever = equipmentTransaction.getReciever();
        String status = equipmentTransaction.getStatus();
        writer.printf("\n%d,%s,%s,%s,%d,%s,%d,%s,%s,%s,%s,%s",transactionId, studentNo, studentName, collateral, itemId, itemName, borrowQuantity, borrowDateTime, returnDateTime, issuer, reciever, status);
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
