import java.time.LocalDateTime;

public class TransactionLog {
  private LocalDateTime timestamp;
  private String borrowerId;
  private String borrowerName;
  private String event;
  private int transactionId;
  private int itemId;
  private String itemName;
  private String status; // success / failed

  public TransactionLog(LocalDateTime timestamp, String borrowerId, String borrowerName, String event,int transactionId, int itemId, String itemName, String status) {
    this.timestamp = timestamp;
    this.borrowerId = borrowerId;
    this.borrowerName = borrowerName;
    this.event = event;
    this.transactionId = transactionId;
    this.itemId = itemId;
    this.itemName = itemName;
    this.status = status;
  }


  public void getTransactionLogDetails() {
    System.out.printf("Timestamp: %s\tBorrowerId: %s\tBorrowerName: %s\tEvent: %s\tTransactionId: %d\tItemId: %d\tItemName: %s\tStatus: %s\t",timestamp, borrowerId, borrowerName, event, transactionId, itemId, itemName, status);
  }


  // GETTERS AND SETTERS (laging niyo tabunan 'to ng mga important methods, mahaba kasi)
  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public int getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(int transactionId) {
    this.transactionId = transactionId;
  }

  public String getBorrowerId() {
    return borrowerId;
  }

  public void setBorrowerId(String borrowerId) {
    this.borrowerId = borrowerId;
  }

  public String getBorrowerName() {
    return borrowerName;
  }

  public void setBorrowerName(String borrower) {
    this.borrowerName = borrower;
  }

  public int getItemId() {
    return itemId;
  }

  public void setItemId(int itemId) {
    this.itemId = itemId;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }
}
