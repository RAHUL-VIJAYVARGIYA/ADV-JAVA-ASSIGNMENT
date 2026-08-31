import java.sql.*;
import java.util.Scanner;

public class Q3_JDBC_StudentManagement {
    
    // Database Connection Details
    static final String DB_URL = "jdbc:mysql://localhost:3306/college";
    static final String DB_USER = "college_admin";
    static final String DB_PASSWORD = "college_admin@2024";
    
    static Connection conn = null;
    static Scanner sc = new Scanner(System.in);
    
    // Connect to Database
    public static void connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to MySQL database 'college'");
        } catch(Exception e) {
            System.out.println("Connection Error: " + e.getMessage());
        }
    }
    
    // Create Student Table
    public static void createTable() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS student (id INT PRIMARY KEY, name VARCHAR(100), course VARCHAR(100))";
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
            System.out.println("Table 'student' created/verified");
        } catch(SQLException e) {
            System.out.println("Table Error: " + e.getMessage());
        }
    }
    
    // Insert Student Record using PreparedStatement
    public static void insertStudent() {
        try {
            System.out.print("Enter Student ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            
            System.out.print("Enter Student Name: ");
            String name = sc.nextLine();
            
            System.out.print("Enter Course Name: ");
            String course = sc.nextLine();
            
            String sql = "INSERT INTO student (id, name, course) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.setString(2, name);
            pst.setString(3, course);
            
            int rowsInserted = pst.executeUpdate();
            if(rowsInserted > 0) {
                System.out.println("Student record inserted successfully");
            }
            pst.close();
        } catch(SQLException e) {
            System.out.println("Insert Error: " + e.getMessage());
        }
    }
    
    // Display All Students using PreparedStatement
    public static void displayAllStudents() {
        try {
            String sql = "SELECT id, name, course FROM student";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            System.out.println("\nStudent Records:");
            System.out.println("ID\tName\t\t\tCourse");
            System.out.println("----------------------------------------------");
            
            while(rs.next()) {
                System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t\t" + rs.getString(3));
            }
            pst.close();
        } catch(SQLException e) {
            System.out.println("Display Error: " + e.getMessage());
        }
    }
    
    // Search Student by ID
    public static void searchStudent() {
        try {
            System.out.print("Enter Student ID to search: ");
            int id = sc.nextInt();
            
            String sql = "SELECT id, name, course FROM student WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()) {
                System.out.println("ID: " + rs.getInt(1));
                System.out.println("Name: " + rs.getString(2));
                System.out.println("Course: " + rs.getString(3));
            } else {
                System.out.println("Student not found");
            }
            pst.close();
        } catch(SQLException e) {
            System.out.println("Search Error: " + e.getMessage());
        }
    }
    
    // Main Menu
    public static void main(String[] args) {
        connectDB();
        createTable();
        
        int choice;
        while(true) {
            System.out.println("\n--- Student Database Menu ---");
            System.out.println("1. Insert Student Record");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            
            choice = sc.nextInt();
            
            switch(choice) {
                case 1:
                    insertStudent();
                    break;
                case 2:
                    displayAllStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    try {
                        conn.close();
                    } catch(SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
