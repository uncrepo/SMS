package org.project.sms.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.project.sms.Controllers.Teacher.TeacherController;


public class TeacherViewFactory extends ViewFactory {
    // teachers views
    private AnchorPane teacherDashboardView;
    private AnchorPane teacherClassesView;
    private AnchorPane teacherAssignmentsView;
    private AnchorPane teacherGradesView;


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
                teacherAssignmentsView = new FXMLLoader(getClass().getResource("/Fxml/Teacher/teacher_assignments.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return teacherAssignmentsView;
    }

    public AnchorPane getTeacherClassesView() {
        if(teacherClassesView==null) {
            try {
                teacherClassesView = new FXMLLoader(getClass().getResource("/Fxml/Teacher/teacher_classes.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return teacherClassesView;
    }

    public AnchorPane getTeacherGradesView() {
        if(teacherGradesView==null) {
            try {
                teacherGradesView = new FXMLLoader(getClass().getResource("/Fxml/Teacher/teacher_grades.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return teacherGradesView;
    }

}
