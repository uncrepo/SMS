package org.project.sms;


import javafx.application.Application;
import javafx.stage.Stage;
import org.project.sms.Models.Model;


public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Model.getInstance().getViewFactory().showLoginWindow();
    }

}
