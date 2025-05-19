package org.project.sms.Controllers.Teacher;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.project.sms.Models.Model;
import org.project.sms.options.TeacherMenuOptions;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherMenuController implements Initializable {
    public Button btnClasses;
    public Button btnAssignments;
    public Button btnProfile;
    public Button btnLogout;
    public Button btnSchedule;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        btnSchedule.setOnAction(event -> onSchedule());
        btnClasses.setOnAction(event -> onClasses());
        btnAssignments.setOnAction(event -> onAssignments());
        btnProfile.setOnAction(event -> onProfile());
        btnLogout.setOnAction(actionEvent -> closeWindow());

    }


    private void onSchedule() {
        Model.getInstance().getTeacherSelectedMenu().set(TeacherMenuOptions.SCHEDULE);
    }

    private void onClasses() {
        Model.getInstance().getTeacherSelectedMenu().set(TeacherMenuOptions.CLASSES);

    }

    private void onAssignments() {
        Model.getInstance().getTeacherSelectedMenu().set(TeacherMenuOptions.ASSIGNMENTS);

    }


    private void onProfile() {
        Model.getInstance().getTeacherSelectedMenu().set(TeacherMenuOptions.PROFILE);

    }

    private void closeWindow() {
        btnLogout.getScene().getWindow().hide();
        Model.getInstance().getViewFactory().showLoginWindow();// Closes login stage
    }


}

