
package Controller;
import Model.Model.User.ModelUser;

public class SessionManager {
    private static ModelUser currentUser;
    public static void setCurrentUser(ModelUser user) { currentUser = user; }
    public static ModelUser getCurrentUser() { return currentUser; }
}