/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.View.Tugas;

// ... (kode sama persis dengan InputDataView.java yang sudah direvisi agar status hanya muncul saat edit) ...
import Model.Model.Tugas.ModelTugas;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class InputDataView extends JDialog {
    private JTextField judulField;
    private JTextArea deskripsiArea;
    private JTextField dueDateField;
    private JCheckBox isDoneCheckBox; 
    private JLabel statusLabel;      
    private JButton okButton;
    private JButton cancelButton;
    private ModelTugas tugasResult;

    public InputDataView(Frame owner, String title, ModelTugas tugasToEdit) {
        super(owner, title, true); 
        setSize(450, 350); 
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10,10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        judulField = new JTextField(20);
        deskripsiArea = new JTextArea(5, 20);
        dueDateField = new JTextField(10);
        dueDateField.setToolTipText("Format: YYYY-MM-DD");
        
        statusLabel = new JLabel("Status:");
        isDoneCheckBox = new JCheckBox("Sudah Selesai");

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Judul:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; formPanel.add(judulField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Deskripsi:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(new JScrollPane(deskripsiArea), gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Batas Waktu (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(dueDateField, gbc);
        
        if (tugasToEdit != null) { // Mode Edit
            judulField.setText(tugasToEdit.getJudul());
            deskripsiArea.setText(tugasToEdit.getDeskripsi());
            if (tugasToEdit.getDueDate() != null) {
                dueDateField.setText(tugasToEdit.getDueDate().toString());
            }
            gbc.gridx = 0; gbc.gridy = 3; formPanel.add(statusLabel, gbc);
            gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST; 
            formPanel.add(isDoneCheckBox, gbc);
            isDoneCheckBox.setSelected(tugasToEdit.isIsDone());
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        okButton = new JButton("OK");
        cancelButton = new JButton("Batal");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        okButton.addActionListener(e -> onOK(tugasToEdit));
        cancelButton.addActionListener(e -> onCancel());

        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void onOK(ModelTugas tugasToEdit) {
        if (judulField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Judul wajib diisi.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        LocalDate dueDate = null; 
        if (!dueDateField.getText().trim().isEmpty()) {
            try {
                dueDate = LocalDate.parse(dueDateField.getText().trim());
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Format tanggal salah. Gunakan YYYY-MM-DD atau kosongkan.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        int id = (tugasToEdit != null) ? tugasToEdit.getIdTugas() : 0;
        boolean finalIsDone;
        if (tugasToEdit != null) { 
            finalIsDone = isDoneCheckBox.isSelected(); 
        } else { 
            finalIsDone = false; 
        }

        this.tugasResult = new ModelTugas(
                id, judulField.getText().trim(), deskripsiArea.getText().trim(),
                dueDate, finalIsDone, 0 
        );
        setVisible(false);
    }
    private void onCancel() {
        this.tugasResult = null;
        setVisible(false);
    }
    public ModelTugas showDialog() {
        this.tugasResult = null; 
        setVisible(true);
        return this.tugasResult;
    }
}