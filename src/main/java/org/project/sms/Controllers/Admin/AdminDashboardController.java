package org.project.sms.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import org.project.sms.Models.Model;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {
    public Label totalStudentsLabel;
    public Label totalTeachersLabel;
    public Label totalClassesLabel;
    public Label pendingApprovalsLabel;


    public void initialize(URL location, ResourceBundle resources) {
        loadDashboardStats();
    }

    private void loadDashboardStats() {
        setTotalStudents();
        setTotalTeachers();
        setTotalClasses();
        setPendingApprovals();
    }

    private void setTotalStudents() {
        String query = "SELECT COUNT(*) FROM students";
        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                totalStudentsLabel.setText(String.valueOf(rs.getInt(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            totalStudentsLabel.setText("Error");
        }
    }

    private void setTotalTeachers() {
        String query = "SELECT COUNT(*) FROM teachers";
        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                totalTeachersLabel.setText(String.valueOf(rs.getInt(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            totalTeachersLabel.setText("Error");
        }
    }

    private void setTotalClasses() {
        String query = "SELECT COUNT(*) FROM classes";
        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                totalClassesLabel.setText(String.valueOf(rs.getInt(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            totalClassesLabel.setText("Error");
        }
    }

    private void setPendingApprovals() {
        String query = "SELECT COUNT(*) FROM users WHERE status = 'PENDING'";
        try (Connection conn = Model.getInstance().getDbConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                pendingApprovalsLabel.setText(String.valueOf(rs.getInt(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            pendingApprovalsLabel.setText("Error");
        }
    }
}
