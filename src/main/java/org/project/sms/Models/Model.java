package org.project.sms.Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.project.sms.Views.AdminViewFactory;
import org.project.sms.Views.StudentViewFactory;
import org.project.sms.Views.TeacherViewFactory;
import org.project.sms.Views.ViewFactory;
import org.project.sms.config.DBConnection;
import org.project.sms.options.AdminMenuOptions;
import org.project.sms.options.StudentMenuOptions;
import org.project.sms.options.TeacherMenuOptions;
import org.project.sms.options.AccountType;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;

    private Model() {
        this.viewFactory=new ViewFactory();
    }

    public static synchronized Model getInstance() {
        if (model==null) {
            model=new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    // Currently logged-in user role
    private final ObjectProperty<AccountType> currentUserRole = new SimpleObjectProperty<>();

    // Navigation state
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenu = new SimpleObjectProperty<>(AdminMenuOptions.DASHBOARD);
    private final ObjectProperty<TeacherMenuOptions> teacherSelectedMenu = new SimpleObjectProperty<>(TeacherMenuOptions.DASHBOARD);
    private final ObjectProperty<StudentMenuOptions> studentSelectedMenu = new SimpleObjectProperty<>(StudentMenuOptions.CURRENT_SEMESTER);

    // Role-specific factories
    private final AdminViewFactory adminViewFactory = new AdminViewFactory();
    private final TeacherViewFactory teacherViewFactory = new TeacherViewFactory();
    private final StudentViewFactory studentViewFactory = new StudentViewFactory();

    public ObjectProperty<AccountType> getCurrentUserRole() { return currentUserRole; }

    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenu() { return adminSelectedMenu; }
    public ObjectProperty<TeacherMenuOptions> getTeacherSelectedMenu() { return teacherSelectedMenu; }
    public ObjectProperty<StudentMenuOptions> getStudentSelectedMenu() { return studentSelectedMenu; }

    public AdminViewFactory getAdminViewFactory() { return adminViewFactory; }
    public TeacherViewFactory getTeacherViewFactory() { return teacherViewFactory; }
    public StudentViewFactory getStudentViewFactory() { return studentViewFactory; }


    public DBConnection getDbConnection() {
        return new DBConnection();
    }


    private Admin currentAdmin;
    private Student currentStudent;
    private Teacher currentTeacher;


    public void setCurrentAdmin(Admin admin) {
        this.currentAdmin = admin;
    }

    public Admin getCurrentAdmin() {
        return currentAdmin;
    }


    public void setCurrentTeacher(Teacher teacher) {
        this.currentTeacher = teacher;
    }

    public Teacher getCurrentTeacher() {
        return currentTeacher;
    }


    public void setCurrentStudent(Student student) {
        this.currentStudent = student;
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public String getCurrentAcademicYear() {
        return "2024/25";
    }
    public String getCurrentSemester() {
        return "1";
    }
}


