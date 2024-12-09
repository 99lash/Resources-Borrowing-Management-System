import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LogController {
  private List<TransactionLog> transactionsLog;
  private List<AuditLog> auditLogs;

  public LogController() {
    this.transactionsLog = new ArrayList<>();
    this.auditLogs = new ArrayList<>();
  }

  public void clearTransactionLog() {
    transactionsLog.clear();
  }

  public void clearAuditLog() {
    auditLogs.clear();
  }

  public void fetchTransactionLogFromDatabase() {
    String filepath = "../res/logs/transaction/transaction_log.csv";
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
          String itemName = fields[4];
          LocalDateTime borrowDateTime = LocalDateTime.parse(fields[5], formatter);
          LocalDateTime returnDateTime = fields[6].equalsIgnoreCase("N/A") ? null : LocalDateTime.parse(fields[6], formatter);
          String issuer = fields[7];
          String reciver = fields[8];
          String status = fields[9];
          transactionsLog.add(new TransactionLog(transactionId, studentNo, studentName, collateral, itemName, borrowDateTime, returnDateTime, issuer, reciver, status));
        } else {
          header = false;
        }
      }

      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void fetchAuditLogFromDatabase() {

  }

  public void updateTransactionLogDatabase() {
    String origFilepath = "../res/logs/transaction/transaction_log.csv";
    String tempFilepath = "../res/logs/transaction/transaction_log_temp.csv";

    try {
      Scanner reader = new Scanner(new File(origFilepath));
      PrintWriter writer = new PrintWriter(new File(tempFilepath));
      
      String line = reader.nextLine();
      writer.print(line);      
      
      for (TransactionLog transactionLog : transactionsLog) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
        int transactionId = transactionLog.getTransactionId();
        String studentNo = transactionLog.getStudentNo();
        String studentName = transactionLog.getStudentName();
        String collateral = transactionLog.getCollateral();
        String itemName = transactionLog.getItemName();

        String borrowDateTime = transactionLog.getBorrowDateTime().format(formatter);
        String returnDateTime = (transactionLog.getReturnDateTime() != null) ? transactionLog.getReturnDateTime().format(formatter) : "N/A"; 
        String issuer = transactionLog.getIssuer();
        String reciever = transactionLog.getReciever();
        String status = transactionLog.getStatus();
        writer.printf("\n%d,%s,%s,%s,%s,%s,%s,%s,%s,%s", transactionId, studentNo, studentName, collateral, itemName, borrowDateTime, returnDateTime, issuer, reciever, status);
      }
      reader.close();
      writer.close();
      transactionsLog.clear();
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

  public void updateAuditLogDatabase() {

  }

  public void displayAllTranscationLogs() {
    System.out.println("Recent Transactions [Log] (Visible) ");
    System.out.println("+----------+----------------------+--------------------------------+------------+-----------------+----------------------+----------------------+------------+------------+----------+");
    System.out.printf("| %-8s | %-20s | %-30s | %-10s | %-15s | %-20s | %-20s | %-10s | %-10s | %-8s |\n", "Trans ID", "Borrower Student No.", "Borrower Name", "Collateral", "Item Borrowed", "Borrowed Date & Time", "Returned Date & Time", "Issuer", "Reciever", "Status");
    System.out.println("+----------+----------------------+--------------------------------+------------+-----------------+----------------------+----------------------+------------+------------+----------+");
    if (transactionsLog.isEmpty()) {
      System.out.println("| No Transaction Records :D \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t     |");
      System.out.println("+----------+----------------------+--------------------------------+------------+-----------------+----------------------+----------------------+------------+------------+----------+");
    } else {
      int size = transactionsLog.size()-1;
      int displayCount = 1;
      for (int i = size; i >= 0; i--) {
        if (displayCount > 5) break; 
        displayCount++;
        transactionsLog.get(i).getTransactionId();
        int transactionId = transactionsLog.get(i).getTransactionId();
        String borrowerStudentNo = transactionsLog.get(i).getStudentNo();
        String borrowerStudentName = transactionsLog.get(i).getStudentName();
        String collateral = transactionsLog.get(i).getCollateral();
        String itemName = transactionsLog.get(i).getItemName();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
        String borrowDateTime = transactionsLog.get(i).getBorrowDateTime().format(formatter);
        String returnDateTime = transactionsLog.get(i).getReturnDateTime() == null? "N/A" : transactionsLog.get(i).getReturnDateTime().format(formatter);
        String issuer = transactionsLog.get(i).getIssuer();
        String reciever = transactionsLog.get(i).getReciever();
        String status = transactionsLog.get(i).getStatus() == null ? "N/A" : transactionsLog.get(i).getStatus();
        System.out.printf("| %-8d | %-20s | %-30s | %-10s | %-15s | %-20s | %-20s | %-10s | %-10s | %-8s |\n", transactionId, borrowerStudentNo, borrowerStudentName, collateral, itemName, borrowDateTime, returnDateTime, issuer, reciever, status);
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
      }
      // for (TransactionLog transactionLog : transactionsLog) {
      //   int transactionId = transactionLog.getTransactionId();
      //   String borrowerStudentNo = transactionLog.getStudentNo();
      //   String borrowerStudentName = transactionLog.getStudentName();
      //   String collateral = transactionLog.getCollateral();
      //   String itemName = transactionLog.getItemName();
      //   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
      //   String borrowDateTime = transactionLog.getBorrowDate().format(formatter);
      //   String returnDateTime = transactionLog.getReturnDate() == null? "N/A" : transactionLog.getReturnDate().format(formatter);
      //   String issuer = transactionLog.getIssuer();
      //   String reciever = transactionLog.getReciever();
      //   String status = transactionLog.getStatus() == null ? "N/A" : transactionLog.getStatus();
      //   System.out.printf("| %-8d | %-20s | %-30s | %-10s | %-15s | %-20s | %-20s | %-10s | %-10s | %-8s |\n", transactionId, borrowerStudentNo, borrowerStudentName, collateral, itemName, borrowDateTime, returnDateTime, issuer, reciever, status);
      //   System.out.println("+----------+----------------------+--------------------------------+------------+-----------------+----------------------+----------------------+------------+------------+----------+");
      // }
    }
    System.out.println("\n\n");
  }

  // GETTERS and SETTERS (tabunan niyo 'to ng mga important methods.)
  public List<TransactionLog> getTransactionsLog() {
    return transactionsLog;
  }

  public void setTransactionsLog(List<TransactionLog> transactionsLog) {
    this.transactionsLog = transactionsLog;
  }

  public List<AuditLog> getAuditLogs() {
    return auditLogs;
  }

  public void setAuditLogs(List<AuditLog> auditLogs) {
    this.auditLogs = auditLogs;
  }

}
