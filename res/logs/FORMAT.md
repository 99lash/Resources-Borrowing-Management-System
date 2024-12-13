**AUDIT LOG**
Timestamp          User            Event               Entity                 Details
-----------------------------------------------------------------------------------------
2024-12-08 08:00   johndoe         LOGIN               N/A                   Successful login
2024-12-08 08:05   johndoe         LOGIN               N/A                   Incorrect password
2024-12-08 08:10   johndoe         LOGIN               N/A                   Account locked after 3 failed attempts
2024-12-08 08:20   johndoe         CREATE              Order #12345          Order successfully created
2024-12-08 08:25   johndoe         CREATE              Order #12346          Missing required fields: customerName
2024-12-08 08:30   johndoe         UPDATE              Order #12345          Order status updated to 'Shipped'
2024-12-08 08:35   johndoe         UPDATE              Order #12346          Invalid status value: "Shipeed"
2024-12-08 08:40   admin           DELETE              Order #12345          Order successfully deleted
2024-12-08 08:45   johndoe         DELETE              Order #12346          User lacks permission to delete the order
2024-12-08 08:50   admin           GRANT PERMISSION    User #5678            Granted "Admin" role
2024-12-08 08:55   admin           REVOKE PERMISSION   User #5678            Revoked "Staff" role
2024-12-08 09:00   admin           CREATE              User #5678            Successfully created staff account: johndoe
2024-12-08 09:01   admin           CREATE              User Account N/A      Failed to create user account: johndoe (Username already exists)
2024-12-08 09:00   johndoe         READ                File: report.pdf      File accessed successfully
2024-12-08 09:05   johndoe         READ                File: secret.pdf      File does not exist
2024-12-08 09:10   johndoe         READ                File: confidential.txt User lacks permission to access the file
2024-12-08 09:15   System          SYSTEM STARTUP      N/A                   System started successfully
2024-12-08 09:20   System          ERROR               Database              Connection timed out
2024-12-08 09:25   System          WARNING             Memory Usage          Memory usage exceeded 80%
2024-12-08 09:30   johndoe         LOGOUT              N/A                   User logged out

**TRANSACTION LOG**
Timestamp          Person                               Event              Transaction ID          Details                   Status
-------------------------------------------------------------------------------------------------------------------------------------------------------
2024-12-10 14:35   LANCE ALCALDE    #20230183-s         BORROW             TXN-0012345678          borrowed Laptop1 #1       Success
2024-12-10 15:00   JOHN ASHER MANIT #20230184-S         RETURN             TXN-0012345679          Returned PC2-CL1 #2       Success
2024-12-10 15:15   JOSHUA COLMO     #123123-S           BORROW             TXN-0012345680          Borrowed PC1-Cl1 #1       Failed