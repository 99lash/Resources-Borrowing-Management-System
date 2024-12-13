import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AuditLog {
  private LocalDateTime timestamp;
  private String user;
  private String entity;
  private String details;

  public AuditLog(LocalDateTime timestamp, String user, String entity, String details) {
    this.timestamp = timestamp;
    this.user = user;
    this.entity = entity;
    this.details = details;
  }
  
  // GETTERS and SETTERS (tabunan niyo 'to)  
  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getEntity() {
    return entity;
  }

  public void setEntity(String entity) {
    this.entity = entity;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  
  
}

/* 
 * 5. **AuditLog**
   - Attributes:
     - logId: int
     - action: String
     - performedBy: Account
     - timestamp: LocalDateTime
   - Methods:
     - exportAsCSV(): void
     - getLogDetails(): String
 */