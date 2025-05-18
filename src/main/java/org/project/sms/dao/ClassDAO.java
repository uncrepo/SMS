package org.project.sms.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.project.sms.Models.Class;
import org.project.sms.Models.Model;
import org.project.sms.Models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassDAO {

    public static String getClassID(String grade,String section, String academicYear) {
        String classId = null;
        String query = "SELECT class_id FROM Classes where grade_id = ? AND section = ? AND academic_year = ?";

        String gradeId = GradeDAO.getGradeID(grade);
        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, gradeId);
            stmt.setString(2, section);
            stmt.setString(3, academicYear);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                classId = rs.getString("class_id");
                return classId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classId;
    }


    public static void addClass(String grade, String section, String academicYear) {
        String classQuery = "INSERT INTO Classes (grade_id, section, academic_year) VALUES (?, ?, ?)";

        String gradeId = GradeDAO.getGradeID(grade);
        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement classStmt = conn.prepareStatement(classQuery)) {

            // Set the parameters for the Users table
            classStmt.setString(1, gradeId);
            classStmt.setString(2, section);
            classStmt.setString(3, academicYear);

            int rowsAffected = classStmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            // Retrieve the generated user_id from the Users table
            try (ResultSet generatedKeys = classStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int classId = generatedKeys.getInt(1);
                    AssignmentDAO.generateAssignmentsForClass(gradeId,classId);
                } else {
                    throw new SQLException("User ID retrieval failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static List<Class> searchClassesByYearGradeSection(String grade, String year, String section) {
        List<Class> classes = new ArrayList<>();

        String query = " SELECT " +
                "    Classes.class_id," +
                "    Classes.academic_year," +
                "    Grades.grade," +
                "    Classes.section" +
                "FROM  " +
                "    Classes " +
                "JOIN Grades ON Classes.grade_id = Grades.grade_id " +
                "WHERE grade_id = ? AND academic_year = ? AND section = ?";
        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String gradeId = GradeDAO.getGradeID(grade);
            stmt.setString(1, gradeId);
            stmt.setString(2, year);
            stmt.setString(3, section);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                classes.add(new Class(
                        rs.getString("class_id"),
                        rs.getString("academic_year"),
                        rs.getString("grade"),
                        rs.getString("section")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }

    public static void generateAssignmentsForClass(int classId, int gradeId) {
        String fetchCourses = "SELECT grade_course_id FROM grade_course WHERE grade_id = ?";
        String insertAssignment = "INSERT INTO assignment_course (grade_course_id, class_id) VALUES (?, ?)";

        try (PreparedStatement courseStmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(fetchCourses);
             PreparedStatement insertStmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(insertAssignment)) {

            courseStmt.setInt(1, gradeId);
            ResultSet rs = courseStmt.executeQuery();

            while (rs.next()) {
                int gradeCourseId = rs.getInt("grade_course_id");
                insertStmt.setInt(1, gradeCourseId);
                insertStmt.setInt(2, classId);
                insertStmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static List<Class> getAllClassesDefault() {
        List<Class> classes = new ArrayList<>();
        String query = "  SELECT Grades.grade," +
                " Classes.class_id," +
                " Classes.section," +
                " Classes.academic_year " +
                "FROM Classes " +
                " JOIN Grades ON classes.grade_id =  grades.grade_id";

        try (Statement stmt = Model.getInstance().getDbConnection().getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Class singleClass = new Class(
                        rs.getString("class_id"),
                        rs.getString("academic_year"),
                        rs.getString("grade"),
                        rs.getString("section")
                );
                classes.add(singleClass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }


    public static void updateClass(String classId, String section, String academicYear, String grade) {
        String query = "UPDATE classes SET section = ?, academic_year = ?, grade_id = ? WHERE class_id = ?";

        String gradeId = GradeDAO.getGradeID(grade);
        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {
            stmt.setString(1, section);
            stmt.setString(2, academicYear);
            stmt.setString(3, gradeId);
            stmt.setString(4, classId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteClass(String classId) {
        String query = "DELETE FROM classes WHERE class_id = ? ";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {

            AssignmentDAO.deleteClassAssignments(classId);
            stmt.setString(1, classId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}

