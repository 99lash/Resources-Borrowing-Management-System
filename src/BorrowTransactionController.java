import java.util.ArrayList;
import java.util.List;

public class BorrowTransactionController {
  List<BorrowTransaction> transactions;

  public BorrowTransactionController() {
    this.transactions = new ArrayList<>();
  }

  // GETTERS
  public List<BorrowTransaction> getTransactions() {
    return transactions;
  }

  // SETTERS
  public void setTransactions(List<BorrowTransaction> transactions) {
    this.transactions = transactions;
  }

  public void fetchTransactions() {
    
  }
}
