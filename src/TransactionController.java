import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionController {
  private List<ComputerTransaction> computerTransactions;
  private List<LaptopTransaction> laptopTransactions;
  private List<EquipmentTransaction> equipmentTransactions;

  public TransactionController() {
    computerTransactions = new ArrayList<>();
    laptopTransactions = new ArrayList<>();
    equipmentTransactions = new ArrayList<>();
  }

  // GETTERS
  public List<ComputerTransaction> getComputerTransactions() {
    return computerTransactions;
  }

  public List<LaptopTransaction> getLaptopTransactions() {
    return laptopTransactions;
  }

  public List<EquipmentTransaction> getEquipmentTransactions() {
    return equipmentTransactions;
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

  // SETTERS
  public void setComputerTransactions(List<ComputerTransaction> computerTransactions) {
    this.computerTransactions = computerTransactions;
  }

  public void setLaptopTransactions(List<LaptopTransaction> laptopTransactions) {
    this.laptopTransactions = laptopTransactions;
  }

  public void setEquipmentTransactions(List<EquipmentTransaction> equipmentTransactions) {
    this.equipmentTransactions = equipmentTransactions;
  }

  public void fetchComputerTransactionsFromDatabase() {
    String filepath = "../res/data/transaction/computer_transaction.csv";
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
          int itemDesination = Integer.parseInt(fields[6]);
          LocalDateTime borrowDateTime = LocalDateTime.parse(fields[7], formatter);
          LocalDateTime returnDateTime = fields[8].equalsIgnoreCase("N/A") ? null : LocalDateTime.parse(fields[8], formatter);
          String issuer = fields[9];
          String reciever = fields[10];
          String status = fields[11];
          computerTransactions.add(new ComputerTransaction(transactionId, studentNo, studentName, collateral, itemId,
              itemName, borrowDateTime, returnDateTime, issuer, reciever, itemDesination, status));
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

  }

  public void equipmentTransactionsFromDatabase() {

  }

  public void updateComputerTranscactionsDatabase() {
    String origFilepath = "../res/data/transaction/computer_transaction.csv";
    String tempFilepath = "../res/data/transaction/computer_transaction_temp.csv";

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

  }

  public void updateEquipmentTransactionsDatabase() {

  }
  
  
}
