import java.util.ArrayList;

public class LoginController {
  private ArrayList<User> users;
  private User currentUser;

  LoginController() {
    users = new ArrayList<>();
    users.add(new User("admin1", "123", "admin"));
    users.add(new User("staff1", "123", "staff"));
  }

  private void setCurrentUser(User currentUser) {
    this.currentUser = currentUser;
  }

  public boolean authenticate(String username, String password) {
    for (User user : users) {
      if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
        setCurrentUser(user);
        return true;
      }
    }
    setCurrentUser(null);
    return false;
  }

  public void initializeDashboard() {
    String userRole = currentUser.getRole();
    Dashboard dashboard;
    if (userRole.equals("admin"))
      dashboard = new AdminDashboard();
    else
      dashboard = new StaffDashboard();

    dashboard.displayDashboard();
    logout();
  }

  public void logout() {
    setCurrentUser(null);
    System.out.println("Returning to login screen...\n");
  }
}
