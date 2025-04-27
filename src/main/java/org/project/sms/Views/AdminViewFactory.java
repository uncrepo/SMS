package org.project.sms.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.project.sms.Controllers.Admin.AdminController;


public class AdminViewFactory extends ViewFactory {
//    admin views
    private AnchorPane adminDashboardView;
    private AnchorPane adminTeachersView;
    private AnchorPane adminDepartmentsView;
//    private AnchorPane adminClassesView;
    private AnchorPane adminGradesView;



    public void showAdminWindow() {
        createStage("/Fxml/Admin/admin.fxml", new AdminController());
    }

    public AnchorPane getAdminDashboardView() {
        if(adminDashboardView==null) {
            try {
                adminDashboardView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_dashboard.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminDashboardView;
    }

    public AnchorPane getAdminTeachersView() {
        if(adminTeachersView==null) {
            try {
                adminTeachersView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_teachers.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminTeachersView;
    }

    public AnchorPane getAdminDepartmentsView() {
        if(adminDepartmentsView==null) {
            try {
                adminDepartmentsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_departments.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminDepartmentsView;
    }

//    public AnchorPane getAdminClassesView() {
//        if(adminClassesView==null) {
//            try {
//                adminClassesView = new FXMLLoader(getClass().getResource("/Fxml/admin_departments.fxml")).load();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return adminClassesView;
//    }

    public AnchorPane getAdminGradesView() {
        if(adminGradesView==null) {
            try {
                adminGradesView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_grades.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminGradesView;
    }

}
