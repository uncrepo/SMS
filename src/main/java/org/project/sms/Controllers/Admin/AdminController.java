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
                case DEPARTMENTS -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getAdminDepartmentsView());
                case TEACHERS -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getAdminTeachersView());
//                case CLASSES -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getClassesView());
                case GRADES -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getAdminGradesView());
                default -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getAdminDashboardView());
            }
        });

//        admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getDashboardView());
    }
}
