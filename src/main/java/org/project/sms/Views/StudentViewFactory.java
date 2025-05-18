package org.project.sms.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.project.sms.Controllers.Student.StudentController;


public class StudentViewFactory extends ViewFactory {
    //    admin views
    private AnchorPane studentCurrentSemesterView;
    private AnchorPane studentDepartmentView;
    private AnchorPane studentResultsView;
    private AnchorPane studentProfileView;


    public void showStudentWindow() {
        createStage("/Fxml/Student/student.fxml", new StudentController());
    }

    public AnchorPane getStudentCurrentSemester() {
        if(studentCurrentSemesterView==null) {
            try {
                studentCurrentSemesterView = new FXMLLoader(getClass().getResource("/Fxml/Student/student_semester.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return studentCurrentSemesterView;
    }

    public AnchorPane getStudentAssignmentView() {
        if(studentDepartmentView==null) {
            try {
                studentDepartmentView = new FXMLLoader(getClass().getResource("/Fxml/Student/student_assignment.fxml")).load();
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
