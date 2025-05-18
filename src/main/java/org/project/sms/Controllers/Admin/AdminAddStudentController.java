package org.project.sms.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.project.sms.Models.AddStudent;
import org.project.sms.Models.Student;
import org.project.sms.Models.Teacher;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminAddStudentController implements Initializable {
    public TextField fullNameField;
    public TextField emailField;
    public TextField guardianNumberField;
    public TextField phoneNumberField;
    public TextField teacherUsernameField;
    public TextField passwordField;

//    public Button addTeacherBtn;
    public Button resetBtn;

    public Button generatePasswordBtn;
    public Button generateUsernameBtn;
    public Button usernameCopy;
    public Button passwordCopy;
    public ToggleGroup genderGroup;
    public Button uploadImage;
    public Button uploadDocument;
    public ImageView uploadedImageView;
    public ImageView uploadedNationalIDView;

    private Image image;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BtnClicks();
    }

    private void BtnClicks() {
        uploadImage.setOnAction(e -> importImageBtn());
        uploadDocument.setOnAction(e -> importNationalIDBtn());

        resetBtn.setOnAction(e -> clearFields());
        generatePasswordBtn.setOnAction(e -> generatePassword());
        generateUsernameBtn.setOnAction(e -> generateUsername());
    }

    private void generateUsername() {
    }

    private void generatePassword() {
    }

    private void importImageBtn() {
        FileChooser open = new FileChooser();
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image", "*.jpg", "*.jpeg","*.png"));

        File file = open.showOpenDialog(uploadImage.getScene().getWindow());

        if (file != null) {
            image = new Image(file.toURI().toString(), 100,100, false, true);
            uploadedImageView.setImage(image);
        }
    }

    private void importNationalIDBtn() {
        FileChooser open = new FileChooser();
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image", "*.jpg", "*.jpeg","*.png"));

        File file = open.showOpenDialog(uploadImage.getScene().getWindow());

        if (file != null) {
            image = new Image(file.toURI().toString(), 100,100, false, true);
            uploadedNationalIDView.setImage(image);
        }
    }

    private void clearFields() {
        fullNameField.clear();
        emailField.clear();
        guardianNumberField.clear();
        phoneNumberField.clear();
        teacherUsernameField.clear();
        passwordField.clear();
    }

    public AddStudent getNewStudentInfo() {
        String fullName = fullNameField.getText();
        String email=emailField.getText();
        String guardian = guardianNumberField.getText();
        String phone = phoneNumberField.getText();
        String username = teacherUsernameField.getText();
        String password = passwordField.getText();
        RadioButton gender =  (RadioButton) genderGroup.getSelectedToggle();
        String selectedGender = gender.getText();

        return new AddStudent(fullName,email,username,password,selectedGender,phone,guardian);
    }
}
