public abstract class Resource {
  private int id;
  private String name;
  private String type;
  private int quantity;
  
  protected Resource(int id, String name, String type) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.quantity = 1;
  }

  protected Resource(int id, String name, String type, int quantity) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.quantity = quantity;
  }

  // GETTERS
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public int getQuantity() {
    return quantity;
  }

  public String getDetails() {
    return String.format("ID: %d\nName: %s\nType: %s\nQuantity: %d", id, name, type, quantity);
  }

  // SETTERS
  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  // increment and decrement  methods
  public void addStock(int quantity) {
    this.quantity += quantity;
  }

  public void removeStock(int quantity) {
    this.quantity -= quantity;
  }
 
}

/*
 * 1. **Resource**
 * - Attributes:
 * - id: int
 * - name: String
 * - type: String
 * - quantity: int
 * - Methods:
 * - addStock(quantity: int): void
 * - removeStock(quantity: int): void
 * - getDetails(): String
 */