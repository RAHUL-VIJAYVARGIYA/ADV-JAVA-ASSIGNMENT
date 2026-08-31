import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class studentregistrationform extends JFrame {
    private JLabel lblName, lblEmail, lblPhone, lblGender, lblBranch, lblSemester, lblAddress;
    private JTextField txtName, txtEmail, txtPhone, txtAddress;
    private JRadioButton rbMale, rbFemale, rbOther;
    private ButtonGroup genderGroup;
    private JComboBox<String> cbBranch, cbSemester;
    private JButton btnSubmit, btnReset;
    private JTextArea displayArea;
    private JScrollPane scrollPane;

    public studentregistrationform() {
        // Frame Setup
        setTitle("Student Registration Form");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel Setup
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 248, 255));

        // Title Label
        JLabel title = new JLabel("Student Registration Form");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(150, 15, 300, 30);
        panel.add(title);

        // Name Label and TextField
        lblName = new JLabel("Name:");
        lblName.setFont(new Font("Arial", Font.PLAIN, 12));
        lblName.setBounds(50, 60, 100, 25);
        panel.add(lblName);

        txtName = new JTextField();
        txtName.setBounds(150, 60, 200, 25);
        txtName.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(txtName);

        // Email Label and TextField
        lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 12));
        lblEmail.setBounds(50, 100, 100, 25);
        panel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(150, 100, 200, 25);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(txtEmail);

        // Phone Label and TextField
        lblPhone = new JLabel("Phone:");
        lblPhone.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPhone.setBounds(50, 140, 100, 25);
        panel.add(lblPhone);

        txtPhone = new JTextField();
        txtPhone.setBounds(150, 140, 200, 25);
        txtPhone.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(txtPhone);

        // Gender Label and Radio Buttons
        lblGender = new JLabel("Gender:");
        lblGender.setFont(new Font("Arial", Font.PLAIN, 12));
        lblGender.setBounds(50, 180, 100, 25);
        panel.add(lblGender);

        genderGroup = new ButtonGroup();

        rbMale = new JRadioButton("Male");
        rbMale.setBounds(150, 180, 60, 25);
        rbMale.setFont(new Font("Arial", Font.PLAIN, 12));
        rbMale.setBackground(new Color(240, 248, 255));
        genderGroup.add(rbMale);
        panel.add(rbMale);

        rbFemale = new JRadioButton("Female");
        rbFemale.setBounds(220, 180, 70, 25);
        rbFemale.setFont(new Font("Arial", Font.PLAIN, 12));
        rbFemale.setBackground(new Color(240, 248, 255));
        genderGroup.add(rbFemale);
        panel.add(rbFemale);

        rbOther = new JRadioButton("Other");
        rbOther.setBounds(300, 180, 60, 25);
        rbOther.setFont(new Font("Arial", Font.PLAIN, 12));
        rbOther.setBackground(new Color(240, 248, 255));
        genderGroup.add(rbOther);
        panel.add(rbOther);

        // Branch Label and ComboBox
        lblBranch = new JLabel("Branch:");
        lblBranch.setFont(new Font("Arial", Font.PLAIN, 12));
        lblBranch.setBounds(50, 220, 100, 25);
        panel.add(lblBranch);

        String[] branches = {"Select Branch", "Computer Science", "Electronics", "Mechanical", "Civil", "Electrical"};
        cbBranch = new JComboBox<>(branches);
        cbBranch.setBounds(150, 220, 200, 25);
        cbBranch.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(cbBranch);

        // Semester Label and ComboBox
        lblSemester = new JLabel("Semester:");
        lblSemester.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSemester.setBounds(50, 260, 100, 25);
        panel.add(lblSemester);

        String[] semesters = {"Select Semester", "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th"};
        cbSemester = new JComboBox<>(semesters);
        cbSemester.setBounds(150, 260, 200, 25);
        cbSemester.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(cbSemester);

        // Address Label and TextField
        lblAddress = new JLabel("Address:");
        lblAddress.setFont(new Font("Arial", Font.PLAIN, 12));
        lblAddress.setBounds(50, 300, 100, 25);
        panel.add(lblAddress);

        txtAddress = new JTextField();
        txtAddress.setBounds(150, 300, 200, 50);
        txtAddress.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(txtAddress);

        // Submit Button
        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(150, 370, 90, 30);
        btnSubmit.setFont(new Font("Arial", Font.BOLD, 12));
        btnSubmit.setBackground(new Color(34, 139, 34));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitForm();
            }
        });
        panel.add(btnSubmit);

        // Reset Button
        btnReset = new JButton("Reset");
        btnReset.setBounds(260, 370, 90, 30);
        btnReset.setFont(new Font("Arial", Font.BOLD, 12));
        btnReset.setBackground(new Color(220, 20, 60));
        btnReset.setForeground(Color.WHITE);
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });
        panel.add(btnReset);

        // Display Area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Arial", Font.PLAIN, 11));
        displayArea.setBackground(new Color(255, 255, 224));
        
        scrollPane = new JScrollPane(displayArea);
        scrollPane.setBounds(50, 420, 500, 200);
        panel.add(scrollPane);

        // Add panel to frame
        add(panel);
        setVisible(true);
    }

    private void submitForm() {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String gender = "";
        String branch = (String) cbBranch.getSelectedItem();
        String semester = (String) cbSemester.getSelectedItem();
        String address = txtAddress.getText().trim();

        // Determine selected gender
        if (rbMale.isSelected()) {
            gender = "Male";
        } else if (rbFemale.isSelected()) {
            gender = "Female";
        } else if (rbOther.isSelected()) {
            gender = "Other";
        }

        // Validation
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || gender.isEmpty() || 
            branch.equals("Select Branch") || semester.equals("Select Semester") || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Phone number validation
        if (!phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid 10-digit phone number!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Email validation
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Display submitted information
        String info = "========== SUBMITTED INFORMATION ==========\n\n";
        info += "Name: " + name + "\n";
        info += "Email: " + email + "\n";
        info += "Phone: " + phone + "\n";
        info += "Gender: " + gender + "\n";
        info += "Branch: " + branch + "\n";
        info += "Semester: " + semester + "\n";
        info += "Address: " + address + "\n";
        info += "\n==========================================\n";

        displayArea.setText(info);
        JOptionPane.showMessageDialog(this, "Form Submitted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetForm() {
        txtName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        genderGroup.clearSelection();
        cbBranch.setSelectedIndex(0);
        cbSemester.setSelectedIndex(0);
        displayArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new studentregistrationform();
            }
        });
    }
}
