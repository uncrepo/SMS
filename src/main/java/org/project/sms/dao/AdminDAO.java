package org.project.sms.dao;

import org.project.sms.Models.Admin;
import org.project.sms.Models.Model;
import org.project.sms.Models.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDAO {
    private final Connection conn = Model.getInstance().getDbConnection().getConnection();

    public Admin getAdminByUserId(String userId) {
        String query = "SELECT * FROM Users WHERE user_id = ?";
        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getString("user_id"));
                admin.setUserId(rs.getString("email"));
                admin.setFullName(rs.getString("full_name"));
//                admin.setEmail(rs.getString("email"));
//                admin.setClassId(rs.getInt("class_id"));
                return admin;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
