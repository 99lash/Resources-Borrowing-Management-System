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
    resources = new ArrayList<>();
    computers = new ArrayList<>();
    laptops = new ArrayList<>();
    equipments = new ArrayList<>();
  }

  public void addResource(Resource resource) {
    resources.add(resource);
  }

  public void removeResource(int resourceId) {
    resources.forEach(res -> {
      if (res.getId() == resourceId) {
        resources.remove(resources.indexOf(res));
        return;
      }
    });
  }

  public void updateResource(int resourceId, int newQuantity) {
    resources.forEach(res -> {
      if (res.getId() == resourceId) {
        res.addStock(newQuantity);
      }
    });
  }

  public Resource getResourceByID(int resourceId) {

    for (Resource res : resources) {
      if (res.getId() == resourceId) {
        return res;
      }
    }
    return null;
  }

  public void borrowComputer(Computer computer) {
    computer.setStatus("Not Available");
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

  // initializer methods
  public void initializeComputerList(int destination) {
    fetchComputers(destination);

    System.out.println("----------------------------------");
    System.out.println("Computer Lab " + destination + " - Available Computers");
    System.out.println("----------------------------------");

    int divider = 0;
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

  // fetch methods
  public void fetchComputers(int destination) {
    String filepath = "../res/assets/cl" + destination + "/computers.csv";

    try {
      Scanner reader = new Scanner(new File(filepath));
      boolean header = false;
      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        String[] fields = line.split(",");

        header = fields[0].equalsIgnoreCase("itemid") ? true : false;

        if (!header) {
          int id = Integer.parseInt(fields[0]);
          String name = fields[1];
          String type = fields[2];
          String status = fields[3];
          computers.add(new Computer(id, name, type, status, destination));
        }
      }
      reader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void fetchLaptops() {

  }

  public void fetchEquipments() {

  }

  // update inventory methods
  public void updateComputerInventory(List<Computer> computers) {
    String origFilePath = "../res/assets/cl" + computers.get(0).getDestination() + "/computers.csv";
    String tempFilePath = "../res/assets/cl" + computers.get(0).getDestination() + "/temp.csv";
    boolean header = true;

    try {
      Scanner reader = new Scanner(new File(origFilePath));
      PrintWriter writer = new PrintWriter(new File(tempFilePath));

      // getting the orig file header [itemId,itemName,type,status,destination]
      while (header) {
        String line = reader.nextLine();
        writer.print(line);
        header = false;
      }

      for (Computer computer : computers) {
        writer.printf("\n%d,%s,%s,%s,%d", computer.getId(), computer.getName(), computer.getType(), computer.getStatus(), computer.getDestination());
      }
      reader.close();
      writer.close();
      computers.clear();
    } catch (FileNotFoundException e) { 
      e.printStackTrace();
      return;
    }

    File originalFile = new File(origFilePath);
    File updatedFile = new File(tempFilePath);

    if (originalFile.delete()) {
      if (updatedFile.renameTo(originalFile)) {
        System.out.println("PC Inventory updated successfully!");
      } else {
        System.out.println("Failed to rename the temporary file.");
      }
    } else {
      System.out.println("Failed to delete the original file.");
    }

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