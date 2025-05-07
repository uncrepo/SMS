package org.project.sms.Controllers.Teacher;


import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.project.sms.Models.Model;

import java.net.URL;
import java.util.ResourceBundle;


public class TeacherController implements Initializable {
    public BorderPane teacher_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getTeacherSelectedMenu().addListener((obs, oldVal, newVal) -> {
            switch (newVal) {
                case ASSIGNMENTS -> teacher_parent.setCenter(Model.getInstance().getTeacherViewFactory().getTeacherAssignmentsView());
                case CLASSES -> teacher_parent.setCenter(Model.getInstance().getTeacherViewFactory().getTeacherClassesView());
                case PROFILE -> teacher_parent.setCenter(Model.getInstance().getTeacherViewFactory().getTeacherProfileView());
                default -> teacher_parent.setCenter(Model.getInstance().getTeacherViewFactory().getTeacherDashboardView());
            }
        });

    }
}
