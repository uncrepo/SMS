package org.project.sms.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.project.sms.Controllers.Teacher.TeacherController;


public class TeacherViewFactory extends ViewFactory {
    // teachers views
    private AnchorPane teacherDashboardView;
    private AnchorPane teacherClassesView;
    private AnchorPane teacherAssignmentsView;
    private AnchorPane teacherProfileView;


    public void showTeacherWindow() {
        createStage("/Fxml/Teacher/teacher.fxml",new TeacherController());
    }

    public AnchorPane getTeacherDashboardView() {
        if(teacherDashboardView==null) {
            try {
                teacherDashboardView = new FXMLLoader(getClass().getResource("/Fxml/Teacher/teacher_dashboard.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return teacherDashboardView;
    }

    public AnchorPane getTeacherAssignmentsView() {
        if(teacherAssignmentsView==null) {
            try {
                teacherAssignmentsView = new FXMLLoader(getClass().getResource("/Fxml/Teacher/teacher_assignment.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return teacherAssignmentsView;
    }

    public AnchorPane getTeacherClassesView() {
        if(teacherClassesView==null) {
            try {
                teacherClassesView = new FXMLLoader(getClass().getResource("/Fxml/Teacher/teacher_class.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return teacherClassesView;
    }

    public AnchorPane getTeacherProfileView() {
        if(teacherProfileView==null) {
            try {
                teacherProfileView = new FXMLLoader(getClass().getResource("/Fxml/Teacher/teacher_profile.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return teacherProfileView;
    }

}
