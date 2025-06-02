package schreminder.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TodoView extends JFrame {
    public DefaultListModel<String> listModel = new DefaultListModel<>();
    public JList<String> todoList = new JList<>(listModel);
    public JTextField taskField = new JTextField(15);
    public JTextField dateField = new JTextField(10); // format: yyyy-MM-dd
    public JTextField timeField = new JTextField(5);  // format: HH:mm
    public JButton addButton = new JButton("Tambah");
    public JButton deleteButton = new JButton("Hapus");

    public TodoView() {
        setTitle("To-Do Reminder");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Tugas:"));
        inputPanel.add(taskField);
        inputPanel.add(new JLabel("Tanggal (yyyy-MM-dd):"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("Waktu (HH:mm):"));
        inputPanel.add(timeField);
        inputPanel.add(addButton);

        JScrollPane scrollPane = new JScrollPane(todoList);

        JPanel controlPanel = new JPanel();
        controlPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    public void showReminder(String task, String datetime) {
        JOptionPane.showMessageDialog(this, "⏰ Waktunya untuk: " + task + " (" + datetime + ")", "Pengingat", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void clearInput() {
        taskField.setText("");
        dateField.setText("");
        timeField.setText("");
    }

    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }
}
