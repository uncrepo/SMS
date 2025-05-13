package org.project.sms.Controllers.Student;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.project.sms.Models.Model;
import org.project.sms.options.StudentMenuOptions;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentMenuController implements Initializable {
    public Button btnDashboard;
    public Button btnDepartmentInfo;
    public Button btnResults;
    public Button btnProfile;
    public Button btnLogout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        btnDashboard.setOnAction(event -> onDashboard());
        btnDepartmentInfo.setOnAction(event -> onDepartmentInfo());
        btnResults.setOnAction(event -> onResults());
        btnProfile.setOnAction(event -> onProfile());
        btnLogout.setOnAction(actionEvent -> closeWindow());

    }


    private void onDashboard() {
        Model.getInstance().getStudentSelectedMenu().set(StudentMenuOptions.DASHBOARD);
    }

    private void onDepartmentInfo() {
        Model.getInstance().getStudentSelectedMenu().set(StudentMenuOptions.DEPARTMENT);

    }

    private void onResults() {
        Model.getInstance().getStudentSelectedMenu().set(StudentMenuOptions.RESULTS);

    }


    private void onProfile() {
        Model.getInstance().getStudentSelectedMenu().set(StudentMenuOptions.PROFILE);

    }

    private void closeWindow() {
        btnLogout.getScene().getWindow().hide();
        Model.getInstance().getViewFactory().showLoginWindow();// Closes login stage
    }

}
