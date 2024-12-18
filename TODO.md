# Resources Borrowing Management System - TODO

## Classes and Relationships

**AppController.java** 
  - **InventoryController.java**
    - Resource.java
      1. Computer.java (extended)
      2. Laptop.java (extended)
      3. Equipment.java (extended)  
  - **AccountController.java**
    - Account.java
      1. Admin.java (extended)
      2. Staff.java (extended)
  - **BorrowTransactionController**
     1. BorrowTransaction.java
  - **StudentController.java**
     1. Student.java    
  - **AuditLogController.java**
     1. AuditLog.java

## Classes, Attributes, Methods

1. **Resource**
   - Attributes:
     - id: int
     - name: String
     - type: String
     - quantity: int
   - Methods:
     - addStock(quantity: int): void
     - removeStock(quantity: int): void
     - getDetails(): String

2. **Student**
   - Attributes:
     - studentId: String
     - name: String
     - email: String
   - Methods:
     - getStudentInfo(): String

3. **BorrowTransaction**
   - Attributes:
     - transactionId: int
     - student: Student
     - resource: Resource
     - quantity: int
     - date: LocalDateTime
     - isReturned: boolean
   - Methods:
     - markAsReturned(): void
     - getTransactionDetails(): String

4. **Account**
   - Attributes:
     - accountId: int
     - username: String
     - password: String
     - role: String
   - Methods:
     - updateAccountDetails(newUsername: String, newPassword: String): void
     - getAccountInfo(): String

5. **AuditLog**
   - Attributes:
     - logId: int
     - action: String
     - performedBy: Account
     - timestamp: LocalDateTime
   - Methods:
     - exportAsCSV(): void
     - getLogDetails(): String

6. **InventoryManager**
   - Attributes:
     - resources: List<Resource>
   - Methods:
     - addResource(resource: Resource): void
     - removeResource(resourceId: int): void
     - updateResource(resourceId: int, newQuantity: int): void
     - getResourceById(resourceId: int): Resource
     - listResources(): List<Resource>

7. **ResourceManager**
   - Attributes:
     - transactions: List<BorrowTransaction>
     - students: List<Student>
     - accounts: List<Account>
     - auditLogs: List<AuditLog>
     - inventoryManager: InventoryManager
   - Methods:
     - Borrowing and Returning:
       - borrowResource(studentId: String, resourceId: int, quantity: int): void
       - returnResource(transactionId: int): void
     - Logs:
       - viewBorrowerLog(filterBy: String, sortBy: String, ascending: boolean): List<BorrowTransaction>
       - viewAuditLog(): List<AuditLog>
     - Administrative Functions:
       - createAccount(account: Account): void
       - updateAccount(accountId: int, newDetails: Account): void
       - deleteAccount(accountId: int): void
       - manageStudent(student: Student, operation: String): void

---

This file serves as a checklist to track implementation progress for the project. Mark each item as complete `[x]` once implemented.****