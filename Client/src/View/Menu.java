package view;

import controller.Controller;

import java.util.Scanner;

public class Menu {

    private Controller controller;
    private boolean exitProgram;
    private Scanner input;
    public Menu(Controller controller){
        this.controller = controller;
        exitProgram = false;
        input = new Scanner(System.in);

        String choice = "";

        do{
            choice = input.nextLine();

            switch (choice){
                case "1":
                    searchProduct();
                    break;
                default:
                    System.out.println("Enter number choice as number between 0 and 10");
                    break;
            }

        } while(!exitProgram);
    }

    private void searchProduct() {
        String searchTerm = "";
        controller.searchProduct();

    }

}
