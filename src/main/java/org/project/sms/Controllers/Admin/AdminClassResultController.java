package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.project.sms.Models.Course;
import org.project.sms.Models.Student;
import org.project.sms.dao.CalendarDAO;
import org.project.sms.dao.CourseDAO;
import org.project.sms.dao.GradeDAO;
import org.project.sms.dao.StudentDAO;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminClassResultController implements Initializable {
    public TableView<Student> studentsTableView;
    public TableColumn<Student, String> colStudentId;
    public TableColumn<Student, String> colFullName;
    public TableColumn<Student, String> colAverage;
    public TableColumn<Student, String> colPromotion;

    public TableView<Course> coursesTableView;
    public TableColumn<Course, String> colCourseTitle;
    public TableColumn<Course, String> colAssignedTeacher;

    public ComboBox<String> gradeComboBox;
    public ComboBox<String> academicYearComboBox;
    public ComboBox<String> sectionComboBox;

    public Button showClassBtn;
    public Button resetBtn;

    public TextField yearField;
    public TextField gradeField;
    public TextField sectionField;


    public void initialize(URL location, ResourceBundle resources) {
        initTableCols();
        initOptions();
        initSelectedClass();

        // Filter action on search
        showClassBtn.setOnAction(e -> loadClassResultsByFilter());
        resetBtn.setOnAction(e -> clearFields());
    }

    private void initSelectedClass(){
        showClassBtn.setOnAction(event -> {
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

    private void initTableCols() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colPromotion.setCellValueFactory(new PropertyValueFactory<>("promotion"));
        colAverage.setCellValueFactory(new PropertyValueFactory<>("average"));

        colCourseTitle.setCellValueFactory(new PropertyValueFactory<>("courseTitle"));
        colAssignedTeacher.setCellValueFactory(new PropertyValueFactory<>("assignedTeacher"));
    }


    private void initOptions() {
        ObservableList<String> grades = FXCollections.observableArrayList(GradeDAO.getAllGrades());
        gradeComboBox.setItems(grades);

        sectionComboBox.setItems(FXCollections.observableArrayList("A","B","C","D"));

        ObservableList<String> calendars = FXCollections.observableArrayList(CalendarDAO.getAllCalendar());
        academicYearComboBox.setItems(calendars);
    }


    private void loadClassResultsByFilter() {
        String grade = gradeComboBox.getValue();
        String year = academicYearComboBox.getValue();
        String section = sectionComboBox.getValue();

//        if (grade == null || year == null || section == null) {
//            showAlert("Please select department, year, and section.");
//            return;
//        }

//        List<Student> students = StudentDAO.getStudentsClassResultsByGradeYearSection(grade, year, section);
//        studentsTableView.setItems(FXCollections.observableArrayList(students));

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
