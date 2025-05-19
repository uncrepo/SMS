package org.project.sms.Views;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.project.sms.Controllers.Admin.AdminController;
import org.project.sms.Controllers.Student.StudentController;
import org.project.sms.Controllers.Teacher.TeacherController;
import org.project.sms.options.AccountType;
import org.project.sms.options.AdminMenuOptions;
import javafx.scene.layout.AnchorPane;

public class ViewFactory implements ViewNavigator {
//    private final ObjectProperty<AdminMenuOptions> selectedMenuItem;
    private AccountType loginAccountType;

    public ViewFactory() {
//        this.selectedMenuItem = new SimpleObjectProperty<>();
        this.loginAccountType = AccountType.STUDENT;
    }

//    public ObjectProperty<AdminMenuOptions> getSelectedMenuItem() {
//        return selectedMenuItem;
//    }

    public AccountType getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    @Override
    public void showLoginWindow() {
        createStage("/Fxml/login.fxml", null);
    }


    protected void createStage(String fxmlPath, Object controller) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            if (controller != null) loader.setController(controller);
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            Image icon = new Image(getClass().getResourceAsStream("/images_and_icons/2176597581715247275-128.png"));
            stage.setScene(scene);
            stage.getIcons().add(icon);
            stage.setTitle("School Management System");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected AnchorPane loadView(String path) {
        try {
            return new FXMLLoader(getClass().getResource(path)).load();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

