package org.project.sms.Controllers.Teacher;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.project.sms.Models.Model;
import org.project.sms.options.TeacherMenuOptions;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherMenuController implements Initializable {
    public Button btnDashboard;
    public Button btnClasses;
    public Button btnAssignments;
    public Button btnGrades;
    public Button btnLogout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        btnDashboard.setOnAction(event -> onDashboard());
        btnClasses.setOnAction(event -> onClasses());
        btnAssignments.setOnAction(event -> onAssignments());
        btnGrades.setOnAction(event -> onGrades());
    }


    private void onDashboard() {
        Model.getInstance().getTeacherSelectedMenu().set(TeacherMenuOptions.DASHBOARD);
    }

    private void onClasses() {
        Model.getInstance().getTeacherSelectedMenu().set(TeacherMenuOptions.CLASSES);

    }

    private void onAssignments() {
        Model.getInstance().getTeacherSelectedMenu().set(TeacherMenuOptions.ASSIGNMENTS);

    }


    private void onGrades() {
        Model.getInstance().getTeacherSelectedMenu().set(TeacherMenuOptions.GRADES);

    }


}

