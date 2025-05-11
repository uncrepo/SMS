package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.project.sms.Models.Course;
import org.project.sms.Models.Grade;
import org.project.sms.Models.Student;
import org.project.sms.dao.CalendarDAO;
import org.project.sms.dao.CourseDAO;
import org.project.sms.dao.GradeDAO;
import org.project.sms.dao.StudentDAO;
import org.project.sms.options.DepartmentOptions;
import org.project.sms.options.GradeOptions;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminClassesController implements Initializable {
    public ComboBox<String> gradeComboBox;
    public ComboBox<String> sectionComboBox;
    public ComboBox<String> academicYearComboBox;

    public TableView<Student> studentsTableView;
    public TableColumn<Student, String> colStudentId;
    public TableColumn<Student, String> colFullName;

    public TableView<Course> coursesTableView;
    public TableColumn<Course, String> colCourseTitle;
    public TableColumn<Course, String> colAssignedTeacher;

    public Button showStudentsBtn;
    public Button resetBtn;

    public TextField yearField;
    public TextField gradeField;
    public TextField sectionField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initOptions();
        initTableColumns();
        initSelectedClass();

        showStudentsBtn.setOnAction(e -> loadStudentsByFilter());
        resetBtn.setOnAction(e -> clearFields());
    }

    // init required options
    private void initOptions() {
        ObservableList<String> grades = FXCollections.observableArrayList(GradeDAO.getAllGrades());
        gradeComboBox.setItems(grades);

        sectionComboBox.setItems(FXCollections.observableArrayList("A","B","C","D"));

        ObservableList<String> calendars = FXCollections.observableArrayList(CalendarDAO.getAllCalendar());
        academicYearComboBox.setItems(calendars);
    }

    private void initTableColumns() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        colCourseTitle.setCellValueFactory(new PropertyValueFactory<>("courseTitle"));
        colAssignedTeacher.setCellValueFactory(new PropertyValueFactory<>("assignedTeacher"));
    }

    private void loadStudentsByFilter() {
        String grade = gradeComboBox.getValue();
        String year = academicYearComboBox.getValue();
        String section = sectionComboBox.getValue();


        List<Student> students = StudentDAO.getStudentsByGradeYearSection(grade, year, section);
        studentsTableView.setItems(FXCollections.observableArrayList(students));

        List<Course> courses = CourseDAO.getCoursesByGradeYearSection(grade, year, section);
        coursesTableView.setItems(FXCollections.observableArrayList(courses));

    }

    private void initSelectedClass(){
        showStudentsBtn.setOnAction(event -> {
                            sectionField.setText(sectionComboBox.getValue());
                            yearField.setText(academicYearComboBox.getValue());
                            gradeField.setText(gradeComboBox.getValue());
                            clearFields();
                        });
                    }

    private void clearFields() {
        gradeComboBox.getSelectionModel().clearSelection();
        academicYearComboBox.getSelectionModel().clearSelection();
        sectionComboBox.getSelectionModel().clearSelection();
    }



    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Required");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
