package Test;

import shared.Request;
import shared.Response;
import shared.Product.*;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;

public class NotificationTest {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        try {
            // Connect to the server
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            System.out.println("Connected to the server.");

            // Create input and output streams for communication
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            // Register User 1
            handleRegisterRequest(outputStream, inputStream, "Hadi", "Saghir", LocalDate.of(1995, 5, 10),
                    "hadisaghir@mau.com", "HD", "password1");

            // Register User 2
            handleRegisterRequest(outputStream, inputStream, "Azam", "Suleiman", LocalDate.of(1992, 8, 20),
                    "azamsuleiman@mau.se", "AZ", "password2");

            // Login User 1
            handleLoginRequest(outputStream, inputStream, "HD", "password1");

            // Login User 2
            handleLoginRequest(outputStream, inputStream, "AZ", "password2");

            // Register interest in books for User 1
            handleRegisterInterestRequest(outputStream, inputStream, "BOOK", "HD");

            // Add a book by User 2
            handleAddProductRequest(outputStream, inputStream, ProductType.BOOK, 19.99, 2023,
                    "Red", ProductCondition.New, "AZ");

            // Wait for a notification for added product by User 1
            waitForNotification(inputStream);

            // Close the connections
            inputStream.close();
            outputStream.close();
            socket.close();
            System.out.println("Connection closed.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void handleRegisterRequest(ObjectOutputStream outputStream, ObjectInputStream inputStream,
                                              String firstName, String lastName, LocalDate dateOfBirth, String email,
                                              String username, String password)
            throws IOException, ClassNotFoundException {
        Request request = Request.register(firstName, lastName, dateOfBirth, email, username, password);
        sendRequest(outputStream, request);
        Response response = receiveResponse(inputStream);
        System.out.println("Received response for REGISTER request: " + response);
    }

    private static void handleLoginRequest(ObjectOutputStream outputStream, ObjectInputStream inputStream,
                                           String username, String password)
            throws IOException, ClassNotFoundException {
        Request request = Request.login(username, password);
        sendRequest(outputStream, request);
        Response response = receiveResponse(inputStream);
        System.out.println("Received response for LOGIN request: " + response);
    }

    private static void handleAddProductRequest(ObjectOutputStream outputStream, ObjectInputStream inputStream,
                                                ProductType productType, double price, int yearOfProduction,
                                                String color, ProductCondition condition, String sellerId)
            throws IOException, ClassNotFoundException {
        Request request = Request.addProduct(productType, price, yearOfProduction, color, condition);
        request.setUserId(sellerId);
        sendRequest(outputStream, request);
        Response response = receiveResponse(inputStream);
        System.out.println("Received response for ADD_PRODUCT request: " + response);
    }

    private static void handleRegisterInterestRequest(ObjectOutputStream outputStream, ObjectInputStream inputStream,
                                                      String interestedProductType, String userId)
            throws IOException, ClassNotFoundException {
        Request request = Request.registerInterest(interestedProductType, userId);
        sendRequest(outputStream, request);
        Response response = receiveResponse(inputStream);
        System.out.println("Received response for REGISTER_INTEREST request: " + response);
    }

    private static void sendRequest(ObjectOutputStream outputStream, Request request) throws IOException {
        outputStream.writeObject(request);
        System.out.println("Request sent to the server: " + request.getType());
    }

    private static Response receiveResponse(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Response response = (Response) inputStream.readObject();
        return response;
    }

    private static void waitForNotification(ObjectInputStream inputStream)
            throws IOException, ClassNotFoundException {
        while (true) {
            Response response = receiveResponse(inputStream);
            if (response.RESPONSE_TYPE() == Response.ResponseType.NOTIFICATION) {
                System.out.println("Received notification for added product: " + response.MESSAGE());
                break;
            }
        }
    }
}
