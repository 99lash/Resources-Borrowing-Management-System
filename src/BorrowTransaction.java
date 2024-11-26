import java.time.LocalDateTime;

public class BorrowTransaction {
  private static int transactionId = 22400;
  private Student student;
  private Resource resource;
  private int quantity;
  private LocalDateTime date;
  private boolean isReturned;

  BorrowTransaction(Student student, Resource resource, int quantity, LocalDateTime date) {
    transactionId++;
    this.student = student;
    this.resource = resource;
    this.quantity = quantity;
    this.date = date;
    this.isReturned = false;
  }

  public void markAsReturned () {
    
  }

  public void getTransactionDetails() { // String type to
    
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
