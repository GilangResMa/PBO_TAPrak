package Model.Model.User;

public interface InterfaceDAOUser {
    public ModelUser validateLogin(String username, String password);
    public boolean addUser(String username, String password);
}