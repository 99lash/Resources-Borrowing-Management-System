public class Laptop extends Resource {
  String status;

  Laptop(int id, String name, String type, String status) {
    super(id, name, type);
    setStatus(status);
  }

  // GETTERS
  public String getStatus() {
    return status;
  }

  // SETTERS
  public void setStatus(String status) {
    this.status = status;
  }
}
