package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.project.sms.Models.Student;
import org.project.sms.Models.Teacher;
import org.project.sms.dao.*;

import java.net.URL;
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
    public TableColumn<Student, String> colAverage;
    public TableColumn<Student, String> colPromotion;

    public ComboBox<String> gradeComboBox;
    public ComboBox<String> academicYearComboBox;
    public ComboBox<String> sectionComboBox;

    public ComboBox<String> sortByComboBox;
    public ComboBox<String> filterComboBox;
    public Button previousBtn;
    public Button nextBtn;


    public void initialize(URL location, ResourceBundle resources) {
        initTableCols();
        initOptions();

        // Load the teacher data
        loadStudentData();

        // Filter action on search
        searchBtn.setOnAction(e -> filterStudents());
        resetBtn.setOnAction(e -> clearFields());
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
        colAverage.setCellValueFactory(new PropertyValueFactory<>("average"));
        colPromotion.setCellValueFactory(new PropertyValueFactory<>("promotion"));

    }


    private void initOptions() {
        ObservableList<String> grades = FXCollections.observableArrayList(GradeDAO.getAllGrades());
        gradeComboBox.setItems(grades);

        sectionComboBox.setItems(FXCollections.observableArrayList("A","B","C","D"));


        ObservableList<String> calendars = FXCollections.observableArrayList(CalendarDAO.getAllCalendar());
        academicYearComboBox.setItems(calendars);

//        sortByComboBox.setItems();
//        filterComboBox.setItems();


    }




//    private void onEditTeacherClick() {
//        // Fetch the selected teacher from the table view
//        Teacher selectedTeacher = teacherTableView.getSelectionModel().getSelectedItem();
//        if (selectedTeacher != null) {
//            // Open a dialog or a new window where the admin can update department/section of this teacher
//            openEditTeacherDialog(selectedTeacher);
//        } else {
//            // Show an error message if no teacher is selected
//            showAlert("Error", "No teacher selected!", Alert.AlertType.ERROR);
//        }
//    }
//
//    private void openEditTeacherDialog(Teacher teacher) {
//        // Code to open the edit teacher dialog and pass the selected teacher details for editing
//        // After edit, save the changes and reload the table
//        TextInputDialog dialog = new TextInputDialog(teacher.getDepartment());
//        dialog.setTitle("Edit Teacher Department");
//        dialog.setHeaderText("Edit Department for " + teacher.getFullName());
//        dialog.setContentText("New Department:");
//
//        dialog.showAndWait().ifPresent(newDepartment -> {
//            // Assuming section is handled similarly
//            teacher.setDepartment(newDepartment);
//            teacherDAO.updateTeacher(teacher);
//            loadTeacherData();  // Reload the teacher data in the table
//        });
//    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
