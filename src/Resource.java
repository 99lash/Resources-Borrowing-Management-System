public abstract class Resource {
  private int id;
  private String name;
  private String type;

  protected Resource(int id, String name, String type) {
    this.id = id;
    this.name = name;
    this.type = type;
  }

  protected Resource(int id, String name, String type, int quantity) {
    this.id = id;
    this.name = name;
    this.type = type;
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

  public String getDetails() {
    return String.format("ID: %d\nName: %s\nType: %s", id, name, type);
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