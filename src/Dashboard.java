import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Dashboard {
  protected String title;
  protected Scanner in = new Scanner(System.in);

  public abstract void displayDashboard();

  public void borrowingForm() {
    int ch = 0;  
    do {
      System.out.println("Type of item to borrow?");
      System.out.println("1. PC");
      System.out.println("2. Laptop");
      System.out.println("3. Other Equipments");
      System.out.println("4. Cancel");
      System.out.print("Select: ");
      ch = in.nextInt();
      
      if (ch < 1 || ch > 4) System.out.println("Invalid selection! Please select between 1-4.");
    } while (ch < 1 || ch > 4);

    switch (ch) {
      case 1:
        initializePCList();
        break;

      case 2:
        System.out.println("Laptop");
        break;

      case 3:
        System.out.println("Other Equipments");
        break;

      case 4:
        return;
    }
  }

  // public void returningForm() {

  // }

  private void initializePCList() {
    System.out.print("SELECT COMPUTER LAB (1-3): ");
    int count = 0;
    int cl = in.nextInt();
    ArrayList<PCInventory> PCs;
    PCs = fetchPCs(cl);
    System.out.println("-----------------------------------");
    System.out.println("Computer Lab " + cl + " - Available PCs");
    System.out.println("-----------------------------------");
    for (PCInventory pc : PCs) {
      if (count == 5) {
        count = 0;
        System.out.println();
      }

      if (pc.getStatus().equalsIgnoreCase("available")) {
        System.out.printf("[%s]\t", pc.getName());
        count++;
      }
    }
    System.out.println("\n-----------------------------------");
    System.out.print("SELECT A PC: ");
    String ch = "PC" + String.valueOf(in.nextInt());

    for (PCInventory pc : PCs) {
      if (pc.getName().equals(ch)) {
        pc.doBorrow();
        pc.updateInventory(PCs);
      }
    }
    PCs.clear();
  }

  private ArrayList<PCInventory> fetchPCs(int destination) {
    ArrayList<PCInventory> PCs = new ArrayList<>();
    String filepath = "../res/inventory/cl" + destination + "/PC_Inventory.csv";
    try {
      Scanner reader = new Scanner(new File(filepath));
      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        String[] fields = line.split(",");

        if (!fields[0].equals("itemId")) {
          int itemId = Integer.parseInt(fields[0]);
          String itemName = fields[1];
          String type = fields[2];
          String status = fields[3];

          PCs.add(new PCInventory(itemId, itemName, type, status, destination));
          // System.out.println(fields[0] + " " + fields[1] + " " + fields[2] + " " + fields[3] + " " + fields[4] + "\n");
        }
      }
      reader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
    return PCs;
  }

}
