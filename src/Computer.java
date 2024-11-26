public class Computer extends Resource {
  private int destination;
  private String status;

  public Computer(int id, String name, String type, String status, int destination) {
    super(id, name, type);
    setDestination(destination);
    setStatus(status);
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
