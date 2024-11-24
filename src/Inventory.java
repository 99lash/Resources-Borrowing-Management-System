import java.util.ArrayList;
import java.util.List;

public abstract class Inventory {
  private int itemId;
  private String name;
  private String type;
  private String status;
  private boolean isBorrowed;
  

  Inventory(int itemId, String name, String status) {
    this.itemId = itemId;
    this.name = name;
    this.type = "Computer";
    this.isBorrowed = false;
    this.status = status;
  }

  // GETTERS
  public int getId() {
    return itemId;
  }
  
  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public String getStatus() {
    return status;
  }

  public boolean isBorrowed() {
    return isBorrowed;
  }

  
  
  public String getDetails() {
    return String.format("Item ID: %d\nItem Name: %s\nItem TypeId: %s\n", itemId, name, type);
  }
  
  // SETTERS 
  public void setId(int itemId) {
    this.itemId = itemId;
  }

  public void setName(String name) {
    this.name = name;
  } 


  public void setBorrowed(boolean isBorrowed) {
    this.isBorrowed = isBorrowed;
  }
  
  public void setStatus(String status) {
    this.status = status;  
  }


  public abstract void doBorrow();
  public abstract void doReturn();
  public abstract void updateInventory(ArrayList<PCInventory> PCs);
  
  
}

/*
 * Attributes:
 * itemID: int
 * name: String
 * type: String
 * quantity: int
 * availableQuantity: int
 * Methods:
 * getDetails(): String
 */