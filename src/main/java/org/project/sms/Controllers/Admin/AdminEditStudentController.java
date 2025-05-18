package org.project.sms.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.project.sms.Models.AddStudent;
import org.project.sms.Models.Student;
import org.project.sms.Models.Teacher;
import org.project.sms.dao.StudentDAO;
import org.project.sms.dao.TeacherDAO;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminEditStudentController implements Initializable {
    public TableView<Student> studentTableView;
    public TableColumn<Student, String> colStudentId;
    public TableColumn<Student, String> colFullName;
    public TableColumn<Student, String> colPhone;
    public TableColumn<Student, String> colGuardian;
    public TableColumn<Student, String> colEmail;

    public ComboBox<String> sortByComboBox;
    public ComboBox<String> filterComboBox;
    public TextField searchStudentField;
    public Button searchBtn;


    public Button toggleEnableBtn;
    public Button saveChangesBtn;
    public Button editStudentBtn;
    public Button deleteStudentBtn;
    public Button addStudentBtn;
    public Button cancelBtn;
    public Button resetBtn;

    public TextField selectedTeacherNameField;
    public TextField selectedTeacherIdField;
    public TextField fullNameField;
    public TextField emailField;
    public TextField guardianField;
    public TextField phoneField;
    public TableColumn<Teacher, String> colGender;
    public TableColumn<Teacher, String> colStatus;
    public Button previousBtn;
    public Button nextBtn;

    public void initialize(URL location, ResourceBundle resources) {
        initTableCols();

        setupEventHandlers();

        // Load the teacher data
        loadStudentsData();

        // Filter action on search
    }



    private void loadStudentsData() {
        studentTableView.setItems(FXCollections.observableArrayList(StudentDAO.getAllStudents()));

    }


    private void initTableCols() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colGuardian.setCellValueFactory(new PropertyValueFactory<>("guardian"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

    }






    // Filter action on search
    private void filterStudents() {
        String searchText = searchStudentField.getText();

        // Perform filtering based on the input search text, department, and section
        studentTableView.setItems(StudentDAO.searchStudentsByName(searchText));
    }



    private void setupEventHandlers() {
        editStudentBtn.setOnAction(e -> editStudentDialog());
        deleteStudentBtn.setOnAction(e -> handleDeleteStudent());
        addStudentBtn.setOnAction(e -> addStudentDialog());
        searchBtn.setOnAction(e -> filterStudents());
        resetBtn.setOnAction(e -> clearSearchFields());
    }


    private void handleDeleteStudent() {
        Student selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showAlert("No Selection", "Please select a teacher to delete.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Delete");
        confirmDialog.setHeaderText("Delete Student");
        confirmDialog.setContentText("Are you sure you want to delete " + selectedStudent.getFullName() + "?");

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Delete the teacher from the database
            StudentDAO.deleteStudent(selectedStudent.getStudentId());
            loadStudentsData(); // Refresh the table
            showAlert("Success", "Student deleted successfully!", Alert.AlertType.INFORMATION);
        }
    }



    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void clearSelectedFields() {
        fullNameField.clear();
        emailField.clear();
        guardianField.clear();
        phoneField.clear();
        selectedTeacherIdField.clear();
        selectedTeacherNameField.clear();

    }


    private void addStudentDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_new_student.fxml"));
            AnchorPane anchorPane = loader.load();

            Dialog<AddStudent> dialog = new Dialog<>();
            dialog.setTitle("Add Student");
            dialog.setHeaderText("Add Student Information");

            AdminAddStudentController controller = loader.getController();
            dialog.getDialogPane().setContent(anchorPane);

            // Set the button types
            ButtonType saveButtonType = new ButtonType("Add Student", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            // Convert the result to a teacher object when the save button is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    AddStudent updated = controller.getNewStudentInfo();
                    return new AddStudent (
                            updated.getFullName(),
                            updated.getEmail(),
                            updated.getUsername(),
                            updated.getPassword(),
                            updated.getGender(),
                            updated.getGuardian(),
                            updated.getPhone()
                            );
                }
                return null;
            });

            Optional<AddStudent> result = dialog.showAndWait();
            result.ifPresent(student -> {
                // Update the teacher in the database
                StudentDAO.addStudent(student);
                loadStudentsData(); // Refresh the table
                showAlert("Success", "A new Student was added successfully!", Alert.AlertType.INFORMATION);
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void editStudentDialog() {
        try {
            Student editStudent = studentTableView.getSelectionModel().getSelectedItem();
            if (editStudent == null) {
                showAlert("No Selection", "Please select a Student to edit.", Alert.AlertType.WARNING);
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_edit_student_dialog.fxml"));
            AnchorPane anchorPane = loader.load();
            AdminEditStudentDialogController controller = loader.getController();

            controller.setStudentForEdit(editStudent);

            Dialog<Student> dialog = new Dialog<>();
            dialog.setTitle("Edit Student");
            dialog.setHeaderText("Edit Student Information");

            dialog.getDialogPane().setContent(anchorPane);


            // Set the button types
            ButtonType saveButtonType = new ButtonType("Update Student", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);


            // Convert the result to a teacher object when the save button is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                AddStudent updated = controller.getEditStudentValues();
                    editStudent.setFullName(updated.getFullName());
                    editStudent.setEmail(updated.getEmail());
                    editStudent.setPhone(updated.getPhone());
                    editStudent.setGuardian(updated.getGuardian());
                    editStudent.setStatus(updated.getStatus());
                    editStudent.setGender(updated.getGender());
                    return editStudent;
                }
                return null;
            });

            Optional<Student> result = dialog.showAndWait();
            result.ifPresent(student -> {
                // Update the teacher in the database
                StudentDAO.updateStudentInfo(student);
                loadStudentsData(); // Refresh the table
                showAlert("Success", "Student information updated successfully!", Alert.AlertType.INFORMATION);
            });

        } catch (IOException e) {
            showAlert("Failed", "Teacher information isn't updated!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void clearSearchFields() {
        searchStudentField.clear();
    }


    // Initialize ComboBoxes with string values
//        sortByComboBox.setItems(FXCollections.observableArrayList(
//                Arrays.stream(DepartmentOptions.values())
//                .map(Enum::toString)
//                  .collect(Collectors.toList())
//            ));
//        filterComboBox.setItems(FXCollections.observableArrayList(
//                Arrays.stream(SectionOptions.values())
//                .map(Enum::toString)
//                  .collect(Collectors.toList())
//            ));

}
