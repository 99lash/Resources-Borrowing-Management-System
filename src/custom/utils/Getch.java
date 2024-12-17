package custom.utils;
import java.io.IOException;
import java.util.Scanner;

public class Getch {
  static Scanner in = new Scanner(System.in);
  public Getch() {
    try {
      // Read a single byte (character)
      System.out.print("\nPress Enter key to continue.");
      System.in.read();
      // in.nextLine();
    } catch (IOException e) {
      System.out.println("Unable to getch.");
    }
  }
}
