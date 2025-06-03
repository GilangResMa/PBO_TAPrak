package View.View.Tugas;

import Controller.SessionManager; 
import Model.Model.Tugas.ModelTugas;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;
 
public class ViewDataView extends JFrame {
    private JTable tugasTable;
    private JButton addButton, editButton, deleteButton;
    private JComboBox<String> sortComboBox;
    private JLabel welcomeLabel;
    private JButton logoutButton;
    private JTextField searchField;
    private JCheckBox hideCompletedCheckBox;
    private JLabel summaryLabel;

    public ViewDataView() {
        setTitle("Daftar Tugas Pro");
        setMinimumSize(new Dimension(900, 650));
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel mainTopPanel = new JPanel(new BorderLayout(10, 5));
        mainTopPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        String username = SessionManager.getCurrentUser() != null ? SessionManager.getCurrentUser().getUsername() : "Pengguna";
        welcomeLabel = new JLabel("Selamat datang, " + username + "!");
        logoutButton = new JButton("Logout");
        userPanel.add(welcomeLabel);
        userPanel.add(logoutButton);
        
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        addButton = new JButton("Tambah Tugas");
        editButton = new JButton("Edit Tugas");
        deleteButton = new JButton("Hapus Tugas");
        controlPanel.add(addButton);
        controlPanel.add(editButton);
        controlPanel.add(deleteButton);
        
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        optionsPanel.add(new JLabel("| Urutkan:"));
        sortComboBox = new JComboBox<>(new String[]{"Batas Waktu (Terdekat)", "Batas Waktu (Terjauh)", "Nama Tugas (A-Z)"});
        sortComboBox.setSelectedItem("Batas Waktu (Terdekat)");
        sortComboBox.setPreferredSize(new Dimension(180, sortComboBox.getPreferredSize().height));
        optionsPanel.add(sortComboBox);
        optionsPanel.add(new JLabel("| Cari Judul:"));
        searchField = new JTextField(15);
        optionsPanel.add(searchField);
        hideCompletedCheckBox = new JCheckBox("Sembunyikan Selesai"); 
        optionsPanel.add(hideCompletedCheckBox); 

        mainTopPanel.add(userPanel, BorderLayout.NORTH);
        JPanel bottomControls = new JPanel(new BorderLayout());
        bottomControls.add(controlPanel, BorderLayout.WEST); 
        bottomControls.add(optionsPanel, BorderLayout.CENTER); 
        mainTopPanel.add(bottomControls, BorderLayout.SOUTH); 
        add(mainTopPanel, BorderLayout.NORTH);

        tugasTable = new JTable();
        tugasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
        tugasTable.getTableHeader().setReorderingAllowed(false); 
        tugasTable.setRowHeight(tugasTable.getRowHeight() + 6); 
        tugasTable.setIntercellSpacing(new Dimension(10, 4)); 
        tugasTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tugasTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(tugasTable);
        scrollPane.setBorder(new EmptyBorder(0, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        summaryLabel = new JLabel("Memuat data...");
        summaryLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        summaryLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(summaryLabel, BorderLayout.SOUTH); 
        if (getContentPane() instanceof JPanel) {
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
        }
    }

    public JTable getTugasTable() { return tugasTable; }
    public JButton getAddButton() { return addButton; }
    public JButton getEditButton() { return editButton; }
    public JButton getDeleteButton() { return deleteButton; }
    public JComboBox<String> getSortComboBox() { return sortComboBox; }
    public JButton getLogoutButton() { return logoutButton; }
    public JTextField getSearchField() { return searchField; }
    public JCheckBox getHideCompletedCheckBox() { return hideCompletedCheckBox; }
    public JLabel getSummaryLabel() { return summaryLabel; }
    
    public void setTableModel(TugasTableModel tableModel) {
        this.tugasTable.setModel(tableModel);
        if (tableModel != null && tableModel.getColumnCount() > 0) {
            TableColumnModel columnModel = this.tugasTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(180); // Judul
            columnModel.getColumn(1).setPreferredWidth(300); // Deskripsi
            columnModel.getColumn(2).setPreferredWidth(120); // Batas Waktu
            if (columnModel.getColumnCount() > 3) { // Kolom "Selesai?" adalah indeks 3
                 columnModel.getColumn(3).setPreferredWidth(80); 
            }
        }
    }
    public ModelTugas getSelectedTugas() {
        int selectedRow = tugasTable.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = tugasTable.convertRowIndexToModel(selectedRow);
            if (tugasTable.getModel() instanceof TugasTableModel) {
                return ((TugasTableModel) tugasTable.getModel()).getTugasAt(modelRow);
            }
        }
        return null;
    }
}