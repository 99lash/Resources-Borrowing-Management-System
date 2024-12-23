package custom.utils;

public class Title {
  public static final String RESET = "\u001B[0m"; // Reset color
  public static final String GREEN = "\u001B[32m";

  public Title() {
    System.out.println("\n\n" + GREEN +
        String.format(
                " ██████╗███████╗██████╗     ███╗   ███╗██╗███████╗\r\n" + //
                "██╔════╝██╔════╝██╔══██╗    ████╗ ████║██║██╔════╝\r\n" + //
                "██║     ███████╗██║  ██║    ██╔████╔██║██║███████╗\r\n" + //
                "██║     ╚════██║██║  ██║    ██║╚██╔╝██║██║╚════██║\r\n" + //
                "╚██████╗███████║██████╔╝    ██║ ╚═╝ ██║██║███████║\r\n" + //
                " ╚═════╝╚══════╝╚═════╝     ╚═╝     ╚═╝╚═╝╚══════╝\r\n" + //
                "                                                  ") + RESET);
    // System.out.println("========================================================================================================================================================================================\n\n");
    // System.out.println("\t\t\t\t\t\t\t\t===== Resources Borrowing Management System ======\n\n");
    System.out.println("===== Resources Borrowing Management System ======\n\n");
  }
}
