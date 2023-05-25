import controller.Controller;
import controller.MainController;
import view.MainView;
import view.View;

/**
 * Starts the client application.
 */
public class StartClient {

    /**
     * Starts the client application. Creates the main view and controller.
     */
    public static void main(String[] args) {
        View menu = new MainView();
        Controller mainController = new MainController();

        mainController.setView(menu);
        menu.setController(mainController);

        mainController.launch();
    }
}