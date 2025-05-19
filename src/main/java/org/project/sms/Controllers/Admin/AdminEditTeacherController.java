package org.project.sms.Controllers.Admin;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.project.sms.Models.Student;
import org.project.sms.Models.Teacher;
import org.project.sms.dao.*;
import org.project.sms.options.SortOptions;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
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
    public Button editTeacherBtn;
    public Button deleteTeacherBtn;
    public Button addTeacherBtn;
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

    private int currentPage = 0;
    private final int ROWS_PER_PAGE = 10;


    public void initialize(URL location, ResourceBundle resources) {
        initTableCols();
        BtnClicks();

        setupEventHandlers();

        setupComboBoxListeners();
        setupPaginationButtons();
        loadFilteredPage();

        // Load the teacher data
//        loadTeacherData();

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
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        sortByComboBox.setItems(FXCollections.observableArrayList(SortOptions.AssignedStudentDetailsFilter));
        filterComboBox.setItems(FXCollections.observableArrayList(SortOptions.CommonFilers));
    }



    private void BtnClicks() {
        searchBtn.setOnAction(e -> filterTeachers());
        deleteTeacherBtn.setOnAction(e -> handleDeleteTeacher());
        addTeacherBtn.setOnAction(e -> addTeacher());
        resetBtn.setOnAction(e -> clearSearchFields());
    }



    // Filter action on search
    private void filterTeachers() {
        String searchText = searchTeacherField.getText();

        // Perform filtering based on the input search text, department, and section
        teacherTableView.setItems(TeacherDAO.searchTeachersByName(searchText));
    }



    private void setupEventHandlers() {
        editTeacherBtn.setOnAction(e -> editTeacherDialog());
        deleteTeacherBtn.setOnAction(e -> handleDeleteTeacher());
        addTeacherBtn.setOnAction(e -> addTeacherDialog());
    }


    private void handleEditTeacher() {
        Teacher selectedTeacher = teacherTableView.getSelectionModel().getSelectedItem();
        if (selectedTeacher == null) {
            showAlert("No Selection", "Please select a teacher to edit.", Alert.AlertType.WARNING);
            return;
        }

        // Create a dialog
        Dialog<Teacher> dialog = new Dialog<>();
        dialog.setTitle("Edit Teacher");
        dialog.setHeaderText("Edit Teacher Information");

        // Set the button types
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);



        // Create the form grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        // Create text fields
        TextField fullNameField = new TextField(selectedTeacher.getFullName());
        TextField emailField = new TextField(selectedTeacher.getEmail());
        TextField phoneField = new TextField(selectedTeacher.getPhone());
        TextField guardianField = new TextField(selectedTeacher.getGuardian());

        // Add fields to grid
        grid.add(new Label("Full Name:"), 0, 0);
        grid.add(fullNameField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(new Label("Phone:"), 0, 2);
        grid.add(phoneField, 1, 2);
        grid.add(new Label("Guardian:"), 0, 3);
        grid.add(guardianField, 1, 3);
//        grid.add(new Label("Grade:"), 0, 4);
//        grid.add(gradeField, 1, 4);
//        grid.add(new Label("Academic Year:"), 0, 5);
//        grid.add(academicYearField, 1, 5);
//        grid.add(new Label("Course:"), 0, 6);
//        grid.add(courseField, 1, 6);
//        grid.add(new Label("Section:"), 0, 7);
//        grid.add(sectionField, 1, 7);

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a teacher object when the save button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                selectedTeacher.setFullName(fullNameField.getText());
                selectedTeacher.setEmail(emailField.getText());
                selectedTeacher.setPhone(phoneField.getText());
                selectedTeacher.setGuardian(guardianField.getText());
                return selectedTeacher;
            }
            return null;
        });

        Optional<Teacher> result = dialog.showAndWait();
        result.ifPresent(teacher -> {
            // Update the teacher in the database
            TeacherDAO.updateTeacher(teacher);
            loadTeacherData(); // Refresh the table
            showAlert("Success", "Teacher information updated successfully!", Alert.AlertType.INFORMATION);
        });
    }

    private void addTeacher() {
        // Create a dialog
        Dialog<Teacher> dialog = new Dialog<>();
        dialog.setTitle("Add Teacher");
        dialog.setHeaderText("Add Teacher Information");

        // Set the button types
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the form grid




        Optional<Teacher> result = dialog.showAndWait();
        result.ifPresent(teacher -> {
            // Update the teacher in the database
            TeacherDAO.addTeacher(teacher);
            loadTeacherData(); // Refresh the table
            showAlert("Success", "New Teacher added successfully!", Alert.AlertType.INFORMATION);
        });
    }

    private void handleDeleteTeacher() {
        Teacher selectedTeacher = teacherTableView.getSelectionModel().getSelectedItem();
        if (selectedTeacher == null) {
            showAlert("No Selection", "Please select a teacher to delete.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Delete");
        confirmDialog.setHeaderText("Delete Teacher");
        confirmDialog.setContentText("Are you sure you want to delete " + selectedTeacher.getFullName() + "?");

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Delete the teacher from the database
            TeacherDAO.deleteTeacher(selectedTeacher.getTeacherId());
            loadTeacherData(); // Refresh the table
            showAlert("Success", "Teacher deleted successfully!", Alert.AlertType.INFORMATION);
        }
    }

