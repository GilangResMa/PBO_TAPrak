package schreminder;

import schreminder.controller.LoginController;
import schreminder.view.LoginView;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            new LoginController(loginView);
        });
    }
}
