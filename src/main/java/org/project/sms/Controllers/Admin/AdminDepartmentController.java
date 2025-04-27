package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.project.sms.Models.Course;
import org.project.sms.Models.Student;
import org.project.sms.dao.CourseDAO;
import org.project.sms.dao.StudentDAO;
import org.project.sms.options.DepartmentOptions;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminDepartmentController implements Initializable {
    public ComboBox<DepartmentOptions> departmentComboBox;
    public ComboBox<String> yearComboBox;
    public ComboBox<String> sectionComboBox;
    public Button showStudentsBtn;
    public TableView<Student> studentTableView;
    public TableColumn<Student, String> colStudentId;
    public TableColumn<Student, String> colFullName;
    public TableColumn<Student, String> colDepartment;
    public TableColumn<Student, String> colYear;
    public TableColumn<Student, String> colSection;
    private final StudentDAO studentDAO = new StudentDAO();

    private final CourseDAO courseDAO = new CourseDAO();
    public TableView<Course> coursesTableView;
    public TableColumn<Course, String> colCourseCode;
    public TableColumn<Course, String> colCourseTitle;
    public TableColumn<Course, String> colCreditHour;
    public TableColumn<Course, String> colAssignedTeacher;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initComboBoxes();
        initTableColumns();

        showStudentsBtn.setOnAction(e -> loadStudentsByFilter());
    }

    // create under options
    private void initComboBoxes() {
        departmentComboBox.setItems(FXCollections.observableArrayList(DepartmentOptions.values()));
        yearComboBox.setItems(FXCollections.observableArrayList("1", "2", "3", "4","5"));
        sectionComboBox.setItems(FXCollections.observableArrayList("A", "B", "C"));
    }

    private void initTableColumns() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("section"));

        colCourseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        colCourseTitle.setCellValueFactory(new PropertyValueFactory<>("courseTitle"));
        colCreditHour.setCellValueFactory(new PropertyValueFactory<>("creditHour"));
        colAssignedTeacher.setCellValueFactory(new PropertyValueFactory<>("assignedTeacher"));
    }

    private void loadStudentsByFilter() {
        String department = departmentComboBox.getValue().toString();
        String year = yearComboBox.getValue();
        String section = sectionComboBox.getValue();

        if (department == null || year == null || section == null) {
            showAlert("Please select department, year, and section.");
            return;
        }

        List<Student> students = studentDAO.getStudentsByDepartmentYearSection(department, year, section);
        studentTableView.setItems(FXCollections.observableArrayList(students));

        List<Course> courses = courseDAO.getCoursesByDepartmentYearSection(department, year, section);
        coursesTableView.setItems(FXCollections.observableArrayList(courses));
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Required");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
