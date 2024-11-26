import java.time.LocalDateTime;

public class BorrowTransaction {
  private static int transactionId = 1000;
  private Student student;
  private Resource resource;
  private int quantity;
  private LocalDateTime borrowDate;
  private LocalDateTime returnDate;
  private boolean isReturned;

  BorrowTransaction(Student student, Resource resource, int quantity, LocalDateTime dateTime) {
    transactionId++;
    this.student = student;
    this.resource = resource;
    this.quantity = quantity;
    this.borrowDate = dateTime.now();
    this.returnDate = null;
    this.isReturned = false;
  }

  // GETTERS
  public static int getTransactionId() {
    return transactionId;
  }

  public Student getStudent() {
    return student;
  }

  public Resource getResource() {
    return resource;
  }

  public int getQuantity() {
    return quantity;
  }

  public LocalDateTime getBorrowDate() {
    return borrowDate;
  }

  public LocalDateTime getReturnDate() {
    return returnDate;
  }

  public boolean isReturned() {
    return isReturned;
  }


  // SETTERS
  public static void setTransactionId(int transactionId) {
    BorrowTransaction.transactionId = transactionId;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public void setResource(Resource resource) {
    this.resource = resource;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
  
  public void setBorrowDate(LocalDateTime borrowDate) {
    this.borrowDate = borrowDate;
  }

  public void setReturnDate(LocalDateTime returnDate) {
    this.returnDate = returnDate;
  }

  public void markAsReturned () {
    this.isReturned = true;
    this.returnDate = returnDate.now();
  }

  public String getTransactionDetails() { // String type to
    return String.format("Transaction ID: %d\nBorrower: %s\nBorrowed: %s\nQuantity: %d\nDate Borrowed: %s\nDate Returned: %s\n", transactionId, student.getFirstName() + " " + student.getLastName(), resource.getName(), this.quantity, borrowDate, returnDate == null? "null" : returnDate);
  }

  
}

/* 
 * 3. **BorrowTransaction**
   - Attributes:
     - transactionId: int
     - student: Student
     - resource: Resource
     - quantity: int
     - date: LocalDateTime
     - isReturned: boolean
   - Methods:
     - markAsReturned(): void
     - getTransactionDetails(): String
 */
