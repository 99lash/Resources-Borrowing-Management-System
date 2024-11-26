public class Account {
  private int id;
  private String username;
  private String password;
  private String role;
  
  Account(int id, String username, String password, String  role) {
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
    return String.format("username: %s\npassword: %s\nrole: %s", username, password, role);
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

  
  //update Method
  public void updateAccountDetails(String newUsername, String newPassword, String newRole) {
    
  }

  
}

/* 
 * 4. **Account**
   - Attributes:
     - accountId: int
     - username: String
     - password: String
     - role: String
   - Methods:
     - updateAccountDetails(newUsername: String, newPassword: String): void
     - getAccountInfo(): String
 */