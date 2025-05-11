package org.project.sms.dao;

import org.project.sms.Models.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CalendarDAO {
    public static List<String> getAllCalendar() {
        List<String> calendars = new ArrayList<>();
        String query = " SELECT calendar FROM calendars";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                calendars.add(
                        rs.getString("calendar")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return calendars;
    }


//    public static String getGradeID(String grade) {
//        String query = "SELECT grade_id FROM where grade = ?";
//        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                grade = rs.getString("grade_id");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return grade;
//    }
}

