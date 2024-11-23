import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class PCInventory extends Inventory {
  private int destination;

  PCInventory(int itemId, String name, String type, String status, int destination) {
    super(itemId, name, status);
    this.destination = destination;
  }

  // GETTERS
  public int getDestination() {
    return destination;
  }

  public void doBorrow() {
    super.setBorrowed(true);
    super.setStatus("Not Available");
  }

  public void doReturn() {
    super.setBorrowed(false);
    super.setStatus("Available");
  }

  public void updateInventory(ArrayList<PCInventory> PCs) {
    String origFilePath = "../res/inventory/cl" + PCs.get(0).getDestination() + "/PC_Inventory.csv";
    String tempFilePath = "../res/inventory/cl" + PCs.get(0).getDestination() + "/temp.csv";
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

      for (PCInventory pc : PCs) {
        writer.printf("\n%d,%s,%s,%s,%d", pc.getId(), pc.getName(), pc.getType(), pc.getStatus(), pc.getDestination());
      }
      reader.close();
      writer.close();
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