//    private void handleSearch() {
//        String searchText = searchTeacherField.getText().trim();
//        if (!searchText.isEmpty()) {
//            // Implement search functionality
//            List<Teacher> searchResults = TeacherDAO.searchTeachers(searchText);
//            teacherTableView.setItems(FXCollections.observableArrayList(searchResults));
//        } else {
//            loadTeacherData(); // If search field is empty, load all teachers
//        }
//    }

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


    private void addTeacherDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_new_teacher.fxml"));
            AnchorPane anchorPane = loader.load();

            Dialog<Teacher> dialog = new Dialog<>();
            dialog.setTitle("Add Teacher");
            dialog.setHeaderText("Add Teacher Information");

            AdminAddTeacherController controller = loader.getController();
            dialog.getDialogPane().setContent(anchorPane);

            // Set the button types
            ButtonType saveButtonType = new ButtonType("Add Teacher", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            // Convert the result to a teacher object when the save button is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    Teacher updated = controller.getNewTeacherInfo();
                    return new Teacher (
                            updated.getFullName(),
                            updated.getEmail(),
                            updated.getUsername(),
                            updated.getPassword(),
                            updated.getGender(),
                            updated.getPhone(),
                            updated.getGuardian(),
                            updated.getPhone()
                    );
                }
                return null;
            });

            Optional<Teacher> result = dialog.showAndWait();
            result.ifPresent(teacher -> {
                // Update the teacher in the database
                TeacherDAO.addTeacher(teacher);
                loadTeacherData(); // Refresh the table
                showAlert("Success", "A new Teacher was added successfully!", Alert.AlertType.INFORMATION);
            });

//            AdminAddTeacherController controller = loader.getController();
//
//            Optional<ButtonType> result = dialog.showAndWait();
//            if (result.isPresent() && result.get() == ButtonType.OK) {
//                Teacher newTeacher = controller.getNewTeacherInfo();
//                TeacherDAO.addTeacher(newTeacher);
//                loadTeacherData(); // Refresh the table
//                showAlert("Success", "Teacher information updated successfully!", Alert.AlertType.INFORMATION);
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void editTeacherDialog() {
        try {
            Teacher editTeacher = teacherTableView.getSelectionModel().getSelectedItem();
            if (editTeacher == null) {
                showAlert("No Selection", "Please select a teacher to edit.", Alert.AlertType.WARNING);
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_edit_teacher_dialog.fxml"));
            AnchorPane anchorPane = loader.load();
            AdminEditTeacherDialogController controller = loader.getController();

            controller.setTeacherForEdit(editTeacher);

            Dialog<Teacher> dialog = new Dialog<>();
            dialog.setTitle("Edit Teacher");
            dialog.setHeaderText("Edit Teacher Information");

            dialog.getDialogPane().setContent(anchorPane);


            // Set the button types
            ButtonType saveButtonType = new ButtonType("Update Teacher", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);


            // Convert the result to a teacher object when the save button is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                Teacher updated = controller.getEditTeacherValues();
                    editTeacher.setFullName(updated.getFullName());
                    editTeacher.setEmail(updated.getEmail());
                    editTeacher.setPhone(updated.getPhone());
                    editTeacher.setGuardian(updated.getGuardian());
                    editTeacher.setStatus(updated.getStatus());
                    editTeacher.setGender(updated.getGender());
                    return editTeacher;
                }
                return null;
            });

            Optional<Teacher> result = dialog.showAndWait();
            result.ifPresent(teacher -> {
                // Update the teacher in the database
                TeacherDAO.updateTeacher(teacher);
                loadTeacherData(); // Refresh the table
                showAlert("Success", "Teacher information updated successfully!", Alert.AlertType.INFORMATION);
            });

        } catch (IOException e) {
            showAlert("Failed", "Teacher information isn't updated!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void clearSearchFields() {
        searchTeacherField.clear();
    }

    private void setupComboBoxListeners() {
        ChangeListener<String> comboListener = (obs, oldVal, newVal) -> {
            currentPage = 0;
            loadFilteredPage();
        };
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

        int offset = currentPage * ROWS_PER_PAGE;

        List<Teacher> results = FilterDAO.getAllTeachersDetailsForEditByFilter(
                ROWS_PER_PAGE, offset
        );

        teacherTableView.setItems(FXCollections.observableArrayList(results));

        // Optional: Disable buttons if on bounds
        previousBtn.setDisable(currentPage == 0);
        nextBtn.setDisable(results.size() < ROWS_PER_PAGE);
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
