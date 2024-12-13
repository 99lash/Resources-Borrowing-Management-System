public class Laptop extends Resource {
  String status;

  Laptop(int id, String name, String type, String status) {
    super(id, name, type);
    setStatus(status);
  }

  // GETTERS and SETTERS tabunuan niyo 'to
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
