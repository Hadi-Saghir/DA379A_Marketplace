package view.subview;

import controller.Controller;
import view.MainView;

import java.util.HashMap;
import java.util.List;

/**
 * A view the handle the sell menu.
 */
public class SellMenu {
    private final MainView mainView;
    private final Controller controller;
    public SellMenu(MainView mainView, Controller controller) {
        this.mainView = mainView;
        this.controller = controller;
    }

    /**
     * Shows the sell menu. The user can offer a product or accept offers.
     */
    public void showSellMenu() {
        mainView.showMessage("|----------------------------------------------|");
        mainView.showMessage("|------------------Sell Menu-------------------|");
        mainView.showMessage("|----------------------------------------------|");
        mainView.showMessage("|-0. Back                                     -|");
        mainView.showMessage("|-1. Offer product                            -|");
        mainView.showMessage("|-2. Accept offers                            -|");
        mainView.showMessage("|----------------------------------------------|");

        String input = mainView.promptForInput("Enter option: ");
        switch (input) {
            case "0" -> mainView.showMainMenu();
            case "1" -> offerProduct();
            case "2" -> acceptOffers();
            default -> {
                mainView.showError("Invalid option");
                showSellMenu();
            }
        }
    }

    /**
     * Offers a product. The user can choose the type, price, year of production, color and condition.
     */
    private void offerProduct() {
        List<String> types = controller.getAllowedTypes();
        List<String> conditions = controller.getAllowedConditions();

        String type;
        do {
            for(int i = 0; i < types.size(); i++){
                mainView.showMessage(i + ". " + types.get(i));
            }

            type = mainView.promptForInput("Choose type: ");
            try {
                int index = Integer.parseInt(type);
                if(index < 0 || index > types.size()) {
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

        String color = mainView.promptForInput("Enter color: ");

        String condition;
        do {
            for(int i= 0; i < conditions.size(); i++){
                mainView.showMessage(i + ". " + conditions.get(i));
            }

            condition = mainView.promptForInput("Choose condition: ");
            try {
                int index = Integer.parseInt(condition);
                if(index < 0 || index > conditions.size()) {
                    throw new NumberFormatException();
                }
            } catch(NumberFormatException e) {
                mainView.showError("Invalid option");
                condition = null;
            }
        } while(condition == null);



        controller.addProduct(types.get(Integer.parseInt(type)), price, yearOfProduction, color, conditions.get(Integer.parseInt(condition)));
        showSellMenu();
    }

    /**
     * Shows the offers the user has received. The user can accept an offer.
     */
    private void acceptOffers() {
        HashMap<Integer, String> products = controller.getProductsWithOffer();

        mainView.showMessage("|----------------------------------------------|");
        mainView.showMessage("|------------------My Offers-------------------|");
        mainView.showMessage("|----------------------------------------------|");
        mainView.showMessage("|-0. Back                                     -|");
        for(int i: products.keySet()) {
            mainView.showMessage("|-" + (i) + ". " + products.get(i));
        }
        mainView.showMessage("|----------------------------------------------|");

        String input = mainView.promptForInput("Enter offer to accept: ");
        try {
            int index = Integer.parseInt(input);
            if(index < 0 || !products.containsKey(index)) {
                throw new NumberFormatException();
            } else if(index == 0) {
                showSellMenu();
            } else {
                acceptOffer(index);
            }
        } catch(NumberFormatException e) {
            mainView.showError("Invalid option");
            acceptOffers();
        }
    }

    /**
     * Accepts an offer.
     * @param id The id of the offer to accept.
     */
    private void acceptOffer(int id) {
        controller.acceptOffer(id);
        showSellMenu();
    }
}
