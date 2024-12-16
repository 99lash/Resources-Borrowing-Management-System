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

  public void fetchTransactionLogsFromDatabase() {
    String filepath = "./res/logs/transaction/transaction_log.csv";
    try {
      Scanner reader = new Scanner(new File(filepath));
      boolean header = true;

      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        String[] fields = line.split(",");

        if (!header) {
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
          LocalDateTime timestamp = LocalDateTime.parse(fields[0], formatter);
          String borrowerId = fields[1];
          String borrowerName = fields[2];
          String event = fields[3];
          int transactionId = Integer.parseInt(fields[4]);
          int itemId = Integer.parseInt(fields[5]);
          String itemName = fields[6];
          String status = fields[7];
          transactionsLog.add(new TransactionLog(timestamp, borrowerId, borrowerName, event, transactionId, itemId, itemName, status));
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
    String origFilepath = "./res/logs/transaction/transaction_log.csv";
    String tempFilepath = "./res/logs/transaction/transaction_log_temp.csv";

    try {
      Scanner reader = new Scanner(new File(origFilepath));
      PrintWriter writer = new PrintWriter(new File(tempFilepath));

      String line = reader.nextLine();
      writer.print(line);

      for (TransactionLog transactionLog : transactionsLog) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
        String timestamp = transactionLog.getTimestamp().format(formatter);
        String borrowerId = transactionLog.getBorrowerId();
        String borrowerName = transactionLog.getBorrowerName();
        String event = transactionLog.getEvent();
        int transactionId = transactionLog.getTransactionId();
        int itemId = transactionLog.getItemId();
        String itemName = transactionLog.getItemName();
        String status = transactionLog.getStatus();
        writer.printf("\n%s,%s,%s,%s,%d,%d,%s,%s", timestamp,borrowerId,borrowerName,event,transactionId,itemId,itemName,status);
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

  public void updateAuditLogDatabase() {

  }

  // public void displayAllTranscationLogs() {
  // System.out.println("Recent Transactions [Log] (Visible) ");
  // System.out.println("+----------+----------------------+--------------------------------+------------+-----------------+----------------------+----------------------+------------+------------+----------+");
  // System.out.printf("| %-8s | %-20s | %-30s | %-10s | %-15s | %-20s | %-20s |
  // %-10s | %-10s | %-8s |\n", "Trans ID", "Borrower Student No.", "Borrower
  // Name", "Collateral", "Item Borrowed", "Borrowed Date & Time", "Returned Date
  // & Time", "Issuer", "Reciever", "Status");
  // System.out.println("+----------+----------------------+--------------------------------+------------+-----------------+----------------------+----------------------+------------+------------+----------+");
  // if (transactionsLog.isEmpty()) {
  // System.out.println("| No Transaction Records :D
  // \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t |");
  // System.out.println("+----------+----------------------+--------------------------------+------------+-----------------+----------------------+----------------------+------------+------------+----------+");
  // } else {
  // int size = transactionsLog.size()-1;
  // int displayCount = 1;
  // for (int i = size; i >= 0; i--) {
  // if (displayCount > 5) break;
  // displayCount++;
  // transactionsLog.get(i).getTransactionId();
  // int transactionId = transactionsLog.get(i).getTransactionId();
  // String borrowerStudentNo = transactionsLog.get(i).getStudentNo();
  // String borrowerStudentName = transactionsLog.get(i).getStudentName();
  // String collateral = transactionsLog.get(i).getCollateral();
  // String itemName = transactionsLog.get(i).getItemName();
  // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm
  // a");
  // String borrowDateTime =
  // transactionsLog.get(i).getBorrowDateTime().format(formatter);
  // String returnDateTime = transactionsLog.get(i).getReturnDateTime() == null?
  // "N/A" : transactionsLog.get(i).getReturnDateTime().format(formatter);
  // String issuer = transactionsLog.get(i).getIssuer();
  // String reciever = transactionsLog.get(i).getReciever();
  // String status = transactionsLog.get(i).getStatus() == null ? "N/A" :
  // transactionsLog.get(i).getStatus();
  // System.out.printf("| %-8d | %-20s | %-30s | %-10s | %-15s | %-20s | %-20s |
  // %-10s | %-10s | %-8s |\n", transactionId, borrowerStudentNo,
  // borrowerStudentName, collateral, itemName, borrowDateTime, returnDateTime,
  // issuer, reciever, status);
  // System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
  // }

  // }
  // System.out.println("\n\n");
  // }

  public void displayTranscationLogs() {
    System.out.println("Transactions Log (Visible)");
    System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    System.out.printf("%-20s\t%-50s\t%-10s\t%-20s\t%-40s\t%-10s\n", "Timestamp", "Borrower", "Event", "Transaction ID", "Details", "Status");
    System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

    if (transactionsLog.isEmpty()) {
      System.out.println("No Transaction Records :D");
    } else {
      int displayCount = 0;
      for (int i = transactionsLog.size()-1; i >= 0; i--) {
        if (displayCount == 5) break;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        String timestamp = transactionsLog.get(i).getTimestamp().format(formatter);
        String borrower = String.format("%-30s\t#%s", transactionsLog.get(i).getBorrowerName(), transactionsLog.get(i).getBorrowerId());
        String event = transactionsLog.get(i).getEvent();
        String transactionId = String.format("#%d", transactionsLog.get(i).getTransactionId());
        int itemId = transactionsLog.get(i).getItemId();
        String itemName = transactionsLog.get(i).getItemName();
        String status = transactionsLog.get(i).getStatus();
        String details = String.format("%s %-20s\t#%d", event, itemName, itemId);
        System.out.printf("%-20s\t%-50s\t%-5s\t%-20s\t%-40s\t%-10s\n",timestamp, borrower, event, transactionId, details, status);
        displayCount++;
      }
    }
    System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n");
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
