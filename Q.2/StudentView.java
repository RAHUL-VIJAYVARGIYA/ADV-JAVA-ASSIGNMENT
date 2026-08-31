import javax.swing.*;

public class StudentView {
    private JFrame frame;
    private JLabel lblStudentId;
    private JLabel lblName;
    private JLabel lblCourse;
    private JTextField txtStudentId;
    private JTextField txtName;
    private JTextField txtCourse;
    private JButton btnSubmit;
    private JTextArea displayArea;
    private JScrollPane scrollPane;

    public StudentView() {
        frame = new JFrame("Student Management System");
        frame.setSize(500, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new java.awt.Color(240, 248, 255));

        JLabel titleLabel = new JLabel("Student Information Form");
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        titleLabel.setBounds(120, 15, 250, 25);
        panel.add(titleLabel);

        lblStudentId = new JLabel("Student ID:");
        lblStudentId.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        lblStudentId.setBounds(50, 60, 100, 25);
        panel.add(lblStudentId);

        txtStudentId = new JTextField();
        txtStudentId.setBounds(180, 60, 200, 25);
        panel.add(txtStudentId);

        lblName = new JLabel("Student Name:");
        lblName.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        lblName.setBounds(50, 110, 100, 25);
        panel.add(lblName);

        txtName = new JTextField();
        txtName.setBounds(180, 110, 200, 25);
        panel.add(txtName);

        lblCourse = new JLabel("Course:");
        lblCourse.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        lblCourse.setBounds(50, 160, 100, 25);
        panel.add(lblCourse);

        txtCourse = new JTextField();
        txtCourse.setBounds(180, 160, 200, 25);
        panel.add(txtCourse);

        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(180, 220, 100, 35);
        btnSubmit.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        btnSubmit.setBackground(new java.awt.Color(34, 139, 34));
        btnSubmit.setForeground(java.awt.Color.WHITE);
        panel.add(btnSubmit);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        displayArea.setBackground(new java.awt.Color(255, 255, 224));

        scrollPane = new JScrollPane(displayArea);
        scrollPane.setBounds(50, 280, 380, 130);
        panel.add(scrollPane);

        frame.add(panel);
        frame.setVisible(true);
    }

    public String getStudentId() {
        return txtStudentId.getText();
    }

    public String getStudentName() {
        return txtName.getText();
    }

    public String getStudentCourse() {
        return txtCourse.getText();
    }

    public void setDisplayText(String text) {
        displayArea.setText(text);
    }

    public JButton getSubmitButton() {
        return btnSubmit;
    }

    public void clearFields() {
        txtStudentId.setText("");
        txtName.setText("");
        txtCourse.setText("");
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(frame, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}