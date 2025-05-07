package org.project.sms.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import org.project.sms.Controllers.Admin.AdminController;


public class AdminViewFactory extends ViewFactory {
    //    admin views
    private AnchorPane adminDashboardView;


    private AnchorPane adminStudentsView;
    private AnchorPane adminAddStudentView;
    private AnchorPane adminStudentStatsView;
    private AnchorPane adminEditStudentView;



    private AnchorPane adminTeachersView;
    private AnchorPane adminEditTeacherView;
    private AnchorPane adminAssignTeacherView;
    private AnchorPane adminAddTeacherView;


    private AnchorPane adminClassesView;
    private AnchorPane adminAddClassView;
    private AnchorPane adminEditClassView;
    private AnchorPane adminClassScheduleView;
    private AnchorPane adminAssignStudentsView;
    private AnchorPane adminPreviousClassView;

    private AnchorPane adminProfileView;
    private AnchorPane adminLogsView;



    public void showAdminWindow() {
        createStage("/Fxml/Admin/admin.fxml", new AdminController());
    }

    public AnchorPane getAdminDashboardView() {
        if (adminDashboardView == null) {
            try {
                adminDashboardView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_dashboard.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminDashboardView;
    }

    public AnchorPane getAdminTeachersView() {
        if (adminTeachersView == null) {
            try {
                adminTeachersView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_teachers.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminTeachersView;
    }

    public AnchorPane getAdminClassesView() {
        if (adminClassesView == null) {
            try {
                adminClassesView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_classes.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminClassesView;
    }


    public AnchorPane getAdminStudentsView() {
        if (adminStudentsView == null) {
            try {
                adminStudentsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_students.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminStudentsView;
    }

    public AnchorPane getAdminStudentStatsView() {
        if (adminStudentStatsView == null) {
            try {
                adminStudentStatsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_student_stats.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminStudentStatsView;
    }

    public AnchorPane getAdminAddTeacherView() {
        if (adminAddTeacherView == null) {
            try {
                adminAddTeacherView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_new_teacher.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminAddTeacherView;
    }

    public AnchorPane getAdminAssignTeacherView() {
        if (adminAssignTeacherView == null) {
            try {
                adminAssignTeacherView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_assign_teacher.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminAssignTeacherView;
    }

    public AnchorPane getAdminEditTeacherView() {
        if (adminEditTeacherView == null) {
            try {
                adminEditTeacherView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_edit_teacher.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminEditTeacherView;
    }

    public AnchorPane getAdminAssignStudentView() {
        if (adminAssignStudentsView == null) {
            try {
                adminAssignStudentsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_assign_students.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminAssignStudentsView;
    }

    public AnchorPane getAdminClassScheduleView() {
        if (adminClassScheduleView == null) {
            try {
                adminClassScheduleView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_class_schedule.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminClassScheduleView;
    }

    public AnchorPane getAdminAddClassView() {
        if (adminAddClassView == null) {
            try {
                adminAddClassView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_new_class.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminAddClassView;
    }

    public AnchorPane getAdminEditClassView() {
        if (adminEditClassView == null) {
            try {
                adminEditClassView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_edit_class.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminEditClassView;
    }

    public AnchorPane getAdminPreviousClassResultsView() {
        if (adminPreviousClassView == null) {
            try {
                adminPreviousClassView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_class_result.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminPreviousClassView;
    }

    public AnchorPane getAdminProfileView() {
        if (adminProfileView == null) {
            try {
                adminProfileView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_profile.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminProfileView;
    }

    public AnchorPane getAdminLogsView() {
        if (adminLogsView == null) {
            try {
                adminLogsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_logs.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminLogsView;
    }

    public AnchorPane getAdminAddStudentView() {
        if (adminAddStudentView == null) {
            try {
                adminAddStudentView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_new_student.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminAddStudentView;
    }

    public AnchorPane getAdminEditStudentView() {
        if (adminEditStudentView == null) {
            try {
                adminEditStudentView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin_edit_student.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminEditStudentView;
    }
}