# Resources Borrowing Management System

## Project Overview
The Resources Borrowing Management System is designed to manage the borrowing and returning of resources (e.g., computers, equipment) for an institution. The system tracks transactions, maintains an audit log of all actions, and allows administrators and staff to manage resources, students, and accounts efficiently.

## Features
1. **Dashboard**:
   - View recent borrowing activities.
   - Issue and return items.

2. **Borrowers Management**:
   - View borrowing records with sorting and filtering options.

3. **Inventory Management (Admin)**:
   - Add, update, delete, and view inventory items.

4. **Student Masterlist (Admin)**:
   - Add, update, delete, and view student records.

5. **Account Management (Admin)**:
   - Manage admin/staff accounts.

6. **Audit Logging**:
   - Log all system actions and export logs.

---

## Requirements

### Functional Requirements
1. **User Management**:
   - Admin and staff accounts with role-based access control.
   - Login functionality.
   - CRUD operations for user accounts.

2. **Inventory Management**:
   - Separate categories for computers and equipment.
   - CRUD operations for inventory items.

3. **Student Management**:
   - CRUD operations for student records.

4. **Borrowing/Returning**:
   - Record transactions for borrowed and returned items.
   - Track borrowing history.

5. **Audit Log**:
   - Log all system actions (e.g., login, CRUD operations).
   - Export logs for external use.

6. **Sorting and Filtering**:
   - Sort borrowing records by:
     - Date, Student ID, Item Name, etc.
   - Filter by categories (e.g., computers, equipment).

### Technical Requirements
- Programming Language: Java
- Data Storage: CSV files

---

## Project Directory Structure
![image](https://github.com/user-attachments/assets/f0c4422c-36a6-4211-8127-313dfb63b30d)


---

## Models and Objects

### 1. User
- **Attributes**:
  - `userID: int`
  - `username: String`
  - `password: String`
  - `role: String` (Admin/Staff)
- **Methods**:
  - `authenticate(String username, String password): boolean`
  - `getRole(): String`

### 2. InventoryItem (Abstract)
- **Attributes**:
  - `itemID: int`
  - `name: String`
  - `type: String`
  - `quantity: int`
  - `availableQuantity: int`
- **Methods**:
  - `getDetails(): String`

### 3. ComputerItem & EquipmentItem
- Inherit from `InventoryItem`.

### 4. Student
- **Attributes**:
  - `studentID: int`
  - `name: String`
  - `contact: String`
- **Methods**:
  - `getDetails(): String`

### 5. BorrowedItem
- **Attributes**:
  - `transactionID: int`
  - `studentID: int`
  - `itemID: int`
  - `borrowDate: Date`
  - `returnDate: Date` (nullable)
- **Methods**:
  - `markReturned()`

### 6. AuditLog
- **Attributes**:
  - `logID: int`
  - `action: String`
  - `timestamp: Date`
  - `details: String`
- **Methods**:
  - `exportLog(String filename)`

---

## Functions to Implement

### 1. AuthSystem
- `boolean login(String username, String password)`
- `boolean checkRole(String username)`

### 2. InventoryManager
- `void addItem(InventoryItem item)`
- `void updateItem(int itemID)`
- `void deleteItem(int itemID)`
- `List<InventoryItem> getAllItems()`

### 3. StudentManager
- `void addStudent(Student student)`
- `void updateStudent(int studentID)`
- `void deleteStudent(int studentID)`
- `List<Student> getAllStudents()`

### 4. BorrowingSystem
- `void issueItem(int studentID, int itemID)`
- `void returnItem(int transactionID)`

### 5. AuditLogManager
- `void logAction(String action, String details)`
- `List<String[]> exportLogs()`

---

## CSV File Structure

### users.csv
- users.csv: userID,username,password,role
- inventory.csv: itemID, name, type, quantity, availableQuantity
- students.csv: studentID, name, course, year, department
- borrowedItems.csv: transactionID, itemID, itemName, borrowerName, borrowedQuantity, borrowDate
- borrowLog: transactionID, itemID, itemName, borrowerName, borrowedQuantity, borrowDate, returnDate (nulllable)
- auditLog.csv: timestamp, action, timestamp, username, userRole,
