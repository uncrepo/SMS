package org.project.sms.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.project.sms.Controllers.Student.StudentController;


public class StudentViewFactory extends ViewFactory {
    //    admin views
    private AnchorPane studentDashboardView;
    private AnchorPane studentDepartmentView;
    private AnchorPane studentResultsView;
    private AnchorPane studentProfileView;


    public void showStudentWindow() {
        createStage("/Fxml/Student/student.fxml", new StudentController());
    }

    public AnchorPane getStudentDashboardView() {
        if(studentDashboardView==null) {
            try {
                studentDashboardView = new FXMLLoader(getClass().getResource("/Fxml/Student/student_dashboard.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return studentDashboardView;
    }

    public AnchorPane getStudentDepartmentView() {
        if(studentDepartmentView==null) {
            try {
                studentDepartmentView = new FXMLLoader(getClass().getResource("/Fxml/Student/student_department.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return studentDepartmentView;
    }

    public AnchorPane getStudentResultsView() {
        if(studentResultsView==null) {
            try {
                studentResultsView = new FXMLLoader(getClass().getResource("/Fxml/Student/student_results.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return studentResultsView;
    }

    public AnchorPane getStudentProfileView() {
        if(studentProfileView==null) {
            try {
                studentProfileView = new FXMLLoader(getClass().getResource("/Fxml/Student/student_profile.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return studentProfileView;
    }


}
