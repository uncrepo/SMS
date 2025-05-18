package org.project.sms.Controllers.Student;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.project.sms.Models.Model;
import org.project.sms.options.StudentMenuOptions;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentMenuController implements Initializable {
    public Button btnCurrentSemester;
    public Button btnAssignments;
    public Button btnResults;
    public Button btnProfile;
    public Button btnLogout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        btnCurrentSemester.setOnAction(event -> onCurrentSemester());
        btnAssignments.setOnAction(event -> onAssignments());
        btnResults.setOnAction(event -> onResults());
        btnProfile.setOnAction(event -> onProfile());
        btnLogout.setOnAction(actionEvent -> closeWindow());

    }


    private void onCurrentSemester() {
        Model.getInstance().getStudentSelectedMenu().set(StudentMenuOptions.CURRENT_SEMESTER);
    }

    private void onAssignments() {
        Model.getInstance().getStudentSelectedMenu().set(StudentMenuOptions.ASSIGNMENTS);

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
