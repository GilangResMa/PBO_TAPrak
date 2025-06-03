package View;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signUpButton;

    public LoginView() {
        setTitle("Login Aplikasi Tugas");
        setSize(400, 250); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        loginButton = new JButton("Login");
        signUpButton = new JButton("Daftar Akun");
        buttonPanel.add(loginButton);
        buttonPanel.add(signUpButton);
        panel.add(buttonPanel, gbc);
        add(panel);
    }
    public String getUsername() { return usernameField.getText(); }
    public String getPassword() { return new String(passwordField.getPassword()); }
    public JButton getLoginButton() { return loginButton; }
    public JButton getSignUpButton() { return signUpButton; }
}