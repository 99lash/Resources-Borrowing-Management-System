package custom.utils;

public class Clrscr {
  public Clrscr() {
    try {
      if (System.getProperty("os.name").contains("Windows")) {
        // Windows command to clear screen
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } else {
        // Unix-based OS command to clear screen
        new ProcessBuilder("clear").inheritIO().start().waitFor();
      }
    } catch (Exception e) {
      System.out.println("Error: Unable to clear screen");
    }
  }
}