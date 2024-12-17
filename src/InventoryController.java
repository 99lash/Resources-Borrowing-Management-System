import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import custom.utils.Clrscr;
import custom.utils.Title;

import java.io.File;

public class InventoryController {
  private List<Resource> resources;
  private List<Computer> computers;
  private List<Laptop> laptops;
  private List<Equipment> equipments;
  static Scanner in = new Scanner(System.in);

  public InventoryController() {
    computers = new ArrayList<>();
    laptops = new ArrayList<>();
    equipments = new ArrayList<>();
  }

  public boolean addComputer() {
    new Clrscr();
    new Title();
    System.out.println("-----------------------------------------------------------");
    System.out.println("Administrative Panel -> Manage Inventory (Adding Computer)");
    System.out.println("-----------------------------------------------------------\n");

    int destination = 0;
    while (destination < 1 || destination > 3) {
      System.out.print("Enter computer lab to place the computer: ");
      destination = in.nextInt();
      in.nextLine();
    }
    fetchComputersFromDatabase(destination);
    System.out.print("Enter computer name: ");
    String newComputerName = in.nextLine().toUpperCase();
    newComputerName = newComputerName.contains("PC") ? newComputerName : "PC" + newComputerName;
    Computer computer = searchComputer(newComputerName);
    if (computer != null) {
      System.out.println("Computer is already exist!");
      return false;
    }
    Computer lastComputer = computers.get(computers.size() - 1);
    int newItemId = lastComputer.getId() + 1;
    computers.add(new Computer(newItemId, newComputerName, destination));
    return true;
  }

  public boolean addLaptop() {
    new Clrscr();
    new Title();
    System.out.println("-----------------------------------------------------------");
    System.out.println("Administrative Panel -> Manage Inventory (Adding Laptop)");
    System.out.println("-----------------------------------------------------------\n");

    fetchLaptopsFromDatabase();
    System.out.print("Enter laptop name: ");
    String newLaptopName = in.nextLine().toUpperCase();
    newLaptopName = newLaptopName.contains("LAPTOP") ? newLaptopName : "LAPTOP" + newLaptopName;
    Laptop laptop = searchLaptop(newLaptopName);

    if (laptop != null) {
      System.out.println("Laptop is already exist!");
      return false;
    }
    Laptop lastLaptop = laptops.get(laptops.size() - 1);
    int newItemId = lastLaptop.getId() + 1;
    laptops.add(new Laptop(newItemId, newLaptopName));
    return true;
  }

  public boolean addEquipment() {
    new Clrscr();
    new Title();
    System.out.println("-----------------------------------------------------------");
    System.out.println("Administrative Panel -> Manage Inventory (Adding Equipment)");
    System.out.println("-----------------------------------------------------------\n");

    fetchEquipmentsFromDatabase();
    System.out.print("Enter equipment name: ");
    String newEquipmentName = in.nextLine().toUpperCase();
    if (newEquipmentName.equalsIgnoreCase("Remote")) {
      System.out.print("Enter remote brand: ");
      String equipmentBrand = in.nextLine().toUpperCase();
      newEquipmentName = String.format("%s (%s)", newEquipmentName, equipmentBrand);
    }

    Equipment equipment = searchEquipment(newEquipmentName);
    if (equipment != null) {
      System.out.println(
          "Equipment is already exist! Please see [6] Update equipment details, if you want to increment/decrement quantity.");
      return false;
    }
    System.out.printf("Enter %s quantity: ", newEquipmentName);
    int initialQuantity = in.nextInt();
    in.nextLine();
    int newItemId = equipments.get(equipments.size() - 1).getId() + 1;
    equipments.add(new Equipment(newItemId, newEquipmentName, initialQuantity));
    return true;
  }

  public boolean updateComputerDetails() {
    new Clrscr();
    new Title();
    System.out.println("-----------------------------------------------------------");
    System.out.println("Administrative Panel -> Manage Inventory (Updating Computer)");
    System.out.println("-----------------------------------------------------------\n");
    int destination = 0;
    while (destination < 1 || destination > 3) {
      System.out.print("Enter computer lab: ");
      destination = in.nextInt();
      in.nextLine();
    }
    fetchComputersFromDatabase(destination);
    System.out.print("Find computer by name: ");
    String updatingComputer = in.nextLine().toUpperCase();
    updatingComputer = updatingComputer.contains("PC") ? updatingComputer : "PC" + updatingComputer;
    Computer computer = searchComputer(updatingComputer);
    if (computer == null) {
      System.out.println("Computer doesn't exist!");
      return false;
    }
    System.out.print("Enter new computer name: ");
    String newComputerName = in.nextLine();
    newComputerName = newComputerName.contains("PC") ? newComputerName : "PC" + newComputerName;
    if (updatingComputer.equalsIgnoreCase(newComputerName)) {
      System.out.println("You cannot replace old computer name with the same computer name.");
      return false;
    }
    computer.setName(newComputerName);
    return true;
  }

