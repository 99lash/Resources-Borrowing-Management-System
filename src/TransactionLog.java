import java.time.LocalDateTime;

public class TransactionLog {
  private int transactionId;
  private String studentNo;
  private String studentName;
  private String collateral;
  private String itemName;
  private LocalDateTime borrowDateTime;
  private LocalDateTime returnDateTime;
  private String issuer;
  private String reciever;
  private String status;

  public TransactionLog(int transactionId, String studentNo, String studentName, String collateral, String itemName, LocalDateTime borrowDateTime, LocalDateTime returnDateTime, String issuer, String reciever, String status) {
    this.transactionId = transactionId;
    this.studentNo = studentNo;
    this.studentName = studentName;
    this.collateral = collateral;
    this.itemName = itemName;
    this.borrowDateTime = borrowDateTime;
    this.returnDateTime = returnDateTime;
    this.issuer = issuer;
    this.reciever = reciever;
    this.status = status;
  }
  
  public TransactionLog(int transactionId, String studentNo, String studentName, String collateral, String itemName, int itemDesination, LocalDateTime borrowDateTime, LocalDateTime returnDateTime, String issuer, String reciever) {
    this.transactionId = transactionId;
    this.studentNo = studentNo;
    this.studentName = studentName;
    this.collateral = collateral;
    this.itemName = String.format("%s (CL%d)", itemName, itemDesination);
    this.borrowDateTime = borrowDateTime;
    this.returnDateTime = returnDateTime;
    this.issuer = issuer;
    this.reciever = reciever;
    this.status = returnDateTime != null ? "returned" : "borrowed";
  }


  // GETTERS AND SETTERS (laging niyo tabunan 'to ng mga important methods, mahaba kasi)
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

  public String getReciever() {
    return reciever;
  }

  public void setReciever(String reciever) {
    this.reciever = reciever;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  
  

}
