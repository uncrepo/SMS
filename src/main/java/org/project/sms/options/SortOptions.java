package org.project.sms.options;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SortOptions {
    public static ObservableList<String> AssignedStudentDetailsFilter = FXCollections.observableArrayList("ID","Name","Email","Phone","Guardian","Year","Grade","Section");
    public static ObservableList<String> EditStudentsFilter = FXCollections.observableArrayList("ID","Name","Email","Phone","Guardian","Gender","Status");
    public static ObservableList<String> AssignedTeachersFilter = FXCollections.observableArrayList("ID","Name","Email","Phone","Guardian","Year","Grade","Section");
    public static ObservableList<String> EditTeachersFilter = FXCollections.observableArrayList("ID","Name","Email","Phone","Guardian","Gender","Status");
    public static ObservableList<String> CommonFilers = FXCollections.observableArrayList("Recent","Oldest");


}
