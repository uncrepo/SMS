package org.project.sms.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.project.sms.Models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane admin_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getAdminSelectedMenu().addListener((obs, oldVal, newVal) -> {
            switch (newVal) {
                case STUDENTS -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getAdminStudentsView());
                case STUDENT_STATS -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getAdminStudentStatsView());
                case ADD_NEW_STUDENT -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getAdminAddStudentView());
                case EDIT_STUDENT -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getAdminEditStudentView());

                case TEACHERS -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getAdminTeachersView());
                case ASSIGN_TEACHER -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getAdminAssignTeacherView());
                case ADD_NEW_TEACHER -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getAdminAddTeacherView());
                case EDIT_TEACHER -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getAdminEditTeacherView());

                case CLASSES -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getAdminClassesView());
                case ASSIGN_STUDENT_CLASS -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getAdminAssignStudentView());
                case GENERATE_CLASS_SCHEDULE -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getAdminClassScheduleView());
                case ADD_NEW_CLASS -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getAdminAddClassView());
                case EDIT_CLASS -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getAdminEditClassView());
                case PREVIOUS_CLASS_RESULTS -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getAdminPreviousClassResultsView());

                case ADMIN_PROFILE -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getAdminProfileView());
                case ADMIN_LOGS -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getAdminLogsView());

                default -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getAdminDashboardView());
            }
        });

//        admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getDashboardView());
    }
}
