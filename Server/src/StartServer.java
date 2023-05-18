import handlers.ServerController;

import java.io.IOException;
import java.util.Scanner;

public class StartServer {

    private static final int port = 8080;

    public static void main(String[] args) {
        try {
            ServerController serverController = new ServerController(port);
            Thread thread = new Thread(serverController);
            thread.start();
            Scanner scanner = new Scanner(System.in);

            try {
                Thread.sleep(5000); // Pause for 1 second
            } catch (InterruptedException e) {
                // Handle the InterruptedException if necessary
            }

            System.out.println("Press Enter to kill the server");
            scanner.nextLine();
            serverController.stop();

        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
        }
    }
}