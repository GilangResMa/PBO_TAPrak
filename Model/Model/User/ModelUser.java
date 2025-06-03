package Model.Model.User;

public class ModelUser {
    private int userId;
    private String username;
    public ModelUser(int userId, String username) { this.userId = userId; this.username = username; }
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
}