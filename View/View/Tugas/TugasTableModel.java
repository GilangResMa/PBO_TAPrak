package View.View.Tugas;

import Model.Model.Tugas.ModelTugas;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class TugasTableModel extends AbstractTableModel {
    private List<ModelTugas> tugasList;
    private final String[] columnNames = {"Judul", "Deskripsi", "Batas Waktu", "Selesai?"};

    public TugasTableModel(List<ModelTugas> tugasList) {
        if (tugasList == null) {
            this.tugasList = new ArrayList<>();
        } else {
            this.tugasList = tugasList;
        }
    }
    @Override
    public int getRowCount() { return tugasList.size(); }
    @Override
    public int getColumnCount() { return columnNames.length; }
    @Override
    public String getColumnName(int column) { return columnNames[column]; }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 3) { return Boolean.class; }
        return super.getColumnClass(columnIndex);
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 3;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ModelTugas tugas = tugasList.get(rowIndex);
        switch (columnIndex) {
            case 0: return tugas.getJudul();
            case 1: return tugas.getDeskripsi();
            case 2: return tugas.getDueDate();
            case 3: return tugas.isIsDone();
            default: return null;
        }
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 3 && aValue instanceof Boolean) {
            ModelTugas tugas = tugasList.get(rowIndex);
            tugas.setIsDone((Boolean) aValue);
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }
    public ModelTugas getTugasAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < tugasList.size()) {
            return tugasList.get(rowIndex);
        }
        return null;
    }
    public List<ModelTugas> getTugasList() {
        return this.tugasList;
    }
    public void setTugasList(List<ModelTugas> tugasList) {
        if (tugasList == null) {
            this.tugasList = new ArrayList<>();
        } else {
            this.tugasList = tugasList;
        }
        fireTableDataChanged();
    }
}