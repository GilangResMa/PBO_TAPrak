/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private JTable tugasTable;
    private JButton addButton, editButton, deleteButton;
    private JComboBox<String> sortComboBox;

    public MainView() {
        setTitle("Daftar Tugas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel Atas untuk Tombol
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButton = new JButton("Tambah");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Hapus");
        sortComboBox = new JComboBox<>(new String[]{"Terbaru", "Terlama"});
        
        topPanel.add(addButton);
        topPanel.add(editButton);
        topPanel.add(deleteButton);
        topPanel.add(new JLabel("  Urutkan:"));
        topPanel.add(sortComboBox);
        add(topPanel, BorderLayout.NORTH);

        // Tabel di Tengah
        tugasTable = new JTable();
        add(new JScrollPane(tugasTable), BorderLayout.CENTER);
    }

    // Getters
    public JTable getTugasTable() { return tugasTable; }
    public JButton getAddButton() { return addButton; }
    public JButton getEditButton() { return editButton; }
    public JButton getDeleteButton() { return deleteButton; }
    public JComboBox<String> getSortComboBox() { return sortComboBox; }
}