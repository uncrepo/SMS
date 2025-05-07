package org.project.sms.dao;

import org.project.sms.Models.Course;
import org.project.sms.Models.Grade;
import org.project.sms.Models.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {
    public static List<Grade> getAllGrades() {
        List<Grade> grades = new ArrayList<>();
        String query = " SELECT grade FROM grades";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                grades.add(new Grade(
                        rs.getString("grade")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    public static String getGradeID(String grade) {
        String query = "SELECT grade_id FROM where grade = ?";
        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                grade = rs.getString("grade_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grade;
    }
}

