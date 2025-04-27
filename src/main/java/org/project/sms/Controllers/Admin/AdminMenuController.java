package org.project.sms.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.project.sms.Models.Model;
import org.project.sms.options.AdminMenuOptions;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button btnDashboard;
    public Button btnTeachers;
    public Button btnDepartments;
//    public Button btnClasses;
    public Button btnGrades;
    public Button btnLogout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        btnDashboard.setOnAction(event -> onDashboard());
        btnTeachers.setOnAction(event -> onTeachers());
        btnDepartments.setOnAction(event -> onDepartments());
//        btnClasses.setOnAction(event -> onClasses());
        btnGrades.setOnAction(event -> onGrades());

    }


    private void onDashboard() {
        Model.getInstance().getAdminSelectedMenu().set(AdminMenuOptions.DASHBOARD);
    }

    private void onTeachers() {
        Model.getInstance().getAdminSelectedMenu().set(AdminMenuOptions.TEACHERS);

    }

    private void onDepartments() {
        Model.getInstance().getAdminSelectedMenu().set(AdminMenuOptions.DEPARTMENTS);

    }

//    private void onClasses() {
//        Model.getInstance().getAdminSelectedMenu().set(AdminMenuOptions.CLASSES);
//
//    }

    private void onGrades() {
        Model.getInstance().getAdminSelectedMenu().set(AdminMenuOptions.GRADES);

    }


}
