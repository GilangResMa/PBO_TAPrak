package Model.Model.Tugas;

import java.util.List;

public interface InterfaceDAOTugas {
    public List<ModelTugas> getAllTugasByUser(int userId);
    public boolean addTugas(ModelTugas tugas);
    public boolean updateTugas(ModelTugas tugas);
    public boolean deleteTugas(int idTugas);
}