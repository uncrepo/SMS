package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.project.sms.Models.Course;
import org.project.sms.Models.Grade;
import org.project.sms.Models.Student;
import org.project.sms.dao.CourseDAO;
import org.project.sms.dao.GradeDAO;
import org.project.sms.dao.StudentDAO;
import org.project.sms.options.DepartmentOptions;
import org.project.sms.options.GradeOptions;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminClassesController implements Initializable {
    public ComboBox gradeComboBox;
    public ComboBox<String> yearComboBox;
    public ComboBox<String> sectionComboBox;
    public Button showStudentsBtn;

    public TableView<Student> studentTableView;
    public TableColumn<Student, String> colStudentId;
    public TableColumn<Student, String> colFullName;
    public TableColumn<Student, String> colEmail;
    public TableColumn<Student, String> colGuardian;
    public TableColumn<Student, String> colPhone;
    private final StudentDAO studentDAO = new StudentDAO();

    public TableView<Course> coursesTableView;
    public TableColumn<Course, String> colCourseTitle;
    public TableColumn<Course, String> colAssignedTeacher;
    private final CourseDAO courseDAO = new CourseDAO();

    private List<Grade> grades = GradeDAO.getAllGrades();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initComboBoxes();
        initTableColumns();

        showStudentsBtn.setOnAction(e -> loadStudentsByFilter());
    }

    // create under options
    private void initComboBoxes() {
        gradeComboBox.setItems(FXCollections.observableArrayList("1","2"));
        yearComboBox.setItems(FXCollections.observableArrayList("2024/25","2023/24","2022/23"));
        sectionComboBox.setItems(FXCollections.observableArrayList("A", "B", "C","D","E"));
    }

    private void initTableColumns() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colGuardian.setCellValueFactory(new PropertyValueFactory<>("guardian"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        colCourseTitle.setCellValueFactory(new PropertyValueFactory<>("courseTitle"));
        colAssignedTeacher.setCellValueFactory(new PropertyValueFactory<>("assignedTeacher"));
    }

    private void loadStudentsByFilter() {
        String grade = gradeComboBox.getValue().toString();
        String year = yearComboBox.getValue();
        String section = sectionComboBox.getValue();

        if (grade == null || year == null || section == null) {
            showAlert("Please select department, year, and section.");
            return;
        }

        List<Student> students = studentDAO.getStudentsByGradeYearSection(grade, year, section);
        studentTableView.setItems(FXCollections.observableArrayList(students));

        List<Course> courses = courseDAO.getCoursesByGradeYearSection(grade, year, section);
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
