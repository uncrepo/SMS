package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.project.sms.Models.Teacher;
import org.project.sms.dao.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminEditTeacherController implements Initializable {
    public TableView<Teacher> teacherTableView;
    public TableColumn<Teacher, String> colTeacherId;
    public TableColumn<Teacher, String> colFullName;
    public TableColumn<Teacher, String> colPhone;
    public TableColumn<Teacher, String> colGuardian;
    public TableColumn<Teacher, String> colEmail;

    public ComboBox<String> sortByComboBox;
    public ComboBox<String> filterComboBox;
    public TextField searchTeacherField;
    public Button searchBtn;


    public Button toggleEnableBtn;
    public Button saveChangesBtn;
    public Button editBtn;
    public Button deleteTeacherBtn;
    public Button cancelBtn;
    public Button resetBtn;

    public TextField selectedTeacherNameField;
    public TextField selectedTeacherIdField;
    public TextField fullNameField;
    public TextField emailField;
    public TextField guardianField;
    public TextField phoneField;

    public void initialize(URL location, ResourceBundle resources) {
        initTableCols();
        initSelectedTeacher();
        BtnClicks();

        // Load the teacher data
        loadTeacherData();

        // Filter action on search
    }



    private void loadTeacherData() {
        teacherTableView.setItems(FXCollections.observableArrayList(TeacherDAO.getAllTeachersForAssign()));

    }


    private void initTableCols() {
        colTeacherId.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colGuardian.setCellValueFactory(new PropertyValueFactory<>("guardian"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    private void initSelectedTeacher(){
        teacherTableView.getSelectionModel().selectedItemProperty().addListener((obs,oldValue,newValue) -> {
            if (newValue != null) {
                selectedTeacherNameField.setText(newValue.getFullName());
                selectedTeacherIdField.setText(newValue.getTeacherId());
                fullNameField.setText(newValue.getFullName());
                emailField.setText(newValue.getEmail());
                guardianField.setText(newValue.getGuardian());
                phoneField.setText(newValue.getPhone());
            }
        });


    }



    private void BtnClicks() {
        searchBtn.setOnAction(e -> filterTeachers());
        deleteTeacherBtn.setOnAction(e -> removeTeacher());
        saveChangesBtn.setOnAction(e -> updateTeacherInfo());
        resetBtn.setOnAction(e -> clearSearchFields());
    }

    private void updateTeacherInfo() {
        String assignedTeacherId = selectedTeacherIdField.getText();

        String phone = phoneField.getText();
        String guardian = guardianField.getText();
        String email = emailField.getText();

        TeacherDAO.updateTeacher(assignedTeacherId,email,phone, guardian);
        loadTeacherData();
        clearSelectedFields();
    }

    private void removeTeacher() {
        String assignId = selectedTeacherIdField.getText();
            TeacherDAO.deleteTeacher(assignId);
            loadTeacherData();
            clearSelectedFields();
            // show error message here
    }


    // Filter action on search
    private void filterTeachers() {
        String searchText = searchTeacherField.getText();

        // Perform filtering based on the input search text, department, and section
        teacherTableView.setItems(TeacherDAO.searchTeachersByName(searchText));
    }


    private void clearSelectedFields() {

        fullNameField.clear();
        emailField.clear();
        guardianField.clear();
        phoneField.clear();
        selectedTeacherIdField.clear();
        selectedTeacherNameField.clear();

    }

    private void clearSearchFields() {
        searchTeacherField.clear();
    }

}
