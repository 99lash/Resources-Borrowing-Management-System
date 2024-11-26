public class Equipment extends Resource{
  int availableQuantity;

  Equipment(int id, String name, String type, int quantity) {
    super(id, name, type, quantity);
    this.availableQuantity = quantity;
  }
}
