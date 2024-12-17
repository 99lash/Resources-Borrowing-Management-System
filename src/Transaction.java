import java.time.LocalDateTime;

public abstract class Transaction {
  private int transactionId;
  private String studentNo;
  private String studentName;
  private String collateral;
  private int  itemId;
  private String itemName;
  private LocalDateTime borrowDateTime;
  private LocalDateTime returnDateTime;
  private String issuer;
  private String reciever;
  private boolean isReturned;
  private String status;

  public Transaction(String studentNo, String studentName, String collateral, int itemId, String itemName, LocalDateTime borrowDateTime, String issuer) {
    this.studentNo = studentNo;
    this.studentName = studentName;
    this.collateral = collateral;
    this.itemId = itemId;
    this.itemName = itemName;
    this.borrowDateTime = borrowDateTime;
    this.returnDateTime = null;
    this.issuer = issuer;
    this.reciever = "N/A";
    this.isReturned = false;
    this.status = isReturned ? "Returned" : "Borrowed";
  }

  public Transaction(int transactionId, String studentNo, String studentName, String collateral, int itemId, String itemName, LocalDateTime borrowDateTime, LocalDateTime returnDateTime, String issuer, String reciever, String status) {
    this.transactionId = transactionId;
    this.studentNo = studentNo;
    this.studentName = studentName;
    this.collateral = collateral;
    this.itemId = itemId;
    this.itemName = itemName;
    this.borrowDateTime = borrowDateTime;
    this.returnDateTime = returnDateTime;
    this.issuer = issuer;
    this.reciever = reciever;
    this.status = status;
    this.isReturned = status.equalsIgnoreCase("Returned");
  }
  
  public abstract String getTransactionDetails();

  // GETTERS & SETTERS
  // public static int getTransactionCount() {
  //   return transactionCount;
  // }

  // public static void setTransactionCount(int transactionCount) {
  //   Transaction.transactionCount = transactionCount;
  // }

  public int getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(int transactionId) {
    this.transactionId = transactionId;
  }

  public String getStudentNo() {
    return studentNo;
  }

  public void setStudentNo(String studentNo) {
    this.studentNo = studentNo;
  }

  public String getStudentName() {
    return studentName;
  }

  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  public String getCollateral() {
    return collateral;
  }

  public void setCollateral(String collateral) {
    this.collateral = collateral;
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

  public LocalDateTime getBorrowDateTime() {
    return borrowDateTime;
  }

  public void setBorrowDateTime(LocalDateTime borrowDate) {
    this.borrowDateTime = borrowDate;
  }

  public LocalDateTime getReturnDateTime() {
    return returnDateTime;
  }

  public void setReturnDateTime(LocalDateTime returnDate) {
    this.returnDateTime = returnDate;
  }

  public String getIssuer() {
    return issuer;
  }

  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }

  public String getReceiver() {
    return reciever;
  }

  public void setReciever(String reciever) {
    this.reciever = reciever;
  }

  public boolean isReturned() {
    return isReturned;
  }

  public void setReturned(boolean isReturned) {
    this.isReturned = isReturned;
    if (isReturned) setStatus("Returned");
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}