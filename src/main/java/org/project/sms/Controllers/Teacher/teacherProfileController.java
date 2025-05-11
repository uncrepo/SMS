package org.project.sms.Controllers.Teacher;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class teacherProfileController {

    @FXML
    private TextField fullNameField;


    @FXML
    private RadioButton maleRadio;

    @FXML
    private RadioButton femaleRadio;

    @FXML
    private ToggleGroup genderGroup;

    @FXML
    private ChoiceBox<String> gradeChoiceBox;

    @FXML
    private TextField emailField;

    @FXML
    private TextField guardianNumberField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ImageView profileImageView;

    @FXML
    private ImageView nationalIdImageView;

    @FXML
    private Button copyUsernameButton;

    @FXML
    private Button copyPasswordButton;

    @FXML
    private Button uploadImageButton;

    @FXML
    private Button uploadIdButton;

    @FXML
    private Button editButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    public void initialize() {
        // Initialize the choice box with grade options
        gradeChoiceBox.getItems().addAll("Grade 1", "Grade 2", "Grade 3", "Grade 4");

        // Disable save button initially
        saveButton.setDisable(true);

        // Set action listeners for buttons
        copyUsernameButton.setOnAction(event -> copyToClipboard(usernameField.getText()));
        copyPasswordButton.setOnAction(event -> copyToClipboard(passwordField.getText()));
        uploadImageButton.setOnAction(event -> uploadImage());
        uploadIdButton.setOnAction(event -> uploadDocument());
        editButton.setOnAction(event -> enableEditing());
        saveButton.setOnAction(event -> saveChanges());
        cancelButton.setOnAction(event -> cancelEditing());
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
        gradeChoiceBox.setDisable(false);
        emailField.setDisable(false);
        guardianNumberField.setDisable(false);
        phoneNumberField.setDisable(false);
        usernameField.setDisable(false);
        passwordField.setDisable(false);
        saveButton.setDisable(false);
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
        gradeChoiceBox.setDisable(true);
        emailField.setDisable(true);
        guardianNumberField.setDisable(true);
        phoneNumberField.setDisable(true);
        usernameField.setDisable(true);
        passwordField.setDisable(true);
        saveButton.setDisable(true);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}