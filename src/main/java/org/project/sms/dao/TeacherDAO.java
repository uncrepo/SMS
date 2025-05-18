package org.project.sms.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.project.sms.Models.*;
import org.project.sms.Models.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {

    public Teacher getTeacherByUserId(String userId) {
        String query = "SELECT users.full_name, teachers.email,teachers.teacher_id, teachers.phone, teachers.guardian, teachers.gender, Users.status " +
                " FROM users JOIN Teachers on users.user_id = teachers.user_id WHERE Users.user_id = ? ";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Teacher teacher = new Teacher(
                        rs.getString("teacher_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("guardian"),
                        rs.getString("gender"),
                        rs.getString("status"));
                return teacher;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static  List<Teacher> getAllTeachersDefault() {
        List<Teacher> teachers = new ArrayList<>();
        String query = "  SELECT  " +
                "    Users.full_name, " +
                "    assign_teacher_class.teacher_id," +
                "    Teachers.phone, " +
                "    Teachers.guardian, " +
                "    Teachers.email, " +
                "    Classes.section, " +
                "    Classes.academic_year, " +
                "    Grades.grade, " +
                "    Courses.course_name " +
                "FROM assign_teacher_class " +
                "JOIN Classes ON assign_teacher_class.class_id = Classes.class_id "+
                "JOIN Teachers ON assign_teacher_class.teacher_id = Teachers.teacher_id " +
                "JOIN Users ON teachers.user_id = Users.user_id " +
                "JOIN grade_course ON assign_teacher_class.grade_course_id = grade_course.grade_course_id " +
                "JOIN Courses ON grade_course.course_id = Courses.course_id " +
                "JOIN Grades ON grade_course.grade_id = Grades.grade_id ";

        try (Statement stmt = Model.getInstance().getDbConnection().getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Teacher teacher = new Teacher(
                        rs.getString("teacher_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("guardian"),
                        rs.getString("academic_year"),
                        rs.getString("grade"),
                        rs.getString("course_name"),
                        rs.getString("section")
                );
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }


    public static  ObservableList<AssignedTeacher> getAssignedTeachers() {
        String query = "  SELECT DISTINCT  " +
                "    Users.full_name, " +
                "    assign_teacher_class.assign_teacher_id, " +
                "    Classes.section, " +
                "    Classes.academic_year, " +
                "    Grades.grade, " +
                "    Courses.course_name, " +
                "    Results.semester " +
                "FROM  " +
                "    assign_teacher_class " +
                "JOIN Classes ON assign_teacher_class.class_id = Classes.class_id "+
                "JOIN  " +
                "    Teachers ON assign_teacher_class.teacher_id = Teachers.teacher_id " +
                "JOIN  " +
                "    Users ON teachers.user_id = Users.user_id " +
                "JOIN " +
                "    grade_course ON assign_teacher_class.grade_course_id = grade_course.grade_course_id " +
                "JOIN  " +
                " Courses ON grade_course.course_id = Courses.course_id " +
                "JOIN Grades ON grade_course.grade_id = Grades.grade_id " +
                "JOIN Results ON assign_teacher_class.teacher_id = Results.teacher_id ";
               ;
        ObservableList<AssignedTeacher> teachers = FXCollections.observableArrayList();

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AssignedTeacher teacher = new AssignedTeacher(
                            rs.getString("assign_teacher_id"),
                            rs.getString("full_name"),
                            rs.getString("academic_year"),
                            rs.getString("grade"),
                            rs.getString("course_name"),
                            rs.getString("section"),
                            rs.getString("semester")
                    );
                    teachers.add(teacher);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    public static  ObservableList<Teacher> getAllTeachersForAssign() {
        ObservableList<Teacher> teachers = FXCollections.observableArrayList();
        String query = " SELECT \n" +
                "    Users.full_name, " +
                "    Teachers.teacher_id, " +
                "    Teachers.guardian, " +
                "    Teachers.phone, " +
                "    Teachers.email, " +
                " Teachers.gender,"+
                " Users.status"+
                "    FROM Users " +
                "    JOIN Teachers ON Users.user_id = Teachers.user_id ";

        try (Statement stmt = Model.getInstance().getDbConnection().getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Teacher teacher = new Teacher(
                        rs.getString("teacher_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("guardian"),
                        rs.getString("gender"),
                        rs.getString("status")
                );
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    public static  ObservableList<Teacher> searchTeachersByNameFullView(String searchText, String grade, String section, String academicYear, String course) {
        String query = "  SELECT  " +
                "    Users.full_name, " +
                "    assign_teacher_class.teacher_id," +
                "    Teachers.phone, " +
                "    Teachers.guardian, " +
                "    Teachers.email, " +
                "    Classes.section, " +
                "    Classes.academic_year, " +
                "    Grades.grade, " +
                "    Courses.course_name " +
                "FROM assign_teacher_class " +
                "JOIN Classes ON assign_teacher_class.class_id = Classes.class_id "+
                "JOIN Teachers ON assign_teacher_class.teacher_id = Teachers.teacher_id " +
                "JOIN Users ON teachers.user_id = Users.user_id " +
                "JOIN grade_course ON assign_teacher_class.grade_course_id = grade_course.grade_course_id " +
                "JOIN Courses ON grade_course.course_id = Courses.course_id " +
                "JOIN Grades ON grade_course.grade_id = Grades.grade_id " +
                " WHERE full_name LIKE ? AND grade LIKE ? AND section LIKE ? AND academic_year LIKE ? AND course_name LIKE ? ";
        ;
        ObservableList<Teacher> teachers = FXCollections.observableArrayList();

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, "%" + searchText + "%");
            stmt.setString(2, grade != null ? grade : "%");
            stmt.setString(3, section != null ? section : "%");
            stmt.setString(4, academicYear != null ? academicYear : "%");
            stmt.setString(5, course != null ? course : "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Teacher teacher = new Teacher(
                            rs.getString("teacher_id"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("guardian"),
                            rs.getString("academic_year"),
                            rs.getString("grade"),
                            rs.getString("course_name"),
                            rs.getString("section")
                    );
                    teachers.add(teacher);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }



    public static  ObservableList<AssignedTeacher> searchAssignedTeachersByName(String searchText, String grade, String section, String academicYear, String course) {
        ObservableList<AssignedTeacher> teachers = FXCollections.observableArrayList();
        String query = "  SELECT  " +
                "    Users.full_name, " +
                "    assign_teacher_class.assign_teacher_id, " +
                "    Classes.section, " +
                "    Classes.academic_year, " +
                "    Grades.grade, " +
                "    Courses.course_name " +
                "FROM  " +
                "    assign_teacher_class " +
                "JOIN  " +
                "    Teachers ON assign_teacher_class.teacher_id = Teachers.teacher_id " +
                "JOIN  " +
                "    Users ON teachers.user_id = Users.user_id " +
                "JOIN " +
                "    grade_course ON assign_teacher_class.grade_course_id = grade_course.grade_course_id " +
                "JOIN  " +
                " Courses ON grade_course.course_id = Courses.course_id " +
                "JOIN  " +
                " Grades ON grade_course.grade_id = Grades.grade_id " +
                "JOIN Classes ON assign_teacher_class.class_id = Classes.class_id " +
                " WHERE full_name LIKE ? AND grade LIKE ? AND section LIKE ? AND academic_year LIKE ? AND course_name LIKE ? ";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, "%" + searchText + "%");
            stmt.setString(2, grade != null ? grade : "%");
            stmt.setString(3, section != null ? section : "%");
            stmt.setString(4, academicYear != null ? academicYear : "%");
            stmt.setString(5, course != null ? course : "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AssignedTeacher teacher = new AssignedTeacher(
                            rs.getString("assign_teacher_id"),
                            rs.getString("full_name"),
                            rs.getString("academic_year"),
                            rs.getString("grade"),
                            rs.getString("course_name"),
                            rs.getString("section")
                    );
                    teachers.add(teacher);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }


    public static  ObservableList<Teacher> searchTeachersByName(String searchText, String grade, String section, String academicYear, String course) {
        ObservableList<Teacher> teachers = FXCollections.observableArrayList();
        String query = "SELECT " +
                "                   Users.full_name, " +
                "                   Users.email,  " +
                "                   Users.phone, " +
                "                   Users.guardian, " +
                "                   Teachers.teacher_id, " +
                "                   Teachers.academic_year, " +
                "                   Teachers.section, " +
                "                   Grades.grade, " +
                "                   Courses.course_name " +
                "                 FROM Users  " +
                "                 JOIN Teachers ON Users.user_id = Teachers.user_id " +
                "                 JOIN Grades ON Grades.grade_id = Teachers.grade_id " +
                "                 JOIN Courses ON Teachers.course_id = Courses.course_id " +
                "                 WHERE full_name LIKE ? AND grade LIKE ? AND section LIKE ? AND academic_year LIKE ? AND course_name LIKE ? ";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, "%" + searchText + "%");
            stmt.setString(2, grade != null ? grade : "%");
            stmt.setString(3, section != null ? section : "%");
            stmt.setString(4, academicYear != null ? academicYear : "%");
            stmt.setString(5, course != null ? course : "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Teacher teacher = new Teacher(
                            rs.getString("teacher_id"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("guardian"),
                            rs.getString("academic_year"),
                            rs.getString("grade"),
                            rs.getString("course_name"),
                            rs.getString("section")
                    );
                    teachers.add(teacher);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    public static ObservableList<Teacher> searchTeachersByName(String searchText) {
        ObservableList<Teacher> teachers = FXCollections.observableArrayList();
        String query = " SELECT" +
                " Users.full_name, " +
                " Teachers.teacher_id, " +
                " Teachers.guardian, " +
                " Teachers.phone, " +
                " Teachers.email, " +
                " Teachers.gender, " +
                " Users.status " +
                " FROM Users " +
                " JOIN Teachers ON Users.user_id = Teachers.user_id " +
                "WHERE full_name LIKE ?";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, "%" + searchText + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Teacher teacher = new Teacher(
                            rs.getString("teacher_id"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("gender"),
                            rs.getString("phone"),
                            rs.getString("guardian"),
                            rs.getString("status")
                    );
                    teachers.add(teacher);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }


    public static  void assignTeacherToClass(String teacherId, String gradeCourseId ,String classId) {
        String query = "INSERT INTO assign_teacher_class(teacher_id,grade_course_id,class_id) " +
                "VALUES (?,?,?)";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, teacherId);
            stmt.setString(2, gradeCourseId);
            stmt.setString(3, classId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


//    public static  void deleteTeacher(String id) {
//
//        String query = "DELETE FROM teachers WHERE teacher_id = ? ";
//
//        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
//            stmt.setString(1, id);
//
//            stmt.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public static void updateTeacher(Teacher teacher) {
        String query = "UPDATE Teachers " +
                "JOIN Users ON Teachers.user_id = Users.user_id " +
                "SET Users.full_name = ?, " +
                "Teachers.email = ?, " +
                "Teachers.phone = ?, " +
                "Teachers.guardian = ?, " +
                "Teachers.gender = ?, " +
                "Users.status = ? " +
                "WHERE Teachers.teacher_id = ?";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, teacher.getFullName());
            stmt.setString(2, teacher.getEmail());
            stmt.setString(3, teacher.getPhone());
            stmt.setString(4, teacher.getGuardian());
            stmt.setString(5, teacher.getGender());
            stmt.setString(6, teacher.getStatus());

            stmt.setString(7, teacher.getTeacherId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update teacher: " + e.getMessage());
        }
    }

    public static void deleteTeacher(String teacherId) {
        String query = "DELETE Teachers, Users " +
                "FROM Teachers " +
                "JOIN Users ON Teachers.user_id = Users.user_id " +
                "WHERE Teachers.teacher_id = ?";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, teacherId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete teacher: " + e.getMessage());
        }
    }

    public List<Teacher> searchTeachers(String searchText) {
        List<Teacher> teachers = new ArrayList<>();
        String query = "SELECT " +
                "Users.full_name, " +
                "Users.email, " +
                "Users.phone, " +
                "Users.guardian, " +
                "Teachers.teacher_id, " +
                "Teachers.academic_year, " +
                "Teachers.section, " +
                "Grades.grade, " +
                "Courses.course_name " +
                "FROM Users " +
                "JOIN Teachers ON Users.user_id = Teachers.user_id " +
                "JOIN Grades ON Grades.grade_id = Teachers.grade_id " +
                "JOIN Courses ON Teachers.course_id = Courses.course_id " +
                "WHERE Users.full_name LIKE ? OR Teachers.teacher_id LIKE ? OR Users.email LIKE ?";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            String searchPattern = "%" + searchText + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Teacher teacher = new Teacher(
                        rs.getString("teacher_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("guardian"),
                        rs.getString("academic_year"),
                        rs.getString("grade"),
                        rs.getString("course_name"),
                        rs.getString("section")
                );
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    public static  void updateTeacher(String id, String email, String phone, String guardian) {
        String query = "UPDATE teachers SET guardian = ?, phone = ?, email = ? WHERE teacher_id = ?";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, guardian);
            stmt.setString(2, phone);
            stmt.setString(3, email);
            stmt.setString(4, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static  void unassignTeacher(String assignId) {
        String query = "DELETE FROM assign_teacher_class WHERE assign_teacher_id = ? ";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, assignId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static  void updateAssignedTeacher(String teacherId, String gradeCourseId, String classId) {
        String query = "UPDATE assign_teacher_class SET grade_course_id = ? , class_id = ? WHERE assign_teacher_id = ? ";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, gradeCourseId);
                stmt.setString(2, classId);
            stmt.setString(3, teacherId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkAssignedTeacherExist(String assignedTeacherId) {
        String query = "SELECT teacher_id FROM assign_teacher_class WHERE assign_teacher_id = ? ";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, assignedTeacherId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public  static boolean checkAssignedTeacherExist(String gradeCourseId, String classId) {
        String query = "SELECT teacher_id FROM assign_teacher_class WHERE grade_course_id = ? AND class_id = ?";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, gradeCourseId);
            stmt.setString(2, classId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addTeacher(Teacher newTeacher) {
        String userQuery = "INSERT INTO Users (full_name, username, password, role) VALUES (?, ?, ?, 'TEACHER')";
        String teacherQuery = "INSERT INTO Teachers (user_id, phone, guardian, email, gender) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement userStmt = conn.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement teacherStmt = conn.prepareStatement(teacherQuery)) {

            // Set the parameters for the Users table
            userStmt.setString(1, newTeacher.getFullName());
            userStmt.setString(2, newTeacher.getUsername());
            userStmt.setString(3, newTeacher.getPassword());

            // Execute the Users insert query
            int rowsAffected = userStmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            // Retrieve the generated user_id from the Users table
            try (ResultSet generatedKeys = userStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);

                    // Now, set the parameters for the Students table using the retrieved user_id
                    teacherStmt.setInt(1, userId);
                    teacherStmt.setString(2, newTeacher.getPhone());
                    teacherStmt.setString(3, newTeacher.getGuardian());
                    teacherStmt.setString(4, newTeacher.getEmail());
                    teacherStmt.setString(5, newTeacher.getGender());

                    // Execute the Students insert query
                    teacherStmt.executeUpdate();
                } else {
                    throw new SQLException("User ID retrieval failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void assignTeacherToResults(String classId, String gradeCourseId ,String teacherId, String semester)  {
        String updateQuery = "UPDATE Results SET teacher_id = ? " +
                "WHERE class_id = ? AND grade_course_id = ? AND semester = ? AND teacher_id IS NULL";

        try (PreparedStatement ps = Model.getInstance().getDbConnection().getConnection().prepareStatement(updateQuery)) {
            ps.setString(1, teacherId);
            ps.setString(2, classId);
            ps.setString(3, gradeCourseId);
            ps.setString(4, semester);

            int updatedRows = ps.executeUpdate();
            System.out.println("Assigned teacher " + teacherId + " to " + updatedRows + " result in class " + classId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public static void removeTeacherFromResults(String classId, String gradeCourseId, String teacherId, String semester) {
        String updateQuery = "UPDATE Results SET teacher_id = NULL " +
                "WHERE class_id = ? AND grade_course_id = ? AND semester = ? AND teacher_id = ?";

        try (PreparedStatement ps = Model.getInstance().getDbConnection().getConnection().prepareStatement(updateQuery)) {
            ps.setString(1, classId);
            ps.setString(2, gradeCourseId);
            ps.setString(3, semester);
            ps.setString(4, teacherId);
            int updatedRows = ps.executeUpdate();
            System.out.println("Removed teacher " + teacherId + " from " + updatedRows + " result in class " + classId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getTeacherIdByAssignedId(String assignedStudentId) {
        String teacherId = null;
        String query = "SELECT teacher_id FROM assign_teacher_class WHERE assign_teacher_id = ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, assignedStudentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                teacherId = rs.getString("teacher_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacherId;
    }


}
