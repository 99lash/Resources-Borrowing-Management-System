public class Equipment extends Resource{
  int availableQuantity;

  Equipment(int id, String name, String type, int quantity) {
    super(id, name, type, quantity);
    this.availableQuantity = quantity;
  }
  
  // GETTERS
  public int getAvailableQuantity() {
    return availableQuantity;
  }

  // SETTERS
  public void setAvailableQuantity(int availableQuantity) {
    this.availableQuantity = availableQuantity;
  }
}
