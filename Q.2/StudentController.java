import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentController {
    private StudentModel model;
    private StudentView view;

    public StudentController(StudentModel model, StudentView view) {
        this.model = model;
        this.view = view;

        view.getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });
    }

    private void handleSubmit() {
        String studentId = view.getStudentId();
        String studentName = view.getStudentName();
        String studentCourse = view.getStudentCourse();

        if (studentId.isEmpty() || studentName.isEmpty() || studentCourse.isEmpty()) {
            view.showError("Please fill all fields!");
            return;
        }

        try {
            int id = Integer.parseInt(studentId);
            model.setStudentId(id);
            model.setName(studentName);
            model.setCourse(studentCourse);

            view.setDisplayText(model.toString());
            view.showSuccess("Student details submitted successfully!");
            view.clearFields();
        } catch (NumberFormatException e) {
            view.showError("Student ID must be a number!");
        }
    }
}
