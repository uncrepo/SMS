package org.project.sms.Controllers.Teacher;


import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.project.sms.Models.Model;
import org.project.sms.options.AccountType;


import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class TeacherController implements Initializable {
    public BorderPane teacher_parent;
    public Label greetText;
    public Label loggedInText;
    public Label roleText;

    private final String name = Model.getInstance().getCurrentTeacher().getFullName();
    private final String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    private final AccountType role = Model.getInstance().getCurrentUserRole().getValue();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MenuOptions();
        initValues();
    }

    private void initValues() {
        greetText.setText("Hello "+name+", Welcome back");
        roleText.setText("CURRENT ROLE : " + role);
        loggedInText.setText("last Logged in : " + time);
    }

    private void MenuOptions() {
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
