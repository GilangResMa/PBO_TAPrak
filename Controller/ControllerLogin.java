package Controller;

import Main.Main;
import Model.Model.User.*;
import View.LoginView;
import javax.swing.JOptionPane;

public class ControllerLogin {
    private LoginView view;
    private InterfaceDAOUser dao;
    private Main mainNavigator;

    public ControllerLogin(LoginView view, Main mainNavigator) {
        this.view = view;
        this.mainNavigator = mainNavigator;
        this.dao = new DAOUser();
        this.view.getLoginButton().addActionListener(e -> handleLogin());
        if (this.view.getSignUpButton() != null) { 
            this.view.getSignUpButton().addActionListener(e -> handleOpenSignUpView());
        }
    }
    private void handleLogin() {
        String username = view.getUsername();
        String password = view.getPassword();
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Username dan password tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ModelUser user = dao.validateLogin(username, password);
        if (user != null) {
            SessionManager.setCurrentUser(user);
            JOptionPane.showMessageDialog(view, "Login berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            view.dispose();
            mainNavigator.showMainView();
        } else {
            JOptionPane.showMessageDialog(view, "Username atau password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void handleOpenSignUpView() {
        view.dispose();
        mainNavigator.showSignUpView();
    }
}