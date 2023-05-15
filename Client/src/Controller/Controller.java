package controller;

import view.LaunchView;
import view.Menu;

public class Controller {

    // private final Menu menu;

    public Controller(){
        // menu = new Menu(this);
    }

    public void launch() {
        int choice = LaunchView.showLaunchMenu();
        if(choice == LaunchView.NEW_USER) {
            signup();
        } else if(choice == LaunchView.LOGIN) {
            login();
        } else {
            System.out.println("Good bye!");
            System.exit(0);
        }
    }

    private void signup() {

    }

    private void login() {

    }

    public void searchProduct() {
    }

}
