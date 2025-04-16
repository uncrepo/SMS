package org.project.sms.Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import org.project.sms.Models.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.project.sms.dao.AdminDAO;
import org.project.sms.dao.StudentDAO;
import org.project.sms.dao.TeacherDAO;
import org.project.sms.dao.UsersDAO;
import org.project.sms.options.AccountType;

import java.net.URL;

import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public ComboBox<AccountType> roleComboBox;
    @FXML
    public TextField emailField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Label errorLabel;
    public Button loginButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roleComboBox.setItems(FXCollections.observableArrayList(AccountType.STUDENT, AccountType.TEACHER, AccountType.ADMIN));
        roleComboBox.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
        roleComboBox.valueProperty().addListener(observable -> Model.getInstance().getViewFactory().setLoginAccountType(roleComboBox.getValue()));
        loginButton.setOnAction(event -> handleLogin());

    }


//    private void onLogin() {
//        Stage stage = (Stage) errorLabel.getScene().getWindow();
//        // close login stage or overwrite ?
//        if(Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.STUDENT)
//            Model.getInstance().getViewFactory().showStudentWindow();
//        else if(Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.TEACHER)
//            Model.getInstance().getViewFactory().showTeacherWindow();
//        else Model.getInstance().getViewFactory().showAdminWindow();
//    }

    //    @FXML
    public void handleLogin() {
        AccountType role = roleComboBox.getValue();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (role == null) {
            errorLabel.setText("Please select a role.");
            return;
        }
        if (email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please fill in all fields.");
            return;
        }

        User user = new UsersDAO().login(email, password);

        if (user != null && user.getRole() == role) {
            completeLogin(user); // ✅ pass User object here
        }
    }

    private void completeLogin(User user) {
        Model model = Model.getInstance();

        switch (user.getRole()) {
            case STUDENT -> {
                Student student = new StudentDAO().getStudentByUserId(user.getUserId());
                model.setCurrentStudent(student);
                model.getCurrentUserRole().set(AccountType.STUDENT);
                model.getStudentViewFactory().showStudentWindow();
            }
            case TEACHER -> {
                Teacher teacher = new TeacherDAO().getTeacherByUserId(user.getUserId());
                model.setCurrentTeacher(teacher);
                model.getCurrentUserRole().set(AccountType.TEACHER);
                model.getTeacherViewFactory().showTeacherWindow();
            }
            case ADMIN -> {
                Admin admin = new AdminDAO().getAdminByUserId(user.getUserId());
                model.setCurrentAdmin(admin);
                model.getCurrentUserRole().set(AccountType.ADMIN);
                model.getAdminViewFactory().showAdminWindow();
            }
        }
    }

        private void closeLoginWindow() {
            emailField.getScene().getWindow().hide(); // Closes login stage
        }
    }


//    private void loadDashboard(String role) throws Exception {
//        String fxml = switch (role) {
//            case "Admin" -> "/Fxml/admin.fxml";
//            case "Teacher" -> "/Fxml/teacher.fxml";
//            case "Student" -> "/Fxml/student.fxml";
//            default -> throw new IllegalArgumentException("Invalid role");
//        };
//
//        Parent root = FXMLLoader.load(getClass().getResource(fxml));
//        Stage stage = (Stage) emailField.getScene().getWindow();
//        stage.setScene(new Scene(root));
//    }
//
//    @FXML
//    public void handleCreateAccount(ActionEvent event) {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/views/create_account.fxml"));
//            Stage stage = (Stage) emailField.getScene().getWindow();
//            stage.setScene(new Scene(root));
//        } catch (Exception e) {
//            errorLabel.setText("Error loading registration: " + e.getMessage());
//        }
//    }

