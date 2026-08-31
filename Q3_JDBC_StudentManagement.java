import java.sql.*;
import java.util.Scanner;

/**
 * Q3. JDBC - Insert and Display [10 Marks]
 * 
 * Question Requirements:
 * 1. Create a Java JDBC program to connect to MySQL database 'college'
 * 2. Use a table named 'student' with columns: id, name, course
 * 3. Insert a student record
 * 4. Display all student records using SELECT
 * 5. Use PreparedStatement
 * 
 * This program demonstrates JDBC connectivity with MySQL and proper
 * PreparedStatement usage for both INSERT and SELECT operations.
 */

public class Q3_JDBC_StudentManagement {
    
    // MySQL Database Configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/college";
    private static final String DB_USER = "college_admin";
    private static final String DB_PASSWORD = "college_admin@2024";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    // SQL Queries using PreparedStatement (Requirement: Use PreparedStatement)
    private static final String INSERT_STUDENT = "INSERT INTO student (id, name, course) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_STUDENTS = "SELECT id, name, course FROM student ORDER BY id";
    private static final String SELECT_STUDENT_BY_ID = "SELECT id, name, course FROM student WHERE id = ?";
    private static final String DELETE_STUDENT = "DELETE FROM student WHERE id = ?";
    
    private static Connection connection;
    private static Scanner scanner = new Scanner(System.in);
    
    /**
     * Step 1: Establish connection with MySQL database 'college'
     * This satisfies the requirement: Connect to MySQL database 'college'
     */
    public static void connectDatabase() {
        try {
            // Load MySQL JDBC Driver
            Class.forName(DB_DRIVER);
            
            // Establish connection to college database
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("\n✓ Successfully connected to MySQL database 'college'");
            System.out.println("  Server: localhost:3306");
            System.out.println("  Database: college");
            System.out.println("  User: college_admin\n");
            
        } catch (ClassNotFoundException e) {
            System.err.println("\n✗ MySQL JDBC Driver not found!");
            System.err.println("  Please add mysql-connector-java JAR file to your classpath.");
            System.err.println("  Error: " + e.getMessage() + "\n");
            System.exit(1);
        } catch (SQLException e) {
            System.err.println("\n✗ Failed to connect to database!");
            System.err.println("  Make sure MySQL is running and credentials are correct.");
            System.err.println("  Error: " + e.getMessage() + "\n");
            System.exit(1);
        }
    }
    
