package Model.Model.User;

import Model.Connector;
import java.sql.*;

public class DAOUser implements InterfaceDAOUser {
    @Override
    public ModelUser validateLogin(String username, String password) {
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (Connection conn = Connector.Connect(); 
             PreparedStatement ps = conn.prepareStatement(query)) {
            if (conn == null) return null; // Jika koneksi gagal
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new ModelUser(rs.getInt("user_id"), rs.getString("username"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
    @Override
    public boolean addUser(String username, String password) {
        if (isUsernameExists(username)) {
             System.out.println("DAOUser: Username '" + username + "' sudah terdaftar.");
            return false;
        }
        String query = "INSERT INTO user (username, password) VALUES (?, ?)";
        try (Connection conn = Connector.Connect(); 
             PreparedStatement ps = conn.prepareStatement(query)) {
            if (conn == null) return false;
            ps.setString(1, username);
            ps.setString(2, password); // Ingat untuk HASH password di aplikasi nyata!
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
    private boolean isUsernameExists(String username) {
        String query = "SELECT COUNT(*) FROM user WHERE username = ?";
        try (Connection conn = Connector.Connect(); 
             PreparedStatement ps = conn.prepareStatement(query)) {
            if (conn == null) return true; // Asumsikan ada jika koneksi gagal, untuk mencegah
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false; // Default ke false jika ada error lain, atau true jika ingin lebih aman
    }
}