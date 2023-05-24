package view.subview;

import controller.Controller;
import view.MainView;

import java.util.HashMap;
import java.util.List;

public class SellMenu {
    private final MainView mainView;
    private final Controller controller;
    public SellMenu(MainView mainView, Controller controller) {
        this.mainView = mainView;
        this.controller = controller;
    }

    public void showSellMenu() {
        mainView.showMessage("|----------------------------------------------|");
        mainView.showMessage("|------------------Sell Menu-------------------|");
        mainView.showMessage("|----------------------------------------------|");
        mainView.showMessage("|-0. Back                                     -|");
        mainView.showMessage("|-1. Sell product                             -|");
        mainView.showMessage("|-2. Show my listed products                  -|");
        mainView.showMessage("|----------------------------------------------|");

        String input = mainView.promptForInput("Enter option: ");
        switch (input) {
            case "0" -> mainView.showMainMenu();
//            case "1" -> sellProduct();
            case "1" -> System.out.println("TODO");
//            case "2" -> showMyProducts();
            case "2" -> System.out.println("TODO");
            default -> {
                mainView.showError("Invalid option");
                showSellMenu();
            }
        }
    }

    private void sellProduct() {
        List<String> types = controller.getAllowedTypes();
        List<String> conditions = controller.getAllowedConditions();

        String type;
        do {
            for(int i= 0; i < types.size(); i++){
                mainView.showMessage(i + ". " + types.get(i));
            }

            type = mainView.promptForInput("Choose type: ");
            try {
                int index = Integer.parseInt(type);
                if(index < 1 || index > types.size()) {
                    throw new NumberFormatException();
                }
            } catch(NumberFormatException e) {
                mainView.showError("Invalid option");
                type = null;
            }
        } while(type == null);

        String input;
        Double price = null;
        do {
            input = mainView.promptForInput("Enter price: ");
            try {
                price = Double.parseDouble(input);
                if(price < 0) {
                    throw new NumberFormatException();
                }
            } catch(NumberFormatException e) {
                mainView.showError("Invalid price");
                input = null;
            }
        } while(input == null);

        Integer yearOfProduction = null;
        do {
            input = mainView.promptForInput("Enter year of production: ");
            try {
                if(!input.matches("^[12]\\d{3}$|^\\d{3}$")) {
                    throw new NumberFormatException();
                }
                yearOfProduction = Integer.parseInt(input);
            } catch(NumberFormatException e) {
                mainView.showError("Invalid year");
                input = null;
            }
        } while(input == null);

        String colour = mainView.promptForInput("Enter description: ");

        String condition;
        do {
            for(int i= 0; i < conditions.size(); i++){
                mainView.showMessage(i + ". " + conditions.get(i));
            }

            condition = mainView.promptForInput("Choose condition: ");
            try {
                int index = Integer.parseInt(condition);
                if(index < 1 || index > conditions.size()) {
                    throw new NumberFormatException();
                }
            } catch(NumberFormatException e) {
                mainView.showError("Invalid option");
                condition = null;
            }
        } while(condition == null);



        controller.addProduct(type, price, yearOfProduction, colour, condition);
        showSellMenu();
    }

    private void showMyProducts() {
        HashMap<Integer, String> products = controller.getMyProducts();
        mainView.showMessage("|----------------------------------------------|");
        mainView.showMessage("|------------------My Products-----------------|");
        mainView.showMessage("|----------------------------------------------|");
        mainView.showMessage("|-0. Back                                     -|");
        for(int i = 0; i < products.size(); i++) {
            mainView.showMessage("|-" + (i + 1) + ". " + products.get(i));
        }
        mainView.showMessage("|----------------------------------------------|");

        String input = mainView.promptForInput("Enter option: ");
        try {
            int index = Integer.parseInt(input);
            if(index < 0 || index > products.size()) {
                throw new NumberFormatException();
            } else if(index == 0) {
                showSellMenu();
            } else {
                showProductDetails(index - 1);
            }
        } catch(NumberFormatException e) {
            mainView.showError("Invalid option");
            showMyProducts();
        }
    }

    private void showProductDetails(int index) {
        HashMap<String, String> product = controller.getMyProductDetails(index);
        mainView.showMessage("|----------------------------------------------|");
        mainView.showMessage("|----------------Product Details---------------|");
        mainView.showMessage("|----------------------------------------------|");
        mainView.showMessage("|-0. Back                                     -|");
        for(String key : product.keySet()) {
            mainView.showMessage("|-" + key + ": " + product.get(key));
        }
        mainView.showMessage("|----------------------------------------------|");

        String input = mainView.promptForInput("Enter option: ");
        if(input.equals("0")) {
            showMyProducts();
        } else {
            mainView.showError("Invalid option");
            showProductDetails(index);
        }
    }
}
