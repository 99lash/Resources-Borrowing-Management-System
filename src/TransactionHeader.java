import java.time.LocalDateTime;

public class TransactionHeader {
  private static int transactionCount = 1000;
  private int transactionId;
  private LocalDateTime transDateTime;
  private String borrowerName;

  public TransactionHeader(LocalDateTime transDateTime, String borrowerName) {
    this.transactionId = ++transactionCount;
    this.transDateTime = transDateTime;
    this.borrowerName = borrowerName;
  }
  
  public TransactionHeader(int transactionId, LocalDateTime transDateTime, String borrowerName) {
    transactionCount = transactionId;
    this.transactionId = transactionCount;
    this.transDateTime = transDateTime;
    this.borrowerName = borrowerName;
  }
  
  @ Override
  public String toString() {
    return String.format("TransID: %d\nTransDateTime: %s\nBorrower Name: %s", transactionId, transDateTime, borrowerName);
  }

  public static int getTransactionCount() {
    return transactionCount;
  }

  public static void setTransactionCount(int transactionCount) {
    TransactionHeader.transactionCount = transactionCount;
  }

  public int getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(int transactionId) {
    this.transactionId = transactionId;
  }

  public LocalDateTime getTransDateTime() {
    return transDateTime;
  }

  public void setTransDateTime(LocalDateTime transDateTime) {
    this.transDateTime = transDateTime;
  }

  public String getBorrowerName() {
    return borrowerName;
  }

  public void setBorrowerName(String borrowerName) {
    this.borrowerName = borrowerName;
  }
}
