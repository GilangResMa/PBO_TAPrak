package Model.Model.Tugas;

import java.time.LocalDate;

public class ModelTugas {
    private int idTugas;
    private String judul;
    private String deskripsi;
    private LocalDate dueDate;
    private boolean isDone;
    private int userId;

    public ModelTugas(int id, String judul, String desc, LocalDate due, boolean done, int uId) {
        this.idTugas = id;
        this.judul = judul;
        this.deskripsi = desc;
        this.dueDate = due;
        this.isDone = done;
        this.userId = uId;
    }
    public int getIdTugas() { return idTugas; }
    public void setIdTugas(int idTugas) { this.idTugas = idTugas; }
    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }
    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public boolean isIsDone() { return isDone; }
    public void setIsDone(boolean isDone) { this.isDone = isDone; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}