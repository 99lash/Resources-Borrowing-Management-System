import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ComputerTransaction extends Transaction {
  private int destination;

  public ComputerTransaction(String studentNo, String studentName, String collateral, int itemId, String itemName, LocalDateTime borrowDate, String issuer, int destination) {
    super(studentNo, studentName, collateral, itemId, itemName, borrowDate, issuer);
    this.destination = destination;
    // setTransactionCount(getTransactionCount()+1);
    // setTransactionId(getTransactionCount());
  }

  public ComputerTransaction(int transactionId, String studentNo, String studentName, String collateral, int itemId, String itemName, LocalDateTime borrowDate, LocalDateTime returnDate, String issuer, String reciever, int destination, String status) {
    super(transactionId, studentNo, studentName, collateral, itemId, itemName, borrowDate, returnDate, issuer, reciever, status);
    this.destination = destination;
  }

  public int getDestination() {
    return destination;
  }

  public void setDestination(int destination) {
    this.destination = destination;
  }

  @Override
  public String getTransactionDetails() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
    String borrowDateTimeStr = getBorrowDateTime().format(formatter);
    String returnDateTimeStr = getReturnDateTime() != null? getReturnDateTime().format(formatter) : null;
    return String.format("Transaction ID: %d\nBorrower ID: %s\nBorrower Name: %s\nCollateral: %s\nItem Borrowed: %s\nItem Destinated: CL%d\nBorrowed Date: %s\nReturn Date: %s\nIssuer: %s\nReciever: %s\nStatus: %s\n",getTransactionId(), getStudentNo(), getStudentName(), getCollateral(), getItemName(), getDestination(), borrowDateTimeStr, returnDateTimeStr, getIssuer(), getReceiver(), getStatus() );
  }

}
