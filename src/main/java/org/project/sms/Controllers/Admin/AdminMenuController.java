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
    public Button btnAssignTeacher;
    public Button btnAddTeacher;
    public Button btnEditTeacher;

    public Button btnClasses;
    public Button btnAssignStudents;
    public Button btnGenerateSchedule;
    public Button btnAddClass;
    public Button btnEditClass;
    public Button btnPrevClasses;


    public Button btnStudents;
    public Button btnStudentStats;
    public Button btnAddStudent;
    public Button btnEditStudent;


    public Button btnAdminProfile;
    public Button btnLogout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        btnDashboard.setOnAction(event -> onDashboard());

        btnTeachers.setOnAction(actionEvent -> onTeachers());
        btnAssignTeacher.setOnAction(actionEvent -> onAssignTeacher());
        btnAddTeacher.setOnAction(actionEvent -> onAddTeacher());
        btnEditTeacher.setOnAction(actionEvent -> onEditTeacher());

        btnClasses.setOnAction(actionEvent -> onClasses());
        btnAssignStudents.setOnAction(actionEvent -> onAssignStudents());
        btnGenerateSchedule.setOnAction(actionEvent -> onGenerateSchedule());
        btnAddClass.setOnAction(actionEvent -> onAddClass());
        btnEditClass.setOnAction(actionEvent -> onEditClass());
        btnPrevClasses.setOnAction(actionEvent -> onPrevClasses());


        btnStudents.setOnAction(event -> onStudents());
        btnStudentStats.setOnAction(actionEvent -> onStudentStats());
        btnAddStudent.setOnAction(actionEvent -> onAddStudent());
        btnEditStudent.setOnAction(actionEvent -> onEditStudent());


        btnAdminProfile.setOnAction(actionEvent -> onProfile());
        btnLogout.setOnAction(actionEvent -> closeWindow());

    }

    private void onAssignStudents() {
        Model.getInstance().getAdminSelectedMenu().set(AdminMenuOptions.ASSIGN_STUDENT_CLASS);

    }

    private void onGenerateSchedule() {
        Model.getInstance().getAdminSelectedMenu().set(AdminMenuOptions.GENERATE_CLASS_SCHEDULE);

    }

    private void onAddClass() {
        Model.getInstance().getAdminSelectedMenu().set(AdminMenuOptions.ADD_NEW_CLASS);

    }

    private void onEditClass() {
        Model.getInstance().getAdminSelectedMenu().set(AdminMenuOptions.EDIT_CLASS);

    }

    private void onPrevClasses() {
        Model.getInstance().getAdminSelectedMenu().set(AdminMenuOptions.PREVIOUS_CLASS_RESULTS);

    }

    private void onStudentStats() {
        Model.getInstance().getAdminSelectedMenu().set(AdminMenuOptions.STUDENT_STATS);

    }

    private void onEditStudent() {
        Model.getInstance().getAdminSelectedMenu().set(AdminMenuOptions.EDIT_STUDENT);

    }

    private void onAddStudent() {
        Model.getInstance().getAdminSelectedMenu().set(AdminMenuOptions.ADD_NEW_STUDENT);

    }

    private void onProfile() {
        Model.getInstance().getAdminSelectedMenu().set(AdminMenuOptions.ADMIN_PROFILE);

    }

    private void onEditTeacher() {
        Model.getInstance().getAdminSelectedMenu().set(AdminMenuOptions.EDIT_TEACHER);

    }

    private void onAddTeacher() {
        Model.getInstance().getAdminSelectedMenu().set(AdminMenuOptions.ADD_NEW_TEACHER);

    }

    private void onAssignTeacher() {
        Model.getInstance().getAdminSelectedMenu().set(AdminMenuOptions.ASSIGN_TEACHER);

    }


    private void onDashboard() {
        Model.getInstance().getAdminSelectedMenu().set(AdminMenuOptions.DASHBOARD);
    }

    private void onTeachers() {
        Model.getInstance().getAdminSelectedMenu().set(AdminMenuOptions.TEACHERS);

    }

    private void onClasses() {
        Model.getInstance().getAdminSelectedMenu().set(AdminMenuOptions.CLASSES);

    }


    private void onStudents() {
        Model.getInstance().getAdminSelectedMenu().set(AdminMenuOptions.STUDENTS);

    }

    private void closeWindow() {
        btnLogout.getScene().getWindow().hide();
        Model.getInstance().getViewFactory().showLoginWindow();// Closes login stage
    }


}
