package schreminder.controller;

import schreminder.model.*;
import schreminder.view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginController {
    private LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;

        loginView.addLoginListener(new LoginAction());
        loginView.setVisible(true);
    }

    class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.usernameField.getText();
            String password = new String(loginView.passwordField.getPassword());

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyek_pbo", "root", "")) {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    loginView.dispose();
                    TodoModel model = new TodoModel();
                    TodoView view = new TodoView();
                    new TodoController(model, view);
                } else {
                    loginView.showError("Username atau password salah!");
                }
            } catch (SQLException ex) {
                loginView.showError("Gagal koneksi ke database!");
            }
        }
    }
}
