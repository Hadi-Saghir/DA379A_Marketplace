package view;

import java.util.Scanner;

public class LaunchView {
    public static final int LOGIN = 1;
    public static final int NEW_USER = 2;

    public static int showLaunchMenu() {
        System.out.println("Welcome to the Warehouse Management System");
        System.out.println("Please select an option:");
        System.out.println("0. Exit");
        System.out.println("1. Login");
        System.out.println("2. Create new user");
        System.out.print("Enter option: ");

        Scanner in = new Scanner(System.in);
        // String input = in.nextLine();

        while(!in.hasNext("[0-2]")) {
            System.out.println("Please enter a valid option");
            System.out.print("Enter option: ");
            in.nextLine();
        }

        String input = in.nextLine();

        if(input.equals("1")) {
            return LOGIN;
        } else if(input.equals("2")) {
            return NEW_USER;
        } else {
            return 0;
        }
    }
}
