import java.time.LocalDateTime;

public class EquipmentTransaction extends Transaction{
  private int borrowQuantity;

  public EquipmentTransaction(String studentNo, String studentName, String collateral, int itemId, String itemName, LocalDateTime borrowDateTime, String issuer, int borrowQuantity) {
    super(studentNo, studentName, collateral, itemId, itemName, borrowDateTime, issuer);
    this.borrowQuantity = borrowQuantity;
  }

  public EquipmentTransaction(int transactionId, String studentNo, String studentName, String collateral, int itemId, String itemName, LocalDateTime borrowDateTime, LocalDateTime returnDateTime, String issuer, String reciever, String status, int borrowQuantity) {
    super(transactionId, studentNo, studentName, collateral, itemId, itemName, borrowDateTime, returnDateTime, issuer, reciever, status);
    this.borrowQuantity = borrowQuantity;
  }



  @Override
  public String getTransactionDetails() {
    // TODO Auto-generated method stub
    return null;
  }

  // GETTERS and SETTERS 
  public int getBorrowQuantity() {
    return borrowQuantity;
  }

  public void setBorrowQuantity(int quantity) {
    this.borrowQuantity = quantity;
  }


}
