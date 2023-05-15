package view;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputHandler {
    private static final Scanner in = new Scanner(System.in);

    public static String getInput(String message, String failMessage) {
        System.out.println(message);

        String input = null;
        while(input == null) {
            input = in.nextLine();
            if(input.isBlank()) {
                System.out.println(failMessage);
            }
        }

        return input;
    }

    public static String getInput(String message, String failMessage, String regex) {
        System.out.println(message);

        String input = null;
        while(input == null) {
            input = in.nextLine();
            if(input.isBlank() || !input.matches(regex)) {
                System.out.println(failMessage);
                input = null;
            }
        }

        return input;
    }

    public static LocalDate getDate(String message, String failMessage) {
        System.out.println(message);

        LocalDate date = null;
        while(date == null) {
            try {
                date = LocalDate.parse(in.nextLine());
            } catch(DateTimeParseException e) {
                System.out.println(failMessage);
            }
        }

        return date;
    }
}
