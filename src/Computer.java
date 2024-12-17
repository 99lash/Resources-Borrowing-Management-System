public class Computer extends Resource {
  private int destination;
  private String status;

  public Computer(int id, String name, String type, String status, int destination) {
    super(id, name, type);
    setDestination(destination);
    setStatus(status);
  }

  public Computer (int id, String name, int destination) {
    super(id, name, "Computer");
    setDestination(destination);
    setStatus("Available");
  }

  @Override
  public String getDetails() {
    return String.format("Item Id: %d\nName: %s\nType: %s\nStatus: %s", super.getId(), super.getName(), super.getType(), status);
  }

  // GETTERS
  public int getDestination() {
    return destination;
  }

  public String getStatus() {
    return status;
  }

  // SETTERS
  public void setDestination(int destination) {
    this.destination = destination;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
