package org.project.sms.Controllers.Admin;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.project.sms.Models.Course;
import org.project.sms.Models.Result;
import org.project.sms.Models.Student;
import org.project.sms.dao.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminClassResultController implements Initializable {
    public TableView<Result> studentsTableView;
    public TableColumn<Result, String> colStudentId;
    public TableColumn<Result, String> colFullName;
    public TableColumn<Result, String> colAverage;
    public TableColumn<Result, String> colPromotion;

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
    public Button previousBtn;
    public Button nextBtn;


    private int currentPage = 0;
    private final int ROWS_PER_PAGE = 10;



    public void initialize(URL location, ResourceBundle resources) {
        initTableCols();
        initOptions();
        initSelectedClass();

        setupComboBoxListeners();
        setupPaginationButtons();

        // Filter action on search
        showClassBtn.setOnAction(e -> loadClassResultsByFilter());
        resetBtn.setOnAction(e -> clearFields());
    }

    private void initSelectedClass(){
        showClassBtn.setOnAction(event -> {
            sectionField.setText(sectionComboBox.getValue());
            yearField.setText(academicYearComboBox.getValue());
            gradeField.setText(gradeComboBox.getValue());
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
        colPromotion.setCellValueFactory(new PropertyValueFactory<>("comment"));
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

        if (grade == null && year == null && section == null) {
            showAlert("Please select grade, year, and section.");
            return;
        }

        List<Result> students = StudentDAO.getStudentsClassResultsByGradeYearSection(grade, year, section);
        if(students != null) {
            studentsTableView.setItems(FXCollections.observableArrayList(students));
        }

        List<Course> courses = CourseDAO.getCoursesByGradeYearSection("Grade 3", "2022/23", "A");
        if (courses != null) {
            coursesTableView.setItems(FXCollections.observableArrayList(courses));
        }
    }


    private void setupCommonListener() {
        ChangeListener<String> commonListener = (obs, oldVal, newVal) -> loadClassResultsByFilter();
        gradeComboBox.valueProperty().addListener(commonListener);
        sectionComboBox.valueProperty().addListener(commonListener);
        academicYearComboBox.valueProperty().addListener(commonListener);
    }

    private void setupComboBoxListeners() {
        ChangeListener<String> comboListener = (obs, oldVal, newVal) -> {
            currentPage = 0;
            loadFilteredPage();
        };

        academicYearComboBox.valueProperty().addListener(comboListener);
        gradeComboBox.valueProperty().addListener(comboListener);
        sectionComboBox.valueProperty().addListener(comboListener);
    }


    private void setupPaginationButtons() {
        previousBtn.setOnAction(event -> {
            if (currentPage > 0) {
                currentPage--;
                loadFilteredPage();
            }
        });

        nextBtn.setOnAction(event -> {
            currentPage++;
            loadFilteredPage();
        });
    }

    private void loadFilteredPage() {
        String academicYear = academicYearComboBox.getValue();
        String grade = gradeComboBox.getValue();
        String section = sectionComboBox.getValue();

        int offset = currentPage * ROWS_PER_PAGE;

        List<Result> results = FilterDAO.getStudentsResultsForAdminByFilter(
                academicYear, grade, section, ROWS_PER_PAGE, offset
        );

        studentsTableView.setItems(FXCollections.observableArrayList(results));

        List<Course> courses = CourseDAO.getCoursesByGradeYearSection("Grade 3", "2022/23", "A");
        if (courses != null) {
            coursesTableView.setItems(FXCollections.observableArrayList(courses));
        }


        // Optional: Disable buttons if on bounds
        previousBtn.setDisable(currentPage == 0);
        nextBtn.setDisable(results.size() < ROWS_PER_PAGE);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Required");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