    /**
     * Create the 'student' table if it doesn't already exist
     * Table schema: id (PRIMARY KEY), name (VARCHAR), course (VARCHAR)
     * This ensures the table exists for our operations
     */
    public static void createStudentTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS student (" +
                "id INT PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "course VARCHAR(100) NOT NULL" +
                ")";
        
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
            System.out.println("✓ Table 'student' verified/created successfully");
            System.out.println("  Schema: id (INT, PRIMARY KEY), name (VARCHAR), course (VARCHAR)\n");
        } catch (SQLException e) {
            System.err.println("✗ Error creating table: " + e.getMessage() + "\n");
        }
    }
    
    /**
     * Requirement 1 & 3: Insert a student record using PreparedStatement
     * This satisfies: "Insert a student record" and "Use PreparedStatement"
     * 
     * PreparedStatement prevents SQL injection and handles data types safely
     * 
     * @param studentId - Unique student ID (Primary Key)
     * @param studentName - Full name of the student
     * @param studentCourse - Course name enrolled by student
     */
    public static void insertStudentRecord(int studentId, String studentName, String studentCourse) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT)) {
            
            // Use PreparedStatement with parameterized queries (? placeholders)
            preparedStatement.setInt(1, studentId);          // Set ID parameter
            preparedStatement.setString(2, studentName);    // Set Name parameter
            preparedStatement.setString(3, studentCourse);  // Set Course parameter
            
            // Execute INSERT query
            int rowsInserted = preparedStatement.executeUpdate();
            
            if (rowsInserted > 0) {
                System.out.println("\n✓ Student record inserted successfully!");
                System.out.println("  ├─ ID: " + studentId);
                System.out.println("  ├─ Name: " + studentName);
                System.out.println("  └─ Course: " + studentCourse + "\n");
            }
            
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.err.println("\n✗ Error: Student with ID " + studentId + " already exists in database!\n");
            } else {
                System.err.println("\n✗ Error inserting student: " + e.getMessage() + "\n");
            }
        }
    }
    
    /**
     * Requirement 2: Display all student records using SELECT with PreparedStatement
     * This satisfies: "Display all student records using SELECT" and "Use PreparedStatement"
     * 
     * Retrieves and displays all students from the 'student' table
     */
    public static void displayAllStudents() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENTS)) {
            
            // Execute SELECT query using PreparedStatement
            ResultSet resultSet = preparedStatement.executeQuery();
            
            System.out.println("\n╔════════════════════════════════════════════════════════╗");
            System.out.println("║             ALL STUDENT RECORDS (FROM DATABASE)         ║");
            System.out.println("╠════════════════════════════════════════════════════════╣");
            
            boolean hasRecords = false;
            
            // Iterate through ResultSet and display records
            while (resultSet.next()) {
                hasRecords = true;
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String course = resultSet.getString("course");
                
                System.out.printf("║ ID: %3d │ Name: %-25s │ Course: %-15s ║%n", 
                                id, name, course);
            }
            
            System.out.println("╚════════════════════════════════════════════════════════╝");
            
            if (!hasRecords) {
                System.out.println("⚠ No student records found in the database.\n");
            } else {
                System.out.println();
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error retrieving student records: " + e.getMessage() + "\n");
        }
    }
    
    /**
     * Display a specific student record by ID using PreparedStatement
     * 
     * @param studentId - ID of the student to search
     */
    public static void searchStudentById(int studentId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_BY_ID)) {
            
            // Use PreparedStatement with parameter
            preparedStatement.setInt(1, studentId);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                System.out.println("\n✓ Student found:");
                System.out.println("  ├─ ID: " + resultSet.getInt("id"));
                System.out.println("  ├─ Name: " + resultSet.getString("name"));
                System.out.println("  └─ Course: " + resultSet.getString("course") + "\n");
            } else {
                System.out.println("\n✗ No student found with ID: " + studentId + "\n");
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error searching student: " + e.getMessage() + "\n");
        }
    }
    
    /**
     * Delete a student record by ID using PreparedStatement
     * 
     * @param studentId - ID of the student to delete
     */
    public static void deleteStudent(int studentId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STUDENT)) {
            
            preparedStatement.setInt(1, studentId);
            int rowsDeleted = preparedStatement.executeUpdate();
            
            if (rowsDeleted > 0) {
                System.out.println("\n✓ Student record with ID " + studentId + " deleted successfully!\n");
            } else {
                System.out.println("\n✗ No student found with ID " + studentId + " to delete.\n");
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error deleting student: " + e.getMessage() + "\n");
        }
    }
    
    /**
     * Close the database connection
     * Important for resource management
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("✗ Error closing connection: " + e.getMessage());
        }
    }
    
    /**
     * Display main menu and handle user choices
     */
    public static void displayMenu() {
        boolean running = true;
        
        while (running) {
            System.out.println("\n╔═══════════════════════════════════════════╗");
            System.out.println("║      STUDENT DATABASE MANAGEMENT SYSTEM    ║");
            System.out.println("╠═══════════════════════════════════════════╣");
            System.out.println("║ 1. Insert a new student record             ║");
            System.out.println("║ 2. Display all student records             ║");
            System.out.println("║ 3. Search student by ID                    ║");
            System.out.println("║ 4. Delete student record                   ║");
            System.out.println("║ 5. Exit program                            ║");
            System.out.println("╚═══════════════════════════════════════════╝");
            System.out.print("Enter your choice (1-5): ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        insertNewStudent();
                        break;
                    case 2:
                        displayAllStudents();
                        break;
                    case 3:
                        searchStudent();
                        break;
                    case 4:
                        deleteStudentRecord();
                        break;
                    case 5:
                        running = false;
                        System.out.println("\n✓ Thank you for using Student Database Management System!");
                        break;
                    default:
                        System.out.println("\n✗ Invalid choice! Please enter a number between 1 and 5.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n✗ Invalid input! Please enter a numeric choice.\n");
            }
        }
    }
    
    /**
     * Helper method to insert a new student with user input
     */
    private static void insertNewStudent() {
        try {
            System.out.println("\n--- INSERT NEW STUDENT RECORD ---");
            
            System.out.print("Enter Student ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("Enter Student Name: ");
            String name = scanner.nextLine().trim();
            
            System.out.print("Enter Course Name: ");
            String course = scanner.nextLine().trim();
            
            if (name.isEmpty() || course.isEmpty()) {
                System.out.println("✗ Name and Course cannot be empty!\n");
                return;
            }
            
            insertStudentRecord(id, name, course);
            
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid ID format! Please enter a valid number.\n");
        }
    }
    
    /**
     * Helper method to search student with user input
     */
    private static void searchStudent() {
        try {
            System.out.print("\nEnter Student ID to search: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            searchStudentById(id);
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid ID format! Please enter a valid number.\n");
        }
    }
    
    /**
     * Helper method to delete student with user input
     */
    private static void deleteStudentRecord() {
        try {
            System.out.print("\nEnter Student ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("Are you sure? (yes/no): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();
            
            if (confirmation.equals("yes") || confirmation.equals("y")) {
                deleteStudent(id);
            } else {
                System.out.println("✓ Operation cancelled.\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid ID format! Please enter a valid number.\n");
        }
    }
    
    /**
     * Run automated demo with sample data
     */
    public static void runDemo() {
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("              DEMO MODE - AUTOMATED EXECUTION");
        System.out.println("═══════════════════════════════════════════════════════════");
        
        System.out.println("\n--- INSERTING SAMPLE STUDENT RECORDS ---");
        insertStudentRecord(101, "Rahul Vijayvargiya", "Advanced Java");
        insertStudentRecord(102, "Priya Sharma", "Web Development");
        insertStudentRecord(103, "Amit Kumar", "Database Management");
        insertStudentRecord(104, "Neha Verma", "Advanced Java");
        insertStudentRecord(105, "Vikram Singh", "Spring Boot");
        
        System.out.println("--- DISPLAYING ALL RECORDS FROM DATABASE ---");
        displayAllStudents();
        
        System.out.println("--- SEARCHING FOR SPECIFIC STUDENT ---");
        searchStudentById(103);
        
        System.out.println("--- SEARCHING FOR ANOTHER STUDENT ---");
        searchStudentById(105);
    }
    
    /**
     * Main method - Entry point of the program
     */
    public static void main(String[] args) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║  Q3: JDBC - Insert and Display Student Records [10 MARKS]    ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Requirements:                                                ║");
        System.out.println("║  ✓ Connect to MySQL database 'college'                       ║");
        System.out.println("║  ✓ Use table 'student' with columns: id, name, course        ║");
        System.out.println("║  ✓ Insert a student record                                   ║");
        System.out.println("║  ✓ Display all student records using SELECT                  ║");
        System.out.println("║  ✓ Use PreparedStatement for all queries                     ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        
        try {
            // Step 1: Connect to MySQL database 'college'
            System.out.println("\n[Step 1] Connecting to MySQL database...");
            connectDatabase();
            
            // Step 2: Ensure student table exists
            System.out.println("[Step 2] Creating/verifying student table...");
            createStudentTable();
            
            // Step 3: Check if demo mode is requested
            System.out.println("[Step 3] Starting application...\n");
            
            if (args.length > 0 && args[0].equalsIgnoreCase("demo")) {
                runDemo();
                System.out.println("\n--- END OF DEMO ---\n");
            } else {
                // Interactive mode
                displayMenu();
            }
            
        } finally {
            // Always close connection
            closeConnection();
        }
    }
}
