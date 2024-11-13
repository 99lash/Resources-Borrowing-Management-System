package model;

public class UserModel {
  private String username;
  private String password;
  private String role;

  UserModel(String username, String password) {
    this.username = username;
    this.password = password;
    this.role = "staff";
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRole(String role) {
    this.role = role;
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
  
}
