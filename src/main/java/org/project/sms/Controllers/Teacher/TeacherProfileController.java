package org.project.sms.Controllers.Teacher;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.project.sms.Models.Model;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TeacherProfileController implements Initializable {

    public ComboBox<String> academicYearComboBox;
    public ComboBox<String> semesterComboBox;
    public TextField fullNameField;


    public RadioButton maleRadio;

    public RadioButton femaleRadio;

    public ToggleGroup genderGroup;


    public TextField emailField;

    public TextField guardianNumberField;

    public TextField phoneNumberField;

    public TextField usernameField;



    @FXML
    public void initialize() {
        initTeacher();

    }

    private void copyToClipboard(String text) {
        if (text != null && !text.isEmpty()) {
            final var clipboard = javafx.scene.input.Clipboard.getSystemClipboard();
            final var content = new javafx.scene.input.ClipboardContent();
            content.putString(text);
            clipboard.setContent(content);
            showAlert(Alert.AlertType.INFORMATION, "Copied to Clipboard", "Text copied successfully!");
        }
    }

    private void uploadImage() {
        // Placeholder implementation for uploading profile image
        showAlert(Alert.AlertType.INFORMATION, "Upload Image", "Profile image uploaded successfully!");
    }

    private void uploadDocument() {
        // Placeholder implementation for uploading a document
        showAlert(Alert.AlertType.INFORMATION, "Upload Document", "National ID uploaded successfully!");
    }

    private void enableEditing() {
        // Enable all input fields for editing
        fullNameField.setDisable(false);
        maleRadio.setDisable(false);
        femaleRadio.setDisable(false);
        emailField.setDisable(false);
        guardianNumberField.setDisable(false);
        phoneNumberField.setDisable(false);
        usernameField.setDisable(false);
    }

    private void saveChanges() {
        // Placeholder implementation for saving changes
        showAlert(Alert.AlertType.INFORMATION, "Save Changes", "Profile changes saved successfully!");
        disableEditing();
    }

    private void cancelEditing() {
        // Reset the fields to their original state
        disableEditing();
    }

    private void disableEditing() {
        // Disable all input fields
        fullNameField.setDisable(true);
        maleRadio.setDisable(true);
        femaleRadio.setDisable(true);
        emailField.setDisable(true);
        guardianNumberField.setDisable(true);
        phoneNumberField.setDisable(true);
        usernameField.setDisable(true);
    }

    private void initTeacher(){
        fullNameField.setText(Model.getInstance().getCurrentTeacher().getFullName());
        academicYearComboBox.setValue(Model.getInstance().getCurrentAcademicYear());
        semesterComboBox.setValue(Model.getInstance().getCurrentSemester());
        emailField.setText(Model.getInstance().getCurrentTeacher().getEmail());
        phoneNumberField.setText(Model.getInstance().getCurrentTeacher().getPhone());
        guardianNumberField.setText(Model.getInstance().getCurrentTeacher().getGuardian());
        if (Model.getInstance().getCurrentTeacher().getGender() == null)
            genderGroup.selectToggle(null);
        if (Objects.equals(Model.getInstance().getCurrentTeacher().getGender(), "male")) {
            genderGroup.selectToggle(maleRadio);
        }
        else if (Objects.equals(Model.getInstance().getCurrentTeacher().getGender(), "female")) {
            genderGroup.selectToggle(femaleRadio);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTeacher();
    }
}