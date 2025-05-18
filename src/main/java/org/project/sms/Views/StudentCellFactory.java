package org.project.sms.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import org.project.sms.Models.Student;

public class StudentCellFactory extends ListCell<Student> {
//    @Override
//    protected void updateItem(Student student, boolean empty) {
//        super.updateItem(student, empty);
//        if (empty) {
//            setText(null);
//            setGraphic(null);
//        } else {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Student/student_cell.fxml"));
//            StudentCellController controller = new StudentCellController(student);
//            loader.setController(controller);
//            setText(null);
//            try {
//                setGraphic(loader.load());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
