package view;


import controller.MainController;

public class Menu {
    private final MainController mainController;

    public Menu(MainController mainController) {
        this.mainController = mainController;
    }

    public void showLoginMenu() {
        System.out.println("Welcome to the Warehouse Management System");
        System.out.println("Please select an option:");
        System.out.println("0. Exit");
        System.out.println("1. Login");
        System.out.println("2. Create new user");
        System.out.print("Enter option: ");
    }

    public void showMainMenu() {

    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String error) {
        System.err.println(error);
    }
}
