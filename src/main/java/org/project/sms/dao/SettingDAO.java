package org.project.sms.dao;

import org.project.sms.Models.Model;

import java.sql.*;
import java.time.LocalDate;

public class SettingDAO {

    private static final String TABLE_NAME = "system_settings";

    // Get the value of a setting as string
    private static String getSettingValue(String key) {
        String sql = "SELECT setting_value FROM " + TABLE_NAME + " WHERE setting_key = ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, key);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("setting_value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Public static methods to get specific settings

    public static String getCurrentAcademicYear() {
        return getSettingValue("academic_year");
    }

    public static int getCurrentSemester() {
        String value = getSettingValue("semester");
        return value != null ? Integer.parseInt(value) : 0;
    }

    public static String getDefaultPassword() {
        return getSettingValue("default_password");
    }

    public static LocalDate getCalendarStart() {
        String value = getSettingValue("calendar_start");
        return value != null ? LocalDate.parse(value) : null;
    }

    public static LocalDate getCalendarEnd() {
        String value = getSettingValue("calendar_end");
        return value != null ? LocalDate.parse(value) : null;
    }

    public static boolean isMaintenanceMode() {
        String value = getSettingValue("maintenance_mode");
        return value != null && value.equalsIgnoreCase("true");
    }


    // Optional: Update a setting
    public static void updateSetting(String key, String value) {
        String sql = "UPDATE " + TABLE_NAME + " SET setting_value = ? WHERE setting_key = ?";

        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, value);
            stmt.setString(2, key);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ADMIN UPDATE FUNCTIONS
    public static void setCurrentAcademicYear(String year) {
        updateSetting("academic_year", year);
    }

    public static void setCurrentSemester(int semester) {
        updateSetting("semester", String.valueOf(semester));
    }

    public static void setDefaultPassword(String password) {
        updateSetting("default_password", password);
    }

    public static void setCalendarStart(LocalDate date) {
        updateSetting("calendar_start", date.toString());
    }

    public static void setCalendarEnd(LocalDate date) {
        updateSetting("calendar_end", date.toString());
    }

    public static void setMaintenanceMode(boolean enabled) {
        updateSetting("maintenance_mode", String.valueOf(enabled));
    }

    public static void setGradeScale(double scale) {
        updateSetting("grade_scale", String.valueOf(scale));
    }

}