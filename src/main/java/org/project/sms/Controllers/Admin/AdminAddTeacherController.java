package org.project.sms.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.project.sms.dao.TeacherDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminAddTeacherController implements Initializable {
    public TextField fullNameField;
    public TextField emailField;
    public TextField guardianNumberField;
    public TextField phoneNumberField;
    public TextField teacherUsernameField;
    public TextField passwordField;

    public Button addTeacherBtn;
    public Button resetBtn;

    public Button generatePasswordBtn;
    public Button generateUsernameBtn;
    public Button usernameCopy;
    public Button passwordCopy;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BtnClicks();
    }

    private void BtnClicks() {
        addTeacherBtn.setOnAction(e -> addTeacher());
        resetBtn.setOnAction(e -> clearFields());
        generatePasswordBtn.setOnAction(e -> generatePassword());
        generateUsernameBtn.setOnAction(e -> generateUsername());
    }

    private void generateUsername() {
    }

    private void generatePassword() {
    }

    private void clearFields() {
        fullNameField.clear();
        emailField.clear();
        guardianNumberField.clear();
        phoneNumberField.clear();
        teacherUsernameField.clear();
        passwordField.clear();
    }

    private void addTeacher() {
        String fullName = fullNameField.getText();
        String email=emailField.getText();
        String guardian = guardianNumberField.getText();
        String phone = phoneNumberField.getText();
        String username = teacherUsernameField.getText();
        String password = passwordField.getText();

        TeacherDAO.addTeacher(fullName,username,email,password,guardian,phone);
        clearFields();
    }
}
