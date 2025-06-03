package Controller; // PASTIKAN SESUAI DENGAN PACKAGE ANDA

import Main.Main; // PASTIKAN SESUAI
import Model.Model.Tugas.*; // PASTIKAN SESUAI
import View.View.Tugas.*;   // PASTIKAN SESUAI
// import java.awt.event.MouseAdapter; // Tidak terpakai, bisa dihapus
// import java.awt.event.MouseEvent;   // Tidak terpakai, bisa dihapus
import java.util.Comparator;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList; // Import jika belum ada
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.JTextField; // Import JTextField
import javax.swing.JCheckBox;  // Import JCheckBox
import javax.swing.JLabel;     // Import JLabel
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class ControllerTugas {
    private ViewDataView view; 
    private InterfaceDAOTugas dao;
    private Main mainNavigator;

    public ControllerTugas(ViewDataView view, Main mainNavigator) {
        this.view = view;
        this.dao = new DAOTugas();
        this.mainNavigator = mainNavigator;
        
        // Pastikan view tidak null sebelum mengakses komponennya untuk listener
        if (this.view != null) {
            loadTugasData(); // Muat data awal hanya jika view ada
            
            // Event Listeners
            this.view.getAddButton().addActionListener(e -> handleTambah());
            this.view.getEditButton().addActionListener(e -> handleEdit());
            this.view.getDeleteButton().addActionListener(e -> handleDelete());
            this.view.getSortComboBox().addActionListener(e -> loadTugasData());
            this.view.getLogoutButton().addActionListener(e -> handleLogout());
            
            JTextField searchField = this.view.getSearchField();
            if (searchField != null) {
                searchField.addKeyListener(new java.awt.event.KeyAdapter() {
                    @Override
                    public void keyReleased(java.awt.event.KeyEvent evt) {
                        loadTugasData();
                    }
                });
            }
            
            JCheckBox hideCompletedCheckBox = this.view.getHideCompletedCheckBox();
            if (hideCompletedCheckBox != null) {
                hideCompletedCheckBox.addActionListener(e -> loadTugasData());
            }
        } else {
            System.err.println("ControllerTugas: ViewDataView adalah null saat inisialisasi!");
            // Mungkin tampilkan error ke pengguna atau lakukan tindakan lain
        }
    }

    private void loadTugasData() {
        if (view == null) return; // Jangan lanjutkan jika view null

        if (SessionManager.getCurrentUser() == null) {
            JOptionPane.showMessageDialog(view, "Sesi pengguna tidak ditemukan. Silakan login kembali.", "Error Sesi", JOptionPane.ERROR_MESSAGE);
            view.dispose(); 
            if (mainNavigator != null) mainNavigator.showLoginView(); 
            return; 
        }
        int userId = SessionManager.getCurrentUser().getUserId();
        List<ModelTugas> allTugasList = dao.getAllTugasByUser(userId);
        
        String searchText = "";
        JTextField searchFieldComponent = view.getSearchField();
        if (searchFieldComponent != null) {
            String textFromField = searchFieldComponent.getText();
            if (textFromField != null) {
                searchText = textFromField.trim().toLowerCase();
            }
        }

        List<ModelTugas> listAfterSearch;
        if (searchText.isEmpty()) {
            listAfterSearch = allTugasList;
        } else {
            // --- REVISI BAGIAN FILTER UNTUK SEARCHTEXT ---
            final String finalSearchText = searchText; // Perlu final untuk digunakan dalam lambda
            listAfterSearch = allTugasList.stream()
                    .filter(tugas -> {
                        String judul = tugas.getJudul();
                        // Hanya proses filter jika judul tidak null
                        return judul != null && judul.toLowerCase().contains(finalSearchText);
                    })
                    .collect(Collectors.toList());
            // --- AKHIR REVISI ---
        }
        
        boolean hideCompleted = false;
        JCheckBox hideCompletedCheckboxComponent = view.getHideCompletedCheckBox();
        if (hideCompletedCheckboxComponent != null) {
            hideCompleted = hideCompletedCheckboxComponent.isSelected();
        }

        List<ModelTugas> listAfterHideCompleted;
        if (hideCompleted) {
            listAfterHideCompleted = listAfterSearch.stream()
                    .filter(tugas -> !tugas.isIsDone())
                    .collect(Collectors.toList());
        } else {
            listAfterHideCompleted = listAfterSearch;
        }
        
        List<ModelTugas> finalList = listAfterHideCompleted;
        String sortCriteria = (String) view.getSortComboBox().getSelectedItem();
        if (sortCriteria != null) {
            // ... (logika switch untuk sorting tetap sama) ...
            switch (sortCriteria) {
                case "Batas Waktu (Terdekat)":
                    finalList.sort(Comparator.comparing(ModelTugas::getDueDate, Comparator.nullsLast(LocalDate::compareTo)));
                    break;
                case "Batas Waktu (Terjauh)":
                    finalList.sort(Comparator.comparing(ModelTugas::getDueDate, Comparator.nullsLast(LocalDate::compareTo)).reversed());
                    break;
                case "Nama Tugas (A-Z)":
                    finalList.sort(Comparator.comparing(ModelTugas::getJudul, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER))); // nullsFirst untuk judul null
                    break;
                default:
                    finalList.sort(Comparator.comparing(ModelTugas::getDueDate, Comparator.nullsLast(LocalDate::compareTo)));
                    break;
            }
        }
        
        TugasTableModel model = new TugasTableModel(new ArrayList<>(finalList)); // Buat salinan untuk keamanan
        
        model.addTableModelListener(e -> { // Menggunakan lambda yang lebih ringkas
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 3) { 
                int row = e.getFirstRow();
                if (view.getTugasTable().getModel() instanceof TugasTableModel) {
                    TugasTableModel currentModel = (TugasTableModel) view.getTugasTable().getModel();
                    ModelTugas changedTugas = currentModel.getTugasAt(row);
                    if (changedTugas != null) {
                        if (!dao.updateTugas(changedTugas)) {
                            JOptionPane.showMessageDialog(view, "Gagal mengupdate status tugas di database.", "Error Update", JOptionPane.ERROR_MESSAGE);
                            changedTugas.setIsDone(!changedTugas.isIsDone());
                            currentModel.fireTableCellUpdated(row, e.getColumn());
                        }
                        // Update summary dengan list dari model yang terpasang di tabel, yang mungkin sudah berubah
                        updateSummaryLabel(currentModel.getTugasList()); 
                    }
                }
            }
        });
        
        view.setTableModel(model);
        updateSummaryLabel(finalList); // Update summary label dengan data yang akan ditampilkan
    }
    
    private void updateSummaryLabel(List<ModelTugas> tugasToShow) {
        JLabel summaryLabelComponent = view.getSummaryLabel();
        if (summaryLabelComponent != null) {
            long completedCount = tugasToShow.stream().filter(ModelTugas::isIsDone).count();
            long pendingCount = tugasToShow.size() - completedCount;
            summaryLabelComponent.setText("Total: " + tugasToShow.size() + " | Selesai: " + completedCount + " | Belum: " + pendingCount);
        }
    }
    
    // Metode handleTambah, handleEdit, handleDelete, handleLogout tetap sama...
    // ... (Pastikan mereka juga memiliki pengecekan null untuk SessionManager.getCurrentUser() jika perlu) ...
    private void handleTambah() {
        InputDataView dialog = new InputDataView(view, "Tambah Tugas Baru", null);
        ModelTugas tugasBaru = dialog.showDialog();
        if (tugasBaru != null) {
            if (SessionManager.getCurrentUser() != null) {
                tugasBaru.setUserId(SessionManager.getCurrentUser().getUserId());
                if (dao.addTugas(tugasBaru)) {
                    loadTugasData();
                } else {
                    JOptionPane.showMessageDialog(view, "Gagal menambah tugas.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                 JOptionPane.showMessageDialog(view, "Sesi pengguna tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleEdit() {
        ModelTugas tugasTerpilih = view.getSelectedTugas();
        if (tugasTerpilih != null) {
            InputDataView dialog = new InputDataView(view, "Edit Tugas", tugasTerpilih);
            ModelTugas hasilEdit = dialog.showDialog();
            if (hasilEdit != null) {
                hasilEdit.setUserId(tugasTerpilih.getUserId()); 
                if (dao.updateTugas(hasilEdit)) {
                    loadTugasData();
                } else {
                    JOptionPane.showMessageDialog(view, "Gagal mengedit tugas.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(view, "Pilih tugas yang ingin diedit.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void handleDelete() {
        ModelTugas tugasTerpilih = view.getSelectedTugas();
        if (tugasTerpilih != null) {
            int confirm = JOptionPane.showConfirmDialog(view, 
                "Yakin ingin menghapus tugas '" + (tugasTerpilih.getJudul() != null ? tugasTerpilih.getJudul() : "") + "'?", 
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.deleteTugas(tugasTerpilih.getIdTugas())) {
                    loadTugasData();
                } else {
                    JOptionPane.showMessageDialog(view, "Gagal menghapus tugas.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(view, "Pilih tugas yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(view, "Anda yakin ingin logout?", "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            SessionManager.setCurrentUser(null); 
            if (view != null) view.dispose(); 
            if (mainNavigator != null) mainNavigator.showLoginView(); 
        }
    }
}