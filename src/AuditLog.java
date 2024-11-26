import java.time.LocalDateTime;

public class AuditLog {
  private int logId;
  private String action;
  private Account performedBy;
  private LocalDateTime timestamp;
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