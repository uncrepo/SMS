package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.project.sms.Models.Course;
import org.project.sms.Models.Student;
import org.project.sms.dao.CourseDAO;
import org.project.sms.dao.StudentDAO;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminClassResultController implements Initializable {
    public TableView studentTableView;
    public TableColumn<Student, String> colStudentId;
    public TableColumn<Student, String> colFullName;
    public TableColumn<Student, String> colAcademicYear;
    public TableColumn<Student, String> colGrade;
    public TableColumn<Student, String> colSection;
    public TableColumn<Student, String> colAverage;
    public TableColumn<Student, String> colPromotion;

    public TableView coursesTableView;
    public TableColumn<Course, String> colCourseTitle;
    public TableColumn<Course, String> colAssignedTeacher;

    public ComboBox<String> gradeComboBox;
    public ComboBox<String> academicYearComboBox;
    public ComboBox<String> sectionComboBox;

    public Button showStudentsBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    // create under options
    private void initComboBoxes() {
        gradeComboBox.setItems(FXCollections.observableArrayList("1","2"));
        academicYearComboBox.setItems(FXCollections.observableArrayList("2024/25","2023/24","2022/23"));
        sectionComboBox.setItems(FXCollections.observableArrayList("A", "B", "C","D","E"));
    }

    private void initTableColumns() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colAcademicYear.setCellValueFactory(new PropertyValueFactory<>("academicYear"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("section"));
        colPromotion.setCellValueFactory(new PropertyValueFactory<>("promotion"));
        colAverage.setCellValueFactory(new PropertyValueFactory<>("average"));

        colCourseTitle.setCellValueFactory(new PropertyValueFactory<>("courseTitle"));
        colAssignedTeacher.setCellValueFactory(new PropertyValueFactory<>("assignedTeacher"));
    }

    private void loadStudentsByFilter() {
        String grade = gradeComboBox.getValue().toString();
        String year = academicYearComboBox.getValue();
        String section = sectionComboBox.getValue();

        if (grade == null || year == null || section == null) {
            showAlert("Please select department, year, and section.");
            return;
        }

        List<Student> students = StudentDAO.getStudentsClassResultsByGradeYearSection(grade, year, section);
        studentTableView.setItems(FXCollections.observableArrayList(students));

        List<Course> courses = CourseDAO.getCoursesByGradeYearSection(grade, year, section);
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