  public boolean updateLaptopDetails() {
    new Clrscr();
    new Title();
    System.out.println("-----------------------------------------------------------");
    System.out.println("Administrative Panel -> Manage Inventory (Updating Laptop)");
    System.out.println("-----------------------------------------------------------\n");
    fetchLaptopsFromDatabase();
    System.out.print("Find laptop by name: ");
    String updatingLaptop = in.nextLine().toUpperCase();
    updatingLaptop = updatingLaptop.contains("LAPTOP") ? updatingLaptop : "LAPTOP" + updatingLaptop;
    Laptop laptop = searchLaptop(updatingLaptop);
    if (laptop == null) {
      System.out.println("Laptop doesn't exist!");
      return false;
    }
    System.out.print("Enter new laptop name: ");
    String newLaptopName = in.nextLine();
    newLaptopName = newLaptopName.contains("LAPTOP") ? newLaptopName : "LAPTOP" + newLaptopName;
    if (updatingLaptop.equalsIgnoreCase(newLaptopName)) {
      System.out.println("You cannot replace old laptop name with the same laptop name.");
      return false;
    }
    laptop.setName(newLaptopName);
    return true;
  }

  public boolean updateEquipmentDetails() {
    new Clrscr();
    new Title();
    System.out.println("-----------------------------------------------------------");
    System.out.println("Administrative Panel -> Manage Inventory (Updating Equipment)");
    System.out.println("-----------------------------------------------------------\n");
    showAvailableEquipments();
    System.out.print("Select equipment by ID: ");
    int itemId = in.nextInt(); in.nextLine();
    Equipment equipment = searchEquipment(itemId);
    if (equipment == null) {
      System.out.println("Equipment doesn't exist!");
      return false;
    }
    System.out.println("[1] Name");
    System.out.println("[2] Quantity");
    System.out.println("[3] Cancel");
    System.out.print("Select equipment information to update: ");
    int ch = in.nextInt(); in.nextLine();

    if (ch == 1) {
      System.out.print("Enter equipment new name: ");
      String newEquipmentName = in.nextLine().toUpperCase();
      if (equipment.getName().equalsIgnoreCase(newEquipmentName)) {
        System.out.println("You cannot replace old equipment name with the same equipment name.");
        return false;
      }
      equipment.setName(newEquipmentName);
    } else if (ch == 2) {
      System.out.printf("Set a new quantity of %s: ", equipment.getName());
      int newQuantity = in.nextInt(); in.nextLine();
      if (equipment.getQuantity() == newQuantity) {
        System.out.println("You cannot replace old equipment quantity with the same equipment quantity.");
        return false;
      }
      int oldQuantity = equipment.getQuantity();
      int quantityGap = 0;
      if (oldQuantity > equipment.getAvailableQuantity()) {
        quantityGap = equipment.getQuantity() - equipment.getAvailableQuantity();
        equipment.setQuantity(newQuantity);
      } else {
        equipment.setQuantity(newQuantity);
        quantityGap = equipment.getQuantity() - equipment.getAvailableQuantity();
      }
      if (newQuantity < oldQuantity) {
        equipment.setAvailableQuantity(equipment.getAvailableQuantity() - quantityGap);
      } else {
        equipment.setAvailableQuantity(equipment.getAvailableQuantity() + quantityGap);
      }
    } else if (ch ==3) {
      return false;
    } else {
      System.out.println("Invalid select! Please select between 1 - 3");
      return false;
    }
    return true;
  }

  public void borrowItem(Computer computer) {
    computer.setStatus("Not Available");
  }

  public void borrowItem(Laptop laptop) {
    laptop.setStatus("Not Available");
  }

