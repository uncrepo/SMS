package org.project.sms.Controllers.Admin;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.project.sms.Models.Student;
import org.project.sms.Models.Teacher;
import org.project.sms.dao.*;
import org.project.sms.options.SortOptions;
import org.project.sms.options.TeacherMenuOptions;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminStudentsController implements Initializable {
    public TextField searchStudentField;
    public Button searchBtn;
    public Button resetBtn;

    public TableView<Student> studentsTableView;
    public TableColumn<Student, String> colStudentId;
    public TableColumn<Student, String> colFullName;
    public TableColumn<Student, String> colEmail;
    public TableColumn<Student, String> colGrade;
    public TableColumn<Student, String> colSection;
    public TableColumn<Student, String> colAcademicYear;
    public TableColumn<Student, String> colGuardian;
    public TableColumn<Student, String> colPhone;

    public ComboBox<String> gradeComboBox;
    public ComboBox<String> academicYearComboBox;
    public ComboBox<String> sectionComboBox;

    public ComboBox<String> sortByComboBox;
    public ComboBox<String> filterComboBox;
    public Button previousBtn;
    public Button nextBtn;

    private int currentPage = 0;
    private final int ROWS_PER_PAGE = 11;



    public void initialize(URL location, ResourceBundle resources) {
        initTableCols();
        initOptions();

        setupPaginationButtons();
        setupComboBoxListeners();
        loadFilteredPage();

        // Load the teacher data
//        loadStudentData();

        // Filter action on search
        searchBtn.setOnAction(e -> filterStudents());
        resetBtn.setOnAction(e -> clearFields());
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

        List<Student> results = FilterDAO.getAllStudentDetailsByFilter(
                academicYear, grade, section, ROWS_PER_PAGE, offset
        );

        studentsTableView.setItems(FXCollections.observableArrayList(results));

        // Optional: Disable buttons if on bounds
        previousBtn.setDisable(currentPage == 0);
        nextBtn.setDisable(results.size() < ROWS_PER_PAGE);
    }

    private void clearFields() {
        gradeComboBox.getSelectionModel().clearSelection();
        academicYearComboBox.getSelectionModel().clearSelection();
        sectionComboBox.getSelectionModel().clearSelection();
    }

    private void loadStudentData() {
        studentsTableView.setItems(FXCollections.observableArrayList(StudentDAO.getAllStudentsDefault()));
    }

    private void filterStudents() {
        String searchText = searchStudentField.getText();
        String academicYear = academicYearComboBox.getValue();
        String grade = gradeComboBox.getValue();
        String section = sectionComboBox.getValue();

        // Perform filtering based on the input search text, department, and section
//        studentsTableView.setItems(StudentDAO.searchStudentsByNames(searchText, grade, section, academicYear));
    }

    private void initTableCols() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("section"));
        colAcademicYear.setCellValueFactory(new PropertyValueFactory<>("academicYear"));
        colGuardian.setCellValueFactory(new PropertyValueFactory<>("guardian"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

    }


    private void initOptions() {
        ObservableList<String> grades = FXCollections.observableArrayList(GradeDAO.getAllGrades());
        gradeComboBox.setItems(grades);

        sectionComboBox.setItems(FXCollections.observableArrayList("A","B","C","D"));


        ObservableList<String> calendars = FXCollections.observableArrayList(CalendarDAO.getAllCalendar());
        academicYearComboBox.setItems(calendars);

        sortByComboBox.setItems(FXCollections.observableArrayList(SortOptions.AssignedStudentDetailsFilter));
        filterComboBox.setItems(FXCollections.observableArrayList(SortOptions.CommonFilers));



    }


    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
