import java.time.LocalDateTime;

public class TransactionHeader {
  private static int transactionCount = 1000;
  private int transactionId;
  private LocalDateTime transDateTime;
  private String borrowerName;
  private String itemType;

  public TransactionHeader(LocalDateTime transDateTime, String borrowerName, String itemType) {
    this.transactionId = ++transactionCount;
    this.transDateTime = transDateTime;
    this.borrowerName = borrowerName;
    this.itemType = itemType;
  }
  
  public TransactionHeader(int transactionId, LocalDateTime transDateTime, String borrowerName, String itemType) {
    transactionCount = transactionId;
    this.transactionId = transactionCount;
    this.transDateTime = transDateTime;
    this.borrowerName = borrowerName;
    this.itemType = itemType;
  }
  
  @ Override
  public String toString() {
    return String.format("TransID: %d\nTransDateTime: %s\nBorrower Name: %s\nItem Type: %s", transactionId, transDateTime, borrowerName, itemType);
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
  
  public String getItemType() {
    return itemType;
  }

  public void setItemType(String itemType) {
    this.itemType = itemType;
  }
}
