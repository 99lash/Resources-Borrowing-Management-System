public class Laptop extends Resource {
  String status;

  public Laptop(int id, String name, String type, String status) {
    super(id, name, type);
    setStatus(status);
  }

  public Laptop(int id, String name) {
    super(id, name, "Laptop");
    setStatus("Available");
  }

  @Override
  public String getDetails() {
    return String.format("Item Id: %d\nName: %s\nType: %s\nStatus: %s", super.getId(), super.getName(), super.getType(), status);
  }

  // GETTERS and SETTERS tabunuan niyo 'to
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
