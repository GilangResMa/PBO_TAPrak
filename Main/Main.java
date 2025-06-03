/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main; // Sesuaikan dengan package Anda

import Controller.ControllerLogin;
import Controller.ControllerSignUp;
import Controller.ControllerTugas;
import View.LoginView;
import View.SignUpView;
import View.View.Tugas.ViewDataView;

import javax.swing.*;
import java.awt.*; // Import untuk Font dan Color

public class Main {

    public static void main(String[] args) {
        try {
            // --- PENGATURAN WARNA DAN FONT DEFAULT ---

            // 1. Atur Font Global (Contoh menggunakan Segoe UI jika tersedia, jika tidak Arial)
            Font globalFont = new Font("Segoe UI", Font.PLAIN, 13);
            // Jika Segoe UI tidak ada, Arial akan digunakan sebagai fallback oleh sistem.
            // Anda bisa menambahkan logika lebih lanjut untuk memilih font secara dinamis jika perlu.

            UIManager.put("Button.font", globalFont);
            UIManager.put("Label.font", globalFont);
            UIManager.put("TextField.font", globalFont);
            UIManager.put("PasswordField.font", globalFont);
            UIManager.put("TextArea.font", globalFont);
            UIManager.put("Table.font", globalFont);
            UIManager.put("TableHeader.font", globalFont.deriveFont(Font.BOLD)); // Header tabel dibuat bold
            UIManager.put("ComboBox.font", globalFont);
            UIManager.put("CheckBox.font", globalFont);
            UIManager.put("TitledBorder.font", globalFont.deriveFont(Font.BOLD)); // Untuk judul border panel
            UIManager.put("OptionPane.messageFont", globalFont); // Font untuk pesan di JOptionPane
            UIManager.put("OptionPane.buttonFont", globalFont); // Font untuk tombol di JOptionPane


            // 2. Atur Warna Global (Contoh Palet Warna)
            Color primaryBackgroundColor = new Color(245, 248, 250); // Putih kebiruan lembut
            Color secondaryBackgroundColor = Color.WHITE;
            Color primaryForegroundColor = new Color(50, 50, 50);   // Abu-abu tua untuk teks
            Color accentColor = new Color(70, 130, 180);         // Biru baja untuk aksen
            Color buttonTextColor = Color.WHITE;

            // Latar Belakang Umum
            UIManager.put("Panel.background", primaryBackgroundColor);
            UIManager.put("OptionPane.background", primaryBackgroundColor);
            UIManager.put("ScrollPane.background", primaryBackgroundColor);
            UIManager.put("Viewport.background", primaryBackgroundColor); // Latar belakang JScrollPane content

            // Latar Belakang Komponen Input
            UIManager.put("TextField.background", secondaryBackgroundColor);
            UIManager.put("PasswordField.background", secondaryBackgroundColor);
            UIManager.put("TextArea.background", secondaryBackgroundColor);
            UIManager.put("ComboBox.background", secondaryBackgroundColor);
            
            // Warna Foreground Umum
            UIManager.put("Label.foreground", primaryForegroundColor);
            UIManager.put("TextField.foreground", primaryForegroundColor);
            UIManager.put("PasswordField.foreground", primaryForegroundColor);
            UIManager.put("TextArea.foreground", primaryForegroundColor);
            UIManager.put("CheckBox.foreground", primaryForegroundColor);
            UIManager.put("ComboBox.foreground", primaryForegroundColor);
            UIManager.put("Table.foreground", primaryForegroundColor);
            UIManager.put("TableHeader.foreground", primaryForegroundColor);

            // Tombol
            UIManager.put("Button.background", accentColor);
            UIManager.put("Button.foreground", buttonTextColor);
            UIManager.put("Button.focus", new Color(accentColor.getRed()-20, accentColor.getGreen()-20, accentColor.getBlue()-20)); // Warna saat fokus

            // Tabel
            UIManager.put("Table.selectionBackground", accentColor.brighter());
            UIManager.put("Table.selectionForeground", Color.WHITE);
            UIManager.put("Table.gridColor", new Color(200, 200, 200)); // Warna garis grid

            // Atur Look and Feel Sistem untuk beberapa elemen dasar (opsional, bisa dikombinasikan)
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception e) {
            System.err.println("Gagal mengatur custom Look and Feel: " + e.getMessage());
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new Main().showLoginView();
        });
    }

    // Metode showLoginView, showSignUpView, dan showMainView Anda tetap sama
    public void showLoginView() {
        LoginView loginView = new LoginView();
        new ControllerLogin(loginView, this);
        loginView.setVisible(true);
    }

    public void showSignUpView() {
        SignUpView signUpView = new SignUpView();
        new ControllerSignUp(signUpView, this);
        signUpView.setVisible(true);
    }

    public void showMainView() {
        ViewDataView viewDataView = new ViewDataView(); 
        new ControllerTugas(viewDataView, this);
        viewDataView.setVisible(true);
    }
}