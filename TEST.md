1. Classes and Relationships
  Classes:
  Student
  Item
  BorrowerLog
  AuditLog
  User (Admin/Staff)
  Inventory
  ResourceManager
  
  Relationships:
  A Student can borrow multiple Items (one-to-many).
  BorrowerLog tracks borrowing and returning transactions, associated with Student and Item.
  AuditLog tracks administrative actions performed by User.
  User manages Inventory, BorrowerLog, and Student records.
  Inventory contains different categories of items (Laptop, Computer, Equipment).

-Class Details
  1. Student
    Attributes:
    studentId: String
    name: String
    course: String
    
    Methods:
    getDetails(): String - Returns a formatted string of student details.
    updateDetails(name: String, course: String): void - Updates student information.
   
  2. Item
    Attributes:
    itemId: String
    name: String
    category: String (e.g., Laptop, Computer, Equipment)
    quantity: int
    
    Methods:
    updateStock(quantity: int): void - Updates the stock quantity.
    getDetails(): String - Returns a string with item details.
   
  3. BorrowerLog
    Attributes:
    logId: int
    student: Student
    item: Item
    borrowDate: LocalDateTime
    returnDate: LocalDateTime (nullable if not yet returned)
    quantity: int
    
    Methods:
    addEntry(student: Student, item: Item, quantity: int): void - Adds a borrowing log.
    markAsReturned(logId: int): void - Updates the log to mark the item as returned.
    getLogsByFilter(filter: String, order: String): List<BorrowerLog> - Returns logs based on filters like date, student ID, or quantity.
  
  4. AuditLog
    Attributes:
    logId: int
    action: String (e.g., "Add Account", "Delete Account")
    performedBy: User
    timestamp: LocalDateTime
    
    Methods:
    addEntry(action: String, performedBy: User): void - Adds an audit entry.
    exportAsCSV(): File - Exports logs to a CSV file.
  
  5. User
    Attributes:
    userId: String
    username: String
    password: String
    role: String (e.g., Admin, Staff)
    
    Methods:
    createAccount(username: String, password: String, role: String): void - Creates a new user account.
    updateAccount(userId: String, username: String, password: String): void - Updates user details.
    deleteAccount(userId: String): void - Deletes a user account.
    viewAllAccounts(): List<User> - Returns a list of all accounts.
  
  6. Inventory
    Attributes:
    items: Map<String, Item> - A map of item IDs to their details.
    
    Methods:
    addItem(item: Item): void - Adds a new item to inventory.
    removeItem(itemId: String): void - Removes an item from inventory.
    searchItemByCategory(category: String): List<Item> - Returns items by category.
    listAllItems(): List<Item> - Returns all items in the inventory.
  
  7. ResourceManager
    Attributes:
    students: List<Student>
    inventory: Inventory
    borrowerLogs: List<BorrowerLog>
    auditLogs: List<AuditLog>
    
    Methods:
    borrowItem(studentId: String, itemId: String, quantity: int): void - Handles borrowing.
    returnItem(logId: int): void - Handles returning.
    viewBorrowerLogs(filter: String, order: String): List<BorrowerLog> - Displays borrower logs.
    manageStudentList(action: String, student: Student): void - Handles CRUD for students.
    logout(): void - Logs out the current user.
