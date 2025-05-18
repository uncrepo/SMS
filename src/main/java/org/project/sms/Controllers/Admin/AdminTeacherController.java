package org.project.sms.Controllers.Admin;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.project.sms.Models.Model;
import org.project.sms.Models.Teacher;
import org.project.sms.dao.CalendarDAO;
import org.project.sms.dao.CourseDAO;
import org.project.sms.dao.GradeDAO;
import org.project.sms.dao.TeacherDAO;
import org.project.sms.options.DepartmentOptions;
import org.project.sms.options.SectionOptions;

import java.net.URL;
import java.util.ResourceBundle;


public class AdminTeacherController implements Initializable {

//    public TableColumn colStatus;
//    public TableColumn colActions;
//        public Button editTeacherBtn;
    public TextField searchTeacherField;
    public Button searchBtn;
    public Button resetBtn;

    public TableView<Teacher> teacherTableView;
    public TableColumn<Teacher, String> colTeacherId;
    public TableColumn<Teacher, String> colFullName;
    public TableColumn<Teacher, String> colEmail;
    public TableColumn<Teacher, String> colGrade;
    public TableColumn<Teacher, String> colSection;
    public TableColumn<Teacher, String> colCourse;
    public TableColumn<Teacher, String> colAcademicYear;
    public TableColumn<Teacher, String> colGuardian;
    public TableColumn<Teacher, String> colPhone;

    public ComboBox<String> courseComboBox;
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
        loadTeacherData();

        // Filter action on search
        searchBtn.setOnAction(e -> filterTeachers());
        resetBtn.setOnAction(e -> clearFields());
    }

    private void clearFields() {
        gradeComboBox.getSelectionModel().clearSelection();
        academicYearComboBox.getSelectionModel().clearSelection();
        sectionComboBox.getSelectionModel().clearSelection();
        courseComboBox.getSelectionModel().clearSelection();
    }

    private void loadTeacherData() {
        teacherTableView.setItems(FXCollections.observableArrayList(TeacherDAO.getAllTeachersDefault()));
    }

    private void filterTeachers() {
        String searchText = searchTeacherField.getText();
        String academicYear = academicYearComboBox.getValue();
        String grade = gradeComboBox.getValue();
        String course = courseComboBox.getValue();
        String section = sectionComboBox.getValue();

         // Perform filtering based on the input search text, department, and section
        teacherTableView.setItems(TeacherDAO.searchTeachersByNameFullView(searchText, grade, section, academicYear, course));
    }

    private void initTableCols() {
        colTeacherId.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("section"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colAcademicYear.setCellValueFactory(new PropertyValueFactory<>("academicYear"));
        colGuardian.setCellValueFactory(new PropertyValueFactory<>("guardian"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }


    private void initOptions() {
        ObservableList<String> grades = FXCollections.observableArrayList(GradeDAO.getAllGrades());
        gradeComboBox.setItems(grades);

        ObservableList<String> courses = FXCollections.observableArrayList(CourseDAO.getAllCourses());
        courseComboBox.setItems(courses);
        sectionComboBox.setItems(FXCollections.observableArrayList("A","B","C","D"));


        if(gradeComboBox.getValue() != null) {
            ObservableList<String> coursesByGrade = FXCollections.observableArrayList(CourseDAO.getAllCoursesByGrade(gradeComboBox.getValue()));
            courseComboBox.setItems(coursesByGrade);
        }

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
