public class User {
  private int id;
  private String username;
  private String password;
  private String role;
  private static int userCount;
  
  User(String username, String password, String  role) {
    userCount++;
    this.id = userCount;
    this.username = username;
    this.password = password;
    this.role = role;
  }

  User(int id, String username, String password, String  role) {
    userCount = id;
    this.id = id;
    this.username = username;
    this.password = password;
    this.role = role;
  }

  // GETTERS
  public int getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  protected String getPassword() {
    return password;
  }

  public String getRole() {
    return role;
  }

  public String getAccountInfo() {
    return String.format("id: %d\nusername: %s\npassword: %s\nrole: %s\n",id, username, password, role);
  }

  // SETTERS
  public void setId(int id) {
    this.id = id;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }

  protected void setPassword(String password) {
    this.password = password;
  }

  public void setRole(String role) {
    this.role = role;
  }  
}