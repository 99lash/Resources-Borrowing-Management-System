import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LaptopTransaction extends Transaction {
  
  public LaptopTransaction(String studentNo, String studentName, String collateral, int itemId, String itemName, LocalDateTime borrowDateTime, String issuer) {
    super(studentNo, studentName, collateral, itemId, itemName, borrowDateTime, issuer);
  }

  public LaptopTransaction(int transactionId, String studentNo, String studentName, String collateral, int itemId, String itemName, LocalDateTime borrowDateTime, LocalDateTime returnDateTime, String issuer, String reciever, String status) {
    super(transactionId, studentNo, studentName, collateral, itemId, itemName, borrowDateTime, returnDateTime, issuer, reciever, status);
  }

  @Override
  public String getTransactionDetails() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
    String borrowDateTimeStr = getBorrowDateTime().format(formatter);
    String returnDateTimeStr = getReturnDateTime() != null? getReturnDateTime().format(formatter) : null;
    return String.format("Transaction ID: %d\nBorrower ID: %s\nBorrower Name: %s\nCollateral: %s\nItem Borrowed: %s\nBorrowed Date: %s\nReturn Date: %s\nIssuer: %s\nReciever: %s\nStatus: %s\n",getTransactionId(), getStudentNo(), getStudentName(), getCollateral(), getItemName(), borrowDateTimeStr, returnDateTimeStr, getIssuer(), getReciever(), getStatus() );
  }

  
}
