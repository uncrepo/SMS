package org.project.sms.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import org.project.sms.Controllers.Teacher.TeacherCellController;
import org.project.sms.Models.Teacher;

public class TeacherCellFactory extends ListCell<Teacher> {
    @Override
    protected void updateItem(Teacher teacher, boolean empty) {
        super.updateItem(teacher, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Teacher/teacher_cell.fxml"));
            TeacherCellController controller = new TeacherCellController(teacher);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
