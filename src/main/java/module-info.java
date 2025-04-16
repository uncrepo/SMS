module org.project.sms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;


    opens org.project.sms to javafx.fxml;
    exports org.project.sms;
    exports org.project.sms.Controllers;
    exports org.project.sms.Controllers.Admin;
    exports org.project.sms.Controllers.Teacher;
    exports org.project.sms.Controllers.Student;
    exports org.project.sms.Models;
    exports org.project.sms.Views;
}