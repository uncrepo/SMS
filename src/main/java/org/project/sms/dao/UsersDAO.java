package org.project.sms.dao;

import org.project.sms.Models.Model;
import org.project.sms.Models.User;
import org.project.sms.options.AccountType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDAO {

    private final Connection conn = Model.getInstance().getDbConnection().getConnection();

    public User login(String username, String password) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
                return new User(
                    rs.getString("user_id"),
                    rs.getString("full_name"),
                    AccountType.valueOf(rs.getString("role"))
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

