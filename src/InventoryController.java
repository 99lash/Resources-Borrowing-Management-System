import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class InventoryController {
  private List<Resource> resources;
  private List<Computer> computers;
  private List<Laptop> laptops;
  private List<Equipment> equipments;

  public InventoryController() {
    computers = new ArrayList<>();
    laptops = new ArrayList<>();
    equipments = new ArrayList<>();
  }

  public void borrow(Computer computer) {
    computer.setStatus("Not Available");
  }

  public void borrow(Laptop laptop) {
    laptop.setStatus("Not Available");
  }

  public void borrow(Equipment equipment, int borrowQuantity) {
    int availableQty = equipment.getAvailableQuantity();
    if (availableQty == 0) {
      System.out.println(equipment.getName() + " isn't available.");
    } else if (borrowQuantity > availableQty) {
      System.out.println("Invalid request! Please borrow within the available quantity.");
    } else {
      int newQuantity = availableQty - borrowQuantity;
      equipment.setAvailableQuantity(newQuantity);
      if (newQuantity == 0) {
        equipment.setStatus("Not Available");
      }
    }
  }

  public void clearComputers() {
    computers.clear();
  }

  public void clearLaptops() {
    laptops.clear();
  }

  public void clearEquipments() {
    equipments.clear();
  }

  // initializer methods
  public void showAvailableComputers(int destination) {
    int divider = 0;
    fetchComputersFromDatabase(destination);
    System.out.println("----------------------------------");
    System.out.println("Computer Lab " + destination + " - Available Computers");
    System.out.println("----------------------------------");

    for (Computer computer : computers) {
      if (divider == 5) {
        divider = 0;
        System.out.println("\n");
      }

      if (computer.getStatus().equalsIgnoreCase("available")) {
        System.out.printf("[%s]\t", computer.getName());
        divider++;
      }
    }
  }

  public void showAvailableLaptops() {
    int divider = 0;
    fetchLaptopsFromDatabase();
    System.out.println("\n+---------------------------------------------------------------------------------------+");
    System.out.print("|\t\t\t\t    Available Laptops  \t\t\t\t\t|");;
    System.out.println("\n+---------------------------------------------------------------------------------------+");
    System.out.println("|\t\t\t\t\t\t\t\t\t\t\t|");
    for (Laptop laptop : laptops) {
      if (divider == 5) {
        System.out.println("|\n|\t\t\t\t\t\t\t\t\t\t\t|");
        divider = 0;
      }
      if (laptop.getStatus().equalsIgnoreCase("available")) {
        if (divider == 0) {
          System.out.printf("|\t[%s]\t", laptop.getName());
        } else {
          System.out.printf("[%s]\t", laptop.getName());
        }
        divider++;
      }
    }
    System.out.print("|\n|\t\t\t\t\t\t\t\t\t\t\t|");
    System.out.println("\n+---------------------------------------------------------------------------------------+");
  }
  
  public void showAvailableEquipments() {
    fetchEquipmentsFromDatabase();
    int itemId = 1;
    System.out.printf("\t%-7s\t\t%-20s\t%-9s\t%-20s\n","Item ID", "Equipment", "Quantity", "Available Quantity");
    System.out.println("+---------------------------------------------------------------------------------------+");
    for(Equipment equipment : equipments) {
      String availableQuantity = equipment.getAvailableQuantity() != 0 ? String.valueOf(equipment.getAvailableQuantity()) : "Out of stock";
      System.out.printf("\t%-7d\t\t%-20s\t%-10s\t%-19s\n",itemId,equipment.getName(), String.valueOf(equipment.getQuantity()), availableQuantity);
      itemId++;
    }
    System.out.println("\n+---------------------------------------------------------------------------------------+");
  }

  public Computer searchComputer(String itemName) {
    for (Computer computer : computers) {
      if (computer.getName().equalsIgnoreCase(itemName)) {
        return computer;
      }
    }
    return null;
  }

  public Laptop searchLaptop(String itemName) {
    for (Laptop laptop : laptops) {
      if (laptop.getName().equalsIgnoreCase(itemName)) {
        return laptop;
      }
    }
    return null;
  }

  public Equipment searchEquipment(int itemId) {
    for (Equipment equipment : equipments) {
      if (equipment.getId() == itemId) {
        return equipment;
      }
    }
    return null;
  }

  // fetch methods
  public void fetchComputersFromDatabase(int destination) {
    String filepath = "./res/assets/cl" + destination + "/computers.csv";

    try {
      Scanner reader = new Scanner(new File(filepath));
      boolean header = true;
      while (reader.hasNextLine()) {
        String line = reader.nextLine(); // itemId,itemName,type,status,destination
        String[] fields = line.split(","); // fields = {1, PC1, Computer, Not Available, 1};

        if (!header) {
          int id = Integer.parseInt(fields[0]);
          String name = fields[1];
          String type = fields[2];
          String status = fields[3];
          computers.add(new Computer(id, name, type, status, destination));
        } else {
          header = false;
        }
      }
      reader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void fetchLaptopsFromDatabase() {
    String filepath = "./res/assets/laptops.csv";
    boolean header = true;
    try {
      Scanner reader = new Scanner(new File(filepath));

      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        String[] fields = line.split(",");

        if (!header) {
          int itemId = Integer.parseInt(fields[0]);
          String itemName = fields[1];
          String type = fields[2];
          String status = fields[3];
          laptops.add(new Laptop(itemId, itemName, type, status));
        } else {
          header = false;
        }
      }
      reader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void fetchEquipmentsFromDatabase() {
    String filepath = "./res/assets/equipments.csv";
    boolean header = true;
    try {
      Scanner reader = new Scanner(new File(filepath));

      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        String[] fields = line.split(",");

        if (!header) {
          int itemId = Integer.parseInt(fields[0]);
          String itemName = fields[1];
          String type = fields[2];
          int quantity = Integer.parseInt(fields[3]);
          int availableQuantity = Integer.parseInt(fields[4]);
          String status = fields[5];
          equipments.add(new Equipment(itemId, itemName, type, quantity, availableQuantity, status));
        } else {
          header = false;
        }
      }
      reader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  // update inventory methods
  public void updateComputerDatabase() {
    String origFilepath = "./res/assets/cl" + computers.get(0).getDestination() + "/computers.csv";
    String tempFilepath = "./res/assets/cl" + computers.get(0).getDestination() + "/temp.csv";
    try {
      Scanner reader = new Scanner(new File(origFilepath));
      PrintWriter writer = new PrintWriter(new File(tempFilepath));

      // getting the orig file header [itemId,itemName,type,status,destination]
      String line = reader.nextLine();
      writer.print(line);

      for (Computer computer : computers) {
        writer.printf("\n%d,%s,%s,%s,%d", computer.getId(), computer.getName(), computer.getType(),
            computer.getStatus(), computer.getDestination());
      }
      reader.close();
      writer.close();
      computers.clear();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return;
    }
    File originalFile = new File(origFilepath);
    File updatedFile = new File(tempFilepath);

    if (originalFile.delete()) {
      if (updatedFile.renameTo(originalFile)) {
        // 200: CSV file updated successfully
        System.out.println("[UPDATE STATUS: 200]");
      } else {
        // 404R: Couldn't rename file
        System.out.println("[UPDATE STATUS: 404R]");
      }
    } else {
      // 404D: Couldn't delete file
      System.out.println("[UPDATE STATUS: 404D]");
    }
  }

  public void updateLaptopDatabase() {
    String origFilepath = "./res/assets/laptops.csv";
    String tempFilepath = "./res/assets/laptops_temp.csv";

    try {
      Scanner reader = new Scanner(new File(origFilepath));
      PrintWriter writer = new PrintWriter(new File(tempFilepath));

      // getting the orig file header [itemId,itemName,type,status,destination]
      String line = reader.nextLine();
      writer.print(line);

      for (Laptop laptop : laptops) {
        int itemId = laptop.getId();
        String itemName = laptop.getName();
        String type = laptop.getType();
        String status = laptop.getStatus();
        writer.printf("\n%d,%s,%s,%s", itemId, itemName, type, status);
      }
      reader.close();
      writer.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return;
    }

    File originalFile = new File(origFilepath);
    File updatedFile = new File(tempFilepath);

    if (originalFile.delete()) {
      if (updatedFile.renameTo(originalFile)) {
        // 200: CSV file updated successfully
        System.out.println("[LAPTOP INVENTORY STATUS: 200]");
      } else {
        // 404R: Couldn't rename file
        System.out.println("[UPDATE STATUS: 404R]");
      }
    } else {
      // 404D: Couldn't delete file
      System.out.println("[UPDATE STATUS: 404D]");
    }
  }

  public void updateEquipmentDatabase() {
    String origFilepath = "./res/assets/equipments.csv";
    String tempFilepath = "./res/assets/equipments_temp.csv";

    try {
      Scanner reader = new Scanner(new File(origFilepath));
      PrintWriter writer = new PrintWriter(new File(tempFilepath));

      // getting the orig file header [itemId,itemName,type,status,destination]
      String line = reader.nextLine();
      writer.print(line);

      for (Equipment equipment : equipments) {
        int itemId = equipment.getId();
        String itemName = equipment.getName();
        String type = equipment.getType();
        int quantity = equipment.getQuantity();
        int availableQuantity = equipment.getAvailableQuantity();
        String status = equipment.getStatus();
        writer.printf("\n%d,%s,%s,%d,%d,%s", itemId, itemName, type ,quantity, availableQuantity, status);
      }
      reader.close();
      writer.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return;
    }

    File originalFile = new File(origFilepath);
    File updatedFile = new File(tempFilepath);

    if (originalFile.delete()) {
      if (updatedFile.renameTo(originalFile)) {
        // 200: CSV file updated successfully
        System.out.println("[EQUIPMENT INVENTORY STATUS: 200]");
      } else {
        // 404R: Couldn't rename file
        System.out.println("[UPDATE STATUS: 404R]");
      }
    } else {
      // 404D: Couldn't delete file
      System.out.println("[UPDATE STATUS: 404D]");
    }
  }

  // GETTERS
  public List<Resource> getResources() {
    return resources;
  }

  public List<Computer> getComputers() {
    return computers;
  }

  public List<Laptop> getLaptops() {
    return laptops;
  }

  public List<Equipment> getEquipments() {
    return equipments;
  }

  // SETTERS
  public void setResources(List<Resource> resources) {
    this.resources = resources;
  }

  public void setComputers(List<Computer> computers) {
    this.computers = computers;
  }

  public void setLaptops(List<Laptop> laptops) {
    this.laptops = laptops;
  }

  public void setEquipments(List<Equipment> equipments) {
    this.equipments = equipments;
  }

}
/*
 * 6. **InventoryManager**
 * - Attributes:
 * - resources: List<Resource>
 * - Methods:
 * - addResource(resource: Resource): void
 * - removeResource(resourceId: int): void
 * - updateResource(resourceId: int, newQuantity: int): void
 * - getResourceById(resourceId: int): Resource
 * - listResources(): List<Resource>
 */