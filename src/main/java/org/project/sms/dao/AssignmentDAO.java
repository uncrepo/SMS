package org.project.sms.dao;

import org.project.sms.Models.Model;

import java.sql.*;

public class AssignmentDAO {
    public static void generateAssignmentsForClass(String gradeId, int classId) {
        String fetchCourses = "SELECT grade_course_id FROM grade_course WHERE grade_id = ?";
        String insertAssignment = "INSERT INTO assignment_course (grade_course_id, class_id,semester) VALUES (?, ?, ?)";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement courseStmt = conn.prepareStatement(fetchCourses);
             PreparedStatement insertStmt = conn.prepareStatement(insertAssignment)) {

            // Set the parameters for the Users table
            courseStmt.setString(1, gradeId);
            ResultSet rs = courseStmt.executeQuery();

                if (rs.next()) {
                    int gradeCourseId = rs.getInt("grade_course_id");

                    insertStmt.setInt(1, gradeCourseId);
                    insertStmt.setInt(2, classId);
                    insertStmt.setString(3, "1");
                    insertStmt.executeUpdate();

                    insertStmt.setString(3,"2");
                    insertStmt.executeUpdate();
                } else {
                    throw new SQLException("User ID retrieval failed, no ID obtained.");
                }
            } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    public static void deleteClassAssignments(String classId) {
        String query = "DELETE FROM assignment_course WHERE class_id = ? AND semester = ? ";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {

            stmt.setString(1, classId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void assignTeacherToAssignment(String classId, String gradeCourseId, String teacherId, String semester) {
        String query = "UPDATE assignment_course SET teacher_id = ? WHERE class_id = ? AND grade_course_id = ? AND semester = ? ";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {

            stmt.setString(1, teacherId);
            stmt.setString(2, classId);
            stmt.setString(3, gradeCourseId);
            stmt.setString(4, semester);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void unassignTeacherFromAssignment(String classId, String gradeCourseId, String teacherId, String semester) {
        String query = "UPDATE assignment_course SET teacher_id = NULL WHERE class_id = ? AND grade_course_id = ? AND semester = ? AND teacher_id = ? ";

        try (PreparedStatement stmt = Model.getInstance().getDbConnection().getConnection().prepareStatement(query)) {

            stmt.setString(1, classId);
            stmt.setString(2, gradeCourseId);
            stmt.setString(3, semester);
            stmt.setString(4, teacherId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
