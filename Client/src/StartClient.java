import controller.Controller;
import controller.MainController;
import view.MainView;
import view.View;

public class StartClient {

    public static void main(String[] args) {
        View menu = new MainView();
        Controller mainController = new MainController();

        mainController.setView(menu);
        menu.setController(mainController);

        mainController.launch();
    }
}