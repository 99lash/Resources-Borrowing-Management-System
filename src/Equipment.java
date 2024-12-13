public class Equipment extends Resource{
  private int quantity;
  private int availableQuantity;
  private String status;

  Equipment(int id, String name, String type, int quantity, int availableQuantity, String status) {
    super(id, name, type, quantity);
    this.quantity = quantity;
    this.availableQuantity = availableQuantity;
    this.status = status;
  }

  Equipment(int id, String name, String type, int quantity) {
    super(id, name, type, quantity);
    this.availableQuantity = quantity;
    this.status = availableQuantity > 0 ? "Available" : "Not Available"; 
  }

  // GETTERS and SETTERS tabunan niyo 'to pls
  public int getQuantity() {
    return quantity;
  }
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public int getAvailableQuantity() {
    return availableQuantity;
  }

  public void setAvailableQuantity(int availableQuantity) {
    this.availableQuantity = availableQuantity;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  
}
