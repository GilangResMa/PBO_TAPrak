package Model.Model.Tugas;

import Model.Connector;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.time.LocalDate;

public class DAOTugas implements InterfaceDAOTugas {
    @Override
    public List<ModelTugas> getAllTugasByUser(int userId) {
        List<ModelTugas> list = new ArrayList<>();
        String query = "SELECT * FROM tugas WHERE user_id = ?";
        try (Connection c = Connector.Connect(); 
             PreparedStatement ps = c.prepareStatement(query)) {
            if (c == null) return list; // Kembalikan list kosong jika koneksi gagal
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ModelTugas(
                    rs.getInt("id_tugas"), 
                    rs.getString("judul"),
                    rs.getString("deskirpsi"), // Pastikan nama kolom "deskirpsi" ini sesuai dengan DB
                    rs.getDate("due_date") != null ? rs.getDate("due_date").toLocalDate() : null,
                    rs.getBoolean("is_done"), 
                    rs.getInt("user_id")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    @Override
    public boolean addTugas(ModelTugas t) {
        String query = "INSERT INTO tugas (judul, deskirpsi, due_date, is_done, user_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = Connector.Connect(); 
             PreparedStatement ps = c.prepareStatement(query)) {
            if (c == null) return false;
            ps.setString(1, t.getJudul()); 
            ps.setString(2, t.getDeskripsi()); 
            if (t.getDueDate() != null) {
                ps.setDate(3, Date.valueOf(t.getDueDate()));
            } else {
                ps.setNull(3, Types.DATE);
            }
            ps.setBoolean(4, t.isIsDone());
            ps.setInt(5, t.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
    @Override
    public boolean updateTugas(ModelTugas t) {
        String query = "UPDATE tugas SET judul = ?, deskirpsi = ?, due_date = ?, is_done = ? WHERE id_tugas = ?";
        try (Connection c = Connector.Connect(); 
             PreparedStatement ps = c.prepareStatement(query)) {
            if (c == null) return false;
            ps.setString(1, t.getJudul()); 
            ps.setString(2, t.getDeskripsi());
             if (t.getDueDate() != null) {
                ps.setDate(3, Date.valueOf(t.getDueDate()));
            } else {
                ps.setNull(3, Types.DATE);
            }
            ps.setBoolean(4, t.isIsDone());
            ps.setInt(5, t.getIdTugas());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
    @Override
    public boolean deleteTugas(int id) {
        String query = "DELETE FROM tugas WHERE id_tugas = ?";
        try (Connection c = Connector.Connect(); 
             PreparedStatement ps = c.prepareStatement(query)) {
            if (c == null) return false;
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}