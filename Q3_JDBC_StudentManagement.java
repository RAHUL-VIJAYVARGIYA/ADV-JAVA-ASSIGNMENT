import java.sql.*;
import java.util.Scanner;

/**
 * Q3. JDBC - Insert and Display [10 Marks]
 * 
 * Create a Java JDBC program to connect to MySQL database 'college'.
 * Use a table named 'student' with columns: id, name, course.
 * 
 * Perform the following:
 * 1. Insert a student record.
 * 2. Display all student records using SELECT.
 * 3. Use PreparedStatement.
 */

public class Q3_JDBC_StudentManagement {
    
    // MySQL Database Connection Parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/college";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password"; // Change as per your MySQL password
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    // SQL Queries using PreparedStatement
    private static final String INSERT_STUDENT = "INSERT INTO student (id, name, course) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_STUDENTS = "SELECT id, name, course FROM student";
    private static final String SELECT_STUDENT_BY_ID = "SELECT id, name, course FROM student WHERE id = ?";
    
    private static Connection connection;
    
    /**
     * Establish connection with MySQL database
     */
    public static void connectToDatabase() {
        try {
            // Load JDBC Driver
            Class.forName(DB_DRIVER);
            System.out.println("✓ JDBC Driver loaded successfully");
            
            // Establish connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("✓ Connected to MySQL database 'college' successfully\n");
            
        } catch (ClassNotFoundException e) {
            System.err.println("✗ JDBC Driver not found: " + e.getMessage());
            System.exit(1);
        } catch (SQLException e) {
            System.err.println("✗ Database connection failed: " + e.getMessage());
            System.exit(1);
        }
    }
    
