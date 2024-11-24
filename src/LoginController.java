import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LoginController {
  private ArrayList<User> users;
  private User currentUser;

  LoginController() {
    users = new ArrayList<>();
  }

  private void setCurrentUser(User currentUser) {
    this.currentUser = currentUser;
  }

  public boolean authenticate(String username, String password) {
    String filepath = "../res/users.csv";

    try { // required 'tong try catch para sa file not found error. Kahit anong gawin mo
          // required try catch, or pwede ka naman mag throw exception
      Scanner reader = new Scanner(new File(filepath));

      while (reader.hasNextLine()) { // per line ang loop nito

        String line = reader.nextLine(); // line value = username,password,role
        String[] fields = line.split(","); // line split delimiter yung comma
        /*
         * line.split will return an array
         * so ganito na ang value niya
         * fields = {username, password, role};
         */

        /*
         * Existing na mga variables sa baba. Basta ganyan equivalent ng fields bawat
         * index
         * String username= fields[0];
         * String password = fields[1];
         * String role = fields[2];
         */

        // Dito ito nilagay ko line by line mula sa CSV file papunta sa ArrayList of Users object. Pag na-gets niyo 'to makikita niyo
        // na purpose ng DSA na subject natin.
        if (!fields[0].equals("username")) users.add(new User(fields[0], fields[1], fields[2]));

      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.err.println(e.getMessage() + " LoginController.java: authenticate method");
    }

    for (User user : users) {
      System.out.println(user.getUsername() + " " + user.getPassword() + " " + user.getRole());// uncomment mo kung gusto mo makita list of user accounts
      if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
        setCurrentUser(user);
        users.clear();
        return true;
      }
    }
    setCurrentUser(null);
    users.clear();
    return false;
  }

  public void initializeDashboard() {
    String userRole = currentUser.getRole();
    Dashboard dashboard;
    if (userRole.equals("admin"))
      dashboard = new AdminDashboard();
    else if (userRole.equals("staff"))
      dashboard = new StaffDashboard();
    else {
      System.out.println("Invalid Role");
      return;
    }

    dashboard.displayDashboard();
    logout();
  }

  public void logout() {
    setCurrentUser(null);
    System.out.println("Returning to login screen...\n");
  }
}