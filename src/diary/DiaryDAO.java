package diary;

import diary.DiaryEntry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiaryDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/diary_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";        // 본인 MySQL 계정
    private static final String PASSWORD = "your_password";  // 본인 비밀번호

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void saveEntry(DiaryEntry entry) {
        String sql = "INSERT INTO diary_entries (title, content) VALUES (?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, entry.getTitle());
            pstmt.setString(2, entry.getContent());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DiaryEntry> loadEntries() {
        List<DiaryEntry> entries = new ArrayList<>();
        String sql = "SELECT * FROM diary_entries ORDER BY created_at DESC";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                entries.add(new DiaryEntry(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }

    public void deleteEntry(int id) {
        String sql = "DELETE FROM diary_entries WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DiaryEntry> searchEntries(String keyword) {
        List<DiaryEntry> entries = new ArrayList<>();
        String sql = "SELECT * FROM diary_entries WHERE title LIKE ? OR content LIKE ? ORDER BY created_at DESC";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String pattern = "%" + keyword + "%";
            pstmt.setString(1, pattern);
            pstmt.setString(2, pattern);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    entries.add(new DiaryEntry(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }
}