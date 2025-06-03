package View;

import javax.swing.*;
import java.awt.*;

public class SignUpView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton signUpButton;
    private JButton backToLoginButton;

    public SignUpView() {
        setTitle("Daftar Akun Baru");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
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
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Konfirmasi Password:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        confirmPasswordField = new JPasswordField(15);
        panel.add(confirmPasswordField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        signUpButton = new JButton("Daftar");
        backToLoginButton = new JButton("Kembali ke Login");
        buttonPanel.add(signUpButton);
        buttonPanel.add(backToLoginButton);
        panel.add(buttonPanel, gbc);
        add(panel);
    }
    public String getUsername() { return usernameField.getText(); }
    public String getPassword() { return new String(passwordField.getPassword()); }
    public String getConfirmPassword() { return new String(confirmPasswordField.getPassword()); }
    public JButton getSignUpButton() { return signUpButton; }
    public JButton getBackToLoginButton() { return backToLoginButton; }
}