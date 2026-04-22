package com.nexthire.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;

@Repository
public class UserRepository {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    private void createTableIfNotExists(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS user_results (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cv_text TEXT, " +
                "github_url TEXT, " +
                "target_role TEXT, " +
                "recommendations TEXT, " +
                "created_at TEXT" +
                ")";
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public void saveResult(String cvText, String githubUrl, String targetRole, String recommendations) {
        String sql = "INSERT INTO user_results (cv_text, github_url, target_role, recommendations, created_at) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = DriverManager.getConnection(dbUrl);
            createTableIfNotExists(conn);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cvText);
            pstmt.setString(2, githubUrl);
            pstmt.setString(3, targetRole);
            pstmt.setString(4, recommendations);
            pstmt.setString(5, LocalDateTime.now().toString());
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            System.out.println("Result saved to database successfully.");
        } catch (SQLException e) {
            System.out.println("Error saving result: " + e.getMessage());
        }
    }
}
