package org.project.sms.Controllers.Admin;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.project.sms.Models.Model;
import org.project.sms.Models.Teacher;
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
        public ComboBox departmentComboBox;
        public Button addTeacherBtn;
        public TextField teacherSearchField;
        public Button searchBtn;

        public ComboBox<String> teacherDepartmentComboBox;
        public ComboBox<String> teacherSectionComboBox;
        public TableView<Teacher> teacherTableView;
        public TableColumn<Teacher, String> colTeacherId;
        public TableColumn<Teacher, String> colFullName;
        public TableColumn<Teacher, String> colEmail;
        public TableColumn<Teacher, String> colDepartment;
        public TableColumn<Teacher, String> colYear;
        public TableColumn<Teacher, String> colSection;
        public TableColumn<Teacher, String> colCourseCode;

        public ComboBox selectTeacherComboBox;
        public ComboBox selectCourseComboBox;
        public ComboBox assignDepartmentComboBox;
        public ComboBox assignYearComboBox;
        public ComboBox assignSectionComboBox;
        public Button assignTeacherBtn;


    private TeacherDAO teacherDAO = new TeacherDAO();


    public AdminTeacherController() {
}

    public void initialize(URL location, ResourceBundle resources) {
        colTeacherId.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("section"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colCourseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));

        assignDepartmentComboBox.setItems(FXCollections.observableArrayList(DepartmentOptions.values()));
        assignSectionComboBox.setItems(FXCollections.observableArrayList(SectionOptions.values()));

        // Load the teacher data
        loadTeacherData();

        // Filter action on search
        searchBtn.setOnAction(e -> filterTeachers());
    }

    private void loadTeacherData() {
        teacherTableView.setItems(FXCollections.observableArrayList(teacherDAO.getAllTeachers()));
    }

    private void filterTeachers() {
        String searchText = teacherSearchField.getText();
        String department = teacherDepartmentComboBox.getValue();
        String section = teacherSectionComboBox.getValue();

        // Perform filtering based on the input search text, department, and section
        teacherTableView.setItems(teacherDAO.searchTeachers(searchText, department, section));
    }

    private void onEditTeacherClick() {
        // Fetch the selected teacher from the table view
        Teacher selectedTeacher = teacherTableView.getSelectionModel().getSelectedItem();
        if (selectedTeacher != null) {
            // Open a dialog or a new window where the admin can update department/section of this teacher
            openEditTeacherDialog(selectedTeacher);
        } else {
            // Show an error message if no teacher is selected
            showAlert("Error", "No teacher selected!", Alert.AlertType.ERROR);
        }
    }

    private void openEditTeacherDialog(Teacher teacher) {
        // Code to open the edit teacher dialog and pass the selected teacher details for editing
        // After edit, save the changes and reload the table
        TextInputDialog dialog = new TextInputDialog(teacher.getDepartment());
        dialog.setTitle("Edit Teacher Department");
        dialog.setHeaderText("Edit Department for " + teacher.getFullName());
        dialog.setContentText("New Department:");

        dialog.showAndWait().ifPresent(newDepartment -> {
            // Assuming section is handled similarly
            teacher.setDepartment(newDepartment);
            teacherDAO.updateTeacher(teacher);
            loadTeacherData();  // Reload the teacher data in the table
        });
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
