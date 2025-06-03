package Controller;

import Main.Main;
import Model.Model.User.*;
import View.SignUpView;
import javax.swing.JOptionPane;

public class ControllerSignUp {
    private final SignUpView view;
    private final InterfaceDAOUser dao;
    private final Main mainNavigator;

    public ControllerSignUp(SignUpView view, Main mainNavigator) {
        this.view = view;
        this.mainNavigator = mainNavigator;
        this.dao = new DAOUser();
        this.view.getSignUpButton().addActionListener(e -> handleSignUp());
        this.view.getBackToLoginButton().addActionListener(e -> handleBackToLogin());
    }
    private void handleSignUp() {
        String username = view.getUsername();
        String password = view.getPassword();
        String confirmPassword = view.getConfirmPassword();
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(view, "Password dan konfirmasi tidak cocok!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean success = dao.addUser(username, password);
        if (success) {
            JOptionPane.showMessageDialog(view, "Akun berhasil dibuat! Silakan login.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            handleBackToLogin();
        } else {
            JOptionPane.showMessageDialog(view, "Username sudah terdaftar atau terjadi kesalahan.", "Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void handleBackToLogin() {
        view.dispose();
        mainNavigator.showLoginView();
    }
}