    /**
     * Create the student table if it doesn't exist
     */
    public static void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS student (" +
                "id INT PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "course VARCHAR(100) NOT NULL" +
                ")";
        
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
            System.out.println("✓ Student table verified/created successfully\n");
        } catch (SQLException e) {
            System.err.println("✗ Error creating table: " + e.getMessage());
        }
    }
    
    /**
     * Insert a student record using PreparedStatement
     * 
     * @param id - Student ID
     * @param name - Student Name
     * @param course - Course Name
     * @return true if insertion successful, false otherwise
     */
    public static boolean insertStudent(int id, String name, String course) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT)) {
            
            // Set parameters using PreparedStatement
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, course);
            
            // Execute the insert query
            int rowsAffected = preparedStatement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✓ Student record inserted successfully!");
                System.out.println("  ID: " + id + ", Name: " + name + ", Course: " + course + "\n");
                return true;
            } else {
                System.err.println("✗ Failed to insert student record\n");
                return false;
            }
            
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.err.println("✗ Error: Student with ID " + id + " already exists!\n");
            } else {
                System.err.println("✗ Error inserting student: " + e.getMessage() + "\n");
            }
            return false;
        }
    }
    
    /**
     * Display a specific student record by ID using PreparedStatement
     * 
     * @param id - Student ID to search
     */
    public static void displayStudentById(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_BY_ID)) {
            
            // Set parameter using PreparedStatement
            preparedStatement.setInt(1, id);
            
            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                System.out.println("✓ Student found:");
                System.out.println("  ID: " + resultSet.getInt("id"));
                System.out.println("  Name: " + resultSet.getString("name"));
                System.out.println("  Course: " + resultSet.getString("course") + "\n");
            } else {
                System.out.println("✗ No student found with ID: " + id + "\n");
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error retrieving student: " + e.getMessage() + "\n");
        }
    }
    
    /**
     * Display all student records using PreparedStatement and SELECT
     */
    public static void displayAllStudents() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENTS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║       ALL STUDENT RECORDS              ║");
            System.out.println("╠════════════════════════════════════════╣");
            
            boolean hasRecords = false;
            
            // Process the result set
            while (resultSet.next()) {
                hasRecords = true;
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String course = resultSet.getString("course");
                
                System.out.printf("║ ID: %-3d | Name: %-20s | Course: %-10s ║%n", id, name, course);
            }
            
            System.out.println("╚════════════════════════════════════════╝");
            
            if (!hasRecords) {
                System.out.println("✗ No student records found in the database.\n");
            } else {
                System.out.println();
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error retrieving student records: " + e.getMessage() + "\n");
        }
    }
    
    /**
     * Delete all records from student table (for testing purposes)
     */
    public static void deleteAllRecords() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM student");
            System.out.println("✓ All student records deleted\n");
        } catch (SQLException e) {
            System.err.println("✗ Error deleting records: " + e.getMessage() + "\n");
        }
    }
    
    /**
     * Close the database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("✗ Error closing connection: " + e.getMessage());
        }
    }
    
    /**
     * Interactive menu for CRUD operations
     */
    public static void interactiveMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            System.out.println("\n╔════════════════════════════════╗");
            System.out.println("║   STUDENT MANAGEMENT SYSTEM    ║");
            System.out.println("╠════════════════════════════════╣");
            System.out.println("║ 1. Insert a new student        ║");
            System.out.println("║ 2. Display all students        ║");
            System.out.println("║ 3. Search student by ID        ║");
            System.out.println("║ 4. Delete all records          ║");
            System.out.println("║ 5. Exit                        ║");
            System.out.println("╚════════════════════════════════╝");
            System.out.print("Enter your choice (1-5): ");
            
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("✗ Invalid input! Please enter a number.\n");
                continue;
            }
            
            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter Student Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Course Name: ");
                    String course = scanner.nextLine();
                    insertStudent(id, name, course);
                    break;
                    
                case 2:
                    displayAllStudents();
                    break;
                    
                case 3:
                    System.out.print("Enter Student ID to search: ");
                    int searchId = Integer.parseInt(scanner.nextLine());
                    displayStudentById(searchId);
                    break;
                    
                case 4:
                    System.out.print("Are you sure? This will delete all records (yes/no): ");
                    String confirmation = scanner.nextLine().toLowerCase();
                    if (confirmation.equals("yes")) {
                        deleteAllRecords();
                    } else {
                        System.out.println("✓ Operation cancelled\n");
                    }
                    break;
                    
                case 5:
                    running = false;
                    System.out.println("✓ Exiting...\n");
                    break;
                    
                default:
                    System.out.println("✗ Invalid choice! Please enter a number between 1 and 5.\n");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Demo mode - Insert sample data and display records
     */
    public static void demoMode() {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("           RUNNING DEMO MODE - AUTO EXECUTION");
        System.out.println("═══════════════════════════════════════════════════════\n");
        
        // Delete existing records for fresh start
        deleteAllRecords();
        
        // Insert sample student records
        System.out.println("--- INSERTING STUDENT RECORDS ---\n");
        insertStudent(1, "Rahul Singh", "Java");
        insertStudent(2, "Priya Sharma", "Python");
        insertStudent(3, "Amit Patel", "Web Development");
        insertStudent(4, "Neha Verma", "Database");
        
        // Display all records
        System.out.println("--- DISPLAYING ALL STUDENT RECORDS ---\n");
        displayAllStudents();
        
        // Display specific student
        System.out.println("--- SEARCHING FOR SPECIFIC STUDENT ---\n");
        displayStudentById(2);
    }
    
    /**
     * Main method
     */
    public static void main(String[] args) {
        System.out.println("╔═════════════════════════════════════════════════════╗");
        System.out.println("║  Q3. JDBC - Insert and Display Student Records     ║");
        System.out.println("║      Using PreparedStatement                       ║");
        System.out.println("╚═════════════════════════════════════════════════════╝\n");
        
        // Connect to database
        connectToDatabase();
        
        // Create table if not exists
        createTableIfNotExists();
        
        // Check if running in demo mode or interactive mode
        if (args.length > 0 && args[0].equalsIgnoreCase("demo")) {
            // Run demo mode
            demoMode();
        } else {
            // Run interactive menu
            interactiveMenu();
        }
        
        // Close database connection
        closeConnection();
    }
}
