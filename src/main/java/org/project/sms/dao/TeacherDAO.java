package org.project.sms.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.project.sms.Models.Model;
import org.project.sms.Models.Teacher;
import org.project.sms.Models.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    private final Connection conn = Model.getInstance().getDbConnection().getConnection();

//    public ResultSet login(String username, String password) {
//        ResultSet rs = null;
//        try {
//            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM teachers WHERE username = ? AND password = ?");
//            stmt.setString(1, username);
//            stmt.setString(2, password);
//            rs = stmt.executeQuery();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return rs;
//    }

    public Teacher getTeacherByUserId(String userId) {
        String query = "SELECT * FROM teachers WHERE user_id = ?";
        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Teacher teacher = new Teacher(
                        rs.getString("teacher_id"),
                        rs.getString("department"),
                        rs.getString("user_id"));
                return teacher;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection connection;

    public TeacherDAO() {
        connection = conn; // Assuming DatabaseDriver handles the DB connection
    }

    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String query = " SELECT \n" +
                "    Users.full_name, " +
                "    Users.email,    " +
                "    Teachers.teacher_id, " +
                "    Teachers.department, " +
                "    Teachers.section, " +
                "    Teachers.year, " +
                "    Courses.course_code " +
                "    FROM Users " +
                "    JOIN Teachers ON Users.user_id = Teachers.user_id " +
                "    JOIN Courses ON Teachers.course_id = Courses.course_id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Teacher teacher = new Teacher(
                        rs.getString("teacher_id"),
                        rs.getString("email"),
                        rs.getString("department"),
                        rs.getString("year"),
                        rs.getString("section"),
                        rs.getString("course_code"),
                        rs.getString("full_name")
                );
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    public ObservableList<Teacher> searchTeachers(String searchText, String department, String section) {
        ObservableList<Teacher> teachers = FXCollections.observableArrayList();
        String query = "SELECT * FROM teachers WHERE name LIKE ? AND department LIKE ? AND section LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + searchText + "%");
            stmt.setString(2, department != null ? department : "%");
            stmt.setString(3, section != null ? section : "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Teacher teacher = new Teacher(
                            rs.getString("teacher_id"),
                            rs.getString("department"),
                            rs.getString("year"),
                            rs.getString("department"),
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

    public void updateTeacher(Teacher teacher) {
        String query = "UPDATE teachers SET department = ? WHERE teacher_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, teacher.getDepartment());
            stmt.setString(2, teacher.getTeacherId().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
