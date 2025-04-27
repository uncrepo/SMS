package org.project.sms.Controllers.Student;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.project.sms.Models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentController implements Initializable {
    public BorderPane student_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getStudentSelectedMenu().addListener((obs, oldVal, newVal) -> {
            switch (newVal) {
                case DEPARTMENT -> student_parent.setCenter(Model.getInstance().getStudentViewFactory().getStudentDepartmentView());
                case RESULTS -> student_parent.setCenter(Model.getInstance().getStudentViewFactory().getStudentResultsView());
                case PROFILE -> student_parent.setCenter(Model.getInstance().getStudentViewFactory().getStudentProfileView());
                default -> student_parent.setCenter(Model.getInstance().getStudentViewFactory().getStudentDashboardView());
            }
        });

    }
}
