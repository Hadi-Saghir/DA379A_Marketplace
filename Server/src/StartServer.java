import java.io.IOException;
import java.util.Scanner;

public class StartServer {

    private static final int port = 8080;

    public static void main(String[] args) {
        try {
            ServerController serverController = new ServerController(port);
            Thread thread = new Thread(serverController);
            thread.start();
            System.out.println("Press any Enter to kill the server");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            serverController.stop();
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
        }
    }
}