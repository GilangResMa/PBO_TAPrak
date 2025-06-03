package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // Driver modern
    private static final String NAMA_DB = "proyek_pbo"; // Pastikan nama DB ini benar
    private static final String URL_DB = "jdbc:mysql://localhost:3306/" + NAMA_DB;
    private static final String USERNAME_DB = "root";
    private static final String PASSWORD_DB = "";

    private static Connection conn;

    public static Connection Connect() {
        try {
            // Tidak perlu Class.forName(JDBC_DRIVER); untuk driver modern jika JAR ada di classpath
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL_DB, USERNAME_DB, PASSWORD_DB);
                // System.out.println("MySQL Connected"); // Opsional untuk debugging
            }
        } catch (SQLException exception) {
            System.err.println("Connection Failed: " + exception.getMessage());
            exception.printStackTrace(); // Penting untuk melihat detail error
            conn = null; // Set ke null jika koneksi gagal
        }
        return conn;
    }
}