public class Equipment extends Resource{
  private int quantity;
  private int availableQuantity;
  private String status;

  Equipment(int id, String name, String type, int quantity, int availableQuantity, String status) {
    super(id, name, type);
    setQuantity(quantity);
    this.availableQuantity = availableQuantity;
    this.status = status;
  }

  Equipment(int id, String name, String type, int quantity) {
    super(id, name, type);
    setQuantity(quantity);
    setAvailableQuantity(quantity);
    this.status = availableQuantity > 0 ? "Available" : "Not Available"; 
  }

  Equipment(int id, String name, int initialQuantity) {
    super(id, name, "Equipment");
    setQuantity(initialQuantity);
    setAvailableQuantity(initialQuantity);
    setStatus("Available");
  }

  @Override
  public String getDetails() {
    return String.format("Item Id: %d\nName: %s\nType: %s\nQuantity: %d\nAvailable Quantity: %d\nStatus: %s", super.getId(), super.getName(), super.getType(), quantity, availableQuantity, status);
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