  public void borrowItem(Equipment equipment, int borrowQuantity) {
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

  public void returnItem(Computer computer) {
    computer.setStatus("Available");
  }

  public void returnItem(Laptop laptop) {
    laptop.setStatus("Available");
  }

  public void returnItem(Equipment equipment, int returnQuantity) {
    int currentAvailableQuantity = equipment.getAvailableQuantity();
    if ((returnQuantity + currentAvailableQuantity ) > equipment.getQuantity()) {
      System.out.println("Cannot return greater than the initial quantity.");
      return;
    }
    equipment.setAvailableQuantity(currentAvailableQuantity + returnQuantity);
    if (equipment.getAvailableQuantity() > 0) {
      equipment.setStatus("Available");
    }
  }

  public void displayComputerDetails() {
    new Clrscr();
    new Title();
    System.out.println("---------------------------------------------------");
    System.out.println("Administrative Panel -> Manage Inventory (Searching Computer)");
    System.out.println("---------------------------------------------------\n");
    int destination = 0;
    while (destination < 1 || destination > 3) {
      System.out.print("Enter computer lab: ");
      destination = in.nextInt();
      in.nextLine();
    }
    fetchComputersFromDatabase(destination);
    System.out.print("Find computer by name: ");
    String computerName = in.nextLine().toUpperCase();
    computerName = computerName.contains("PC") ? computerName : "PC" + computerName;
    Computer computer = searchComputer(computerName);
    if (computer == null) {
      System.out.println("Computer does not exist!");
    } else {
      System.out.println("----------------------------------------\n" + computer.getDetails());
    }
  }

  public void displayLaptopDetails() {
    new Clrscr();
    new Title();
    System.out.println("---------------------------------------------------");
    System.out.println("Administrative Panel -> Manage Inventory (Searching Laptop)");
    System.out.println("---------------------------------------------------\n");
    fetchLaptopsFromDatabase();
    System.out.print("Find laptop by name: ");
    String laptopName = in.nextLine().toUpperCase();
    laptopName = laptopName.contains("LAPTOP") ? laptopName : "LAPTOP" + laptopName;
    Laptop laptop = searchLaptop(laptopName);
    if (laptop == null) {
      System.out.println("Laptop does not exist");
    } else {
      System.out.println("----------------------------------------\n" + laptop.getDetails());
    }
  }

  public void displayEquipmentDetails() {
    new Clrscr();
    new Title();
    System.out.println("---------------------------------------------------");
    System.out.println("Administrative Panel -> Manage Inventory (Searching Equipment)");
    System.out.println("---------------------------------------------------\n");
    fetchEquipmentsFromDatabase();
    System.out.print("Find equipment by name: ");
    String equipmentName = in.nextLine().toUpperCase();
    if (equipmentName.equalsIgnoreCase("remote")) {
      System.out.print("Enter remote brand: ");
      String remoteBrand = in.nextLine().toUpperCase();
      equipmentName = String.format("%s (%s)", equipmentName, remoteBrand);
    }
    Equipment equipment = searchEquipment(equipmentName);
    if (equipment == null) {
      System.out.println("Equipment does not exist");
    } else {
      System.out.println("----------------------------------------\n" + equipment.getDetails());
    }
  }

  
  public boolean deleteComputer() {
    new Clrscr();
    new Title();
    System.out.println("-----------------------------------------------------------");
    System.out.println("Administrative Panel -> Manage Inventory (Deleting Computer)");
    System.out.println("-----------------------------------------------------------\n");

    int destination = 0;
    while (destination < 1 || destination > 3) {
      System.out.print("Enter computer lab: ");
      destination = in.nextInt();
      in.nextLine();
    }
    fetchComputersFromDatabase(destination);
    System.out.print("Enter computer name: ");
    String newComputerName = in.nextLine().toUpperCase();
    newComputerName = newComputerName.contains("PC") ? newComputerName : "PC" + newComputerName;
    Computer computer = searchComputer(newComputerName);
    if (computer == null) {
      System.out.println("Computer doesn't exists!");
      return false;
    } else if (computer.getStatus().equalsIgnoreCase("not available")) {
      System.out.println("This computer is still being borrowed by someone. Please delete it after return completely.");
      return false;
    }
    boolean confirmation = false;
    while (true) { 
      new Clrscr();
      new Title();
      System.out.println("-----------------------------------------------------------");
      System.out.println("Administrative Panel -> Manage Inventory (Deleting Computer)");
      System.out.println("-----------------------------------------------------------\n");
      System.out.println("---------------------------------------------------");
      System.out.println("Review account details to be deleted");
      System.out.println("---------------------------------------------------");
      System.out.println(computer.getDetails());
      System.out.print("Are you sure you want to delete this account? (Y/N): ");
      char ch = in.next().toUpperCase().charAt(0);
      if (ch == 'Y') {
        confirmation = true;
        computers.remove(computer);
        System.out.println("Computer deleted successfully.");
        break;
      } else if (ch == 'N') {
        System.out.println("Computer deleted unsuccessfully.");
        break;
      }
    }
    return confirmation;
  }

  public boolean deleteLaptop() {
    new Clrscr();
    new Title();
    System.out.println("-----------------------------------------------------------");
    System.out.println("Administrative Panel -> Manage Inventory (Deleting Laptop)");
    System.out.println("-----------------------------------------------------------\n");

    fetchLaptopsFromDatabase();
    System.out.print("Enter laptop name: ");
    String newLaptopName = in.nextLine().toUpperCase();
    newLaptopName = newLaptopName.contains("LAPTOP") ? newLaptopName : "LAPTOP" + newLaptopName;
    Laptop laptop = searchLaptop(newLaptopName);

    if (laptop == null) {
      System.out.println("Laptop doesn't exist!");
      return false;
    } else if (laptop.getStatus().equalsIgnoreCase("not available")) {
      System.out.println("This laptop is still being borrowed by someone. Please delete it after return completely.");
      return false;
    } 
    boolean confirmation = false;
    while (true) { 
      new Clrscr();
      new Title();
      System.out.println("-----------------------------------------------------------");
      System.out.println("Administrative Panel -> Manage Inventory (Deleting Laptop)");
      System.out.println("-----------------------------------------------------------\n");
      System.out.println("---------------------------------------------------");
      System.out.println("Review account details to be deleted");
      System.out.println("---------------------------------------------------");
      System.out.println(laptop.getDetails());
      System.out.print("Are you sure you want to delete this account? (Y/N): ");
      char ch = in.next().toUpperCase().charAt(0);
      if (ch == 'Y') {
        confirmation = true;
        laptops.remove(laptop);
        System.out.println("Laptop deleted successfully.");
        break;
      } else if (ch == 'N') {
        System.out.println("Laptop deleted unsuccessfully.");
        break;
      }
    }
    return confirmation;
  }

  public boolean deleteEquipment() {
    new Clrscr();
    new Title();
    System.out.println("-----------------------------------------------------------");
    System.out.println("Administrative Panel -> Manage Inventory (Deleting Equipment)");
    System.out.println("-----------------------------------------------------------\n");

    fetchEquipmentsFromDatabase();
    System.out.print("Enter equipment name: ");
    String newEquipmentName = in.nextLine().toUpperCase();
    if (newEquipmentName.equalsIgnoreCase("Remote")) {
      System.out.print("Enter remote brand: ");
      String equipmentBrand = in.nextLine().toUpperCase();
      newEquipmentName = String.format("%s (%s)", newEquipmentName, equipmentBrand);
    }

    Equipment equipment = searchEquipment(newEquipmentName);
    if (equipment == null) {
      System.out.println("Equipment doesn't exist");
      return false;
    } else if (equipment.getQuantity() != equipment.getAvailableQuantity()) {
      System.out.println("This equipment is still being borrowed by someone. Please delete it after return completely.");
      return false;
    }
    boolean confirmation = false;
    while (true) { 
      new Clrscr();
      new Title();
      System.out.println("-----------------------------------------------------------");
      System.out.println("Administrative Panel -> Manage Inventory (Deleting Equipment)");
      System.out.println("-----------------------------------------------------------\n");
      System.out.println("---------------------------------------------------");
      System.out.println("Review account details to be deleted");
      System.out.println("---------------------------------------------------");
      System.out.println(equipment.getDetails());
      System.out.print("Are you sure you want to delete this account? (Y/N): ");
      char ch = in.next().toUpperCase().charAt(0);
      if (ch == 'Y') {
        confirmation = true;
        equipments.remove(equipment);
        System.out.println("Equipment deleted successfully.");
        break;
      } else if (ch == 'N') {
        System.out.println("Equipment deleted unsuccessfully.");
        break;
      }
    }
    return confirmation;
  }

  public void displayAllComputers() {
    new Clrscr();
    new Title();
    int destination = 0;
    while (destination < 1 || destination > 3) {
      System.out.print("Enter computer lab: ");
      destination = in.nextInt();
      in.nextLine();
    }
    fetchComputersFromDatabase(destination);
    final int displayCountPerPage = 10;
    int totalRecords = computers.size();
    int totalPages = (int) Math.ceil((double) totalRecords / displayCountPerPage);
    int currentPage = 1;

    while (true) {
      new Clrscr();
      new Title();
      int start = (currentPage - 1) * displayCountPerPage;
      int end = Math.min(start + displayCountPerPage, totalRecords);

      System.out.printf("Computer Inventory (CL%d)\tShowing %d - %d entries  Page %d of %d\n",destination , start+1, end, currentPage, totalPages);
      System.out.println("---------------------------------------------------------------------");
      System.out.printf("| %-10s | %-20s | %-20s\t|\n", "Item ID", "Name", "Status");
      System.out.println("---------------------------------------------------------------------");
      for (int i = start; i < end; i++) {
        Computer computer = computers.get(i);
        System.out.printf("| %-10d | %-20s | %-20s\t|\n", computer.getId(), computer.getName(), computer.getStatus());
        System.out.println("---------------------------------------------------------------------");
      }
      if (totalPages == 1) {
        System.out.println("[Q] Quit");
      }else if (currentPage == 1) {
        System.out.println("[N] Next Page | [Q] Quit");
      } else if (currentPage == totalPages) {
        System.out.println("[P] Previous Page | [Q] Quit");
      } else {
        System.out.println("[N] Next Page | [P] Previous Page | [Q] Quit");
      }

      String ch = in.nextLine().trim().toUpperCase();

      if (ch.equals("N")) {
        if (currentPage < totalPages) {
          currentPage++;
        }
      } else if (ch.equals("P")) {
        if (currentPage > 1) {
          currentPage--;
        }
      } else if (ch.equals("Q")) {
        break;
      }
    }
  }

  public void displayAllLaptops() {
    new Clrscr();
    new Title();
    fetchLaptopsFromDatabase();
    final int displayCountPerPage = 10;
    int totalRecords = laptops.size();
    int totalPages = (int) Math.ceil((double) totalRecords / displayCountPerPage);
    int currentPage = 1;

    while (true) {
      new Clrscr();
      new Title();
      int start = (currentPage - 1) * displayCountPerPage;
      int end = Math.min(start + displayCountPerPage, totalRecords);

      System.out.printf("Laptop Inventory\t\tShowing %d - %d entries  Page %d of %d\n", start+1, end, currentPage, totalPages);
      System.out.println("---------------------------------------------------------------------");
      System.out.printf("| %-10s | %-20s | %-20s\t|\n", "Item ID", "Name", "Status");
      System.out.println("---------------------------------------------------------------------");
      for (int i = start; i < end; i++) {
        Laptop laptop = laptops.get(i);
        System.out.printf("| %-10d | %-20s | %-20s\t|\n", laptop.getId(), laptop.getName(), laptop.getStatus());
        System.out.println("---------------------------------------------------------------------");
      }
      if (totalPages == 1) {
        System.out.println("[Q] Quit");
      }else if (currentPage == 1) {
        System.out.println("[N] Next Page | [Q] Quit");
      } else if (currentPage == totalPages) {
        System.out.println("[P] Previous Page | [Q] Quit");
      } else {
        System.out.println("[N] Next Page | [P] Previous Page | [Q] Quit");
      }

      String ch = in.nextLine().trim().toUpperCase();

      if (ch.equals("N")) {
        if (currentPage < totalPages) {
          currentPage++;
        }
      } else if (ch.equals("P")) {
        if (currentPage > 1) {
          currentPage--;
        }
      } else if (ch.equals("Q")) {
        break;
      }
    }
  }

  public void displayAllEquipments() {
    new Clrscr();
    new Title();
    fetchEquipmentsFromDatabase();
    final int displayCountPerPage = 10;
    int totalRecords = equipments.size();
    int totalPages = (int) Math.ceil((double) totalRecords / displayCountPerPage);
    int currentPage = 1;

    while (true) {
      new Clrscr();
      new Title();
      int start = (currentPage - 1) * displayCountPerPage;
      int end = Math.min(start + displayCountPerPage, totalRecords);

      System.out.printf("Equipment Inventory\t\t\t\tShowing %d - %d entries  Page %d of %d\n", start+1, end, currentPage, totalPages);
      System.out.println("-------------------------------------------------------------------------------------------");
      System.out.printf("| %-10s | %-20s | %-10s | %-20s | %-10s\t|\n", "Item ID", "Name", "Quantity", "Available Quantity", "Status");
      System.out.println("-------------------------------------------------------------------------------------------");
      for (int i = start; i < end; i++) {
        Equipment equipment = equipments.get(i);
        System.out.printf("| %-10d | %-20s | %-10d | %-20d | %-10s\t|\n", equipment.getId(), equipment.getName(), equipment.getQuantity(), equipment.getAvailableQuantity(), equipment.getStatus());
        System.out.println("-------------------------------------------------------------------------------------------");
      }
      if (totalPages == 1) {
        System.out.println("[Q] Quit");
      }else if (currentPage == 1) {
        System.out.println("[N] Next Page | [Q] Quit");
      } else if (currentPage == totalPages) {
        System.out.println("[P] Previous Page | [Q] Quit");
      } else {
        System.out.println("[N] Next Page | [P] Previous Page | [Q] Quit");
      }

      String ch = in.nextLine().trim().toUpperCase();

      if (ch.equals("N")) {
        if (currentPage < totalPages) {
          currentPage++;
        }
      } else if (ch.equals("P")) {
        if (currentPage > 1) {
          currentPage--;
        }
      } else if (ch.equals("Q")) {
        break;
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
    System.out.println("------------------------------------------------------");
    System.out.println("Computer Lab " + destination + " - Available Computers");
    System.out.println("------------------------------------------------------");

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
    System.out.print("|\t\t\t\t    Available Laptops  \t\t\t\t\t|");
    ;
    System.out.println("\n+---------------------------------------------------------------------------------------+\n");
    for (Laptop laptop : laptops) {
      if (divider == 5) {
        System.out.println("\n");
        divider = 0;
      }
      if (laptop.getStatus().equalsIgnoreCase("available")) {
        if (divider == 0) {
          System.out.printf("\t[%s]\t", laptop.getName());
        } else {
          System.out.printf("[%s]\t", laptop.getName());
        }
        divider++;
      }
    }
    System.out.println("\n\n-----------------------------------------------------------------------------------------");
  }

  public void showAvailableEquipments() {
    fetchEquipmentsFromDatabase();
    int itemId = 1;

    System.out.println("\n+---------------------------------------------------------------------------------------+");
    System.out.print("|\t\t\t\t    Available Equipments  \t\t\t\t|");
    ;
    System.out.println("\n+---------------------------------------------------------------------------------------+\n");
    System.out.printf("\t%-7s\t\t%-20s\t%-9s\t%-20s\n", "Item ID", "Equipment", "Quantity", "Available Quantity");
    System.out.println("+---------------------------------------------------------------------------------------+");
    for (Equipment equipment : equipments) {
      String availableQuantity = equipment.getAvailableQuantity() != 0
          ? String.valueOf(equipment.getAvailableQuantity())
          : "Out of stock";
      System.out.printf("\t%-7d\t\t%-20s\t%-10s\t%-19s\n", itemId, equipment.getName(),
          String.valueOf(equipment.getQuantity()), availableQuantity);
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

  public Computer searchComputer(int itemId) {
    for (Computer computer : computers) {
      if (computer.getId() == itemId) {
        return computer;
      }
    }
    return null;
  }
  
  public Laptop searchLaptop(int itemId) {
    for (Laptop laptop : laptops) {
      if (laptop.getId() == itemId) {
        return laptop;
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

  private Equipment searchEquipment(String itemName) {
    itemName = itemName.replace(" ", "");
    for (Equipment equipment : equipments) {
      String equipmentName = equipment.getName().replace(" ", "");
      if (equipmentName.equalsIgnoreCase(itemName)) {
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
      } else {
        // 404R: Couldn't rename file
        System.out.println("[COMPUTER INVENTORY UPDATE STATUS: 404R]");
      }
    } else {
      // 404D: Couldn't delete file
      System.out.println("[COMPUTER INVENTORY UPDATE STATUS: 404D]");
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
      } else {
        // 404R: Couldn't rename file
        System.out.println("[LAPTOP INVENTORY UPDATE STATUS: 404R]");
      }
    } else {
      // 404D: Couldn't delete file
      System.out.println("[LAPTOP INVENTORY UPDATE STATUS: 404D]");
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
        writer.printf("\n%d,%s,%s,%d,%d,%s", itemId, itemName, type, quantity, availableQuantity, status);
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
      } else {
        // 404R: Couldn't rename file
        System.out.println("[EQUIPMENT INVENTORY UPDATE STATUS: 404R]");
      }
    } else {
      // 404D: Couldn't delete file
      System.out.println("[EQUIPMENT INVENTORY UPDATE STATUS: 404D]");
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