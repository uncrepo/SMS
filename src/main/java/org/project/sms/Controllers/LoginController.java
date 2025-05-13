package org.project.sms.Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
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

//    public ComboBox<AccountType> roleComboBox;
    public TextField emailField;
    public PasswordField passwordField;
    public Label errorLabel;
    public Button loginButton;
    public ImageView imageError;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        roleComboBox.setItems(FXCollections.observableArrayList(AccountType.STUDENT, AccountType.TEACHER, AccountType.ADMIN));
//        roleComboBox.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
//        roleComboBox.valueProperty().addListener(observable -> Model.getInstance().getViewFactory().setLoginAccountType(roleComboBox.getValue()));
        imageError.setVisible(false);
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
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please fill in all fields.");
            imageError.setVisible(true);
            return;
        } // add a ! icon infront of the message.

        User user = new UsersDAO().login(email, password);

        if (user == null){
            errorLabel.setText("Invalid email or password.");
            imageError.setVisible(true);
        }

        if (user != null) {
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
                closeLoginWindow();
            }
            case TEACHER -> {
                Teacher teacher = new TeacherDAO().getTeacherByUserId(user.getUserId());
                model.setCurrentTeacher(teacher);
                model.getCurrentUserRole().set(AccountType.TEACHER);
                model.getTeacherViewFactory().showTeacherWindow();
                closeLoginWindow();

            }
            case ADMIN -> {
                Admin admin = new AdminDAO().getAdminByUserId(user.getUserId());
                model.setCurrentAdmin(admin);
                model.getCurrentUserRole().set(AccountType.ADMIN);
                model.getAdminViewFactory().showAdminWindow();
                closeLoginWindow();
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

