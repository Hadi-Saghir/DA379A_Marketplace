package Test;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;

import Shared.src.shared.Response;
import S

public class ClientTest {
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

            // Handle each request and response
            handleRegisterRequest(outputStream, inputStream);
            handleLoginRequest(outputStream, inputStream);
            handleAddProductRequest(outputStream, inputStream);
            handleAllProductsRequest(outputStream, inputStream);
            handleSearchProductRequest(outputStream, inputStream);
            handleSellProductRequest(outputStream, inputStream);
            handleMakeOfferRequest(outputStream, inputStream);
            handleRegisterInterestRequest(outputStream, inputStream);
            handleGetPurchaseHistoryRequest(outputStream, inputStream);

            // Close the connections
            inputStream.close();
            outputStream.close();
            socket.close();
            System.out.println("Connection closed.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void handleRegisterRequest(ObjectOutputStream outputStream, ObjectInputStream inputStream)
            throws IOException, ClassNotFoundException {
        Request request = Request.register("John", "Doe", LocalDate.of(1990, 5, 10),
                "john.doe@example.com", "johndoe", "password");
        sendRequest(outputStream, request);
        Response response = receiveResponse(inputStream);
        System.out.println("Received response for REGISTER request: " + response);
    }

    private static void handleLoginRequest(ObjectOutputStream outputStream, ObjectInputStream inputStream)
            throws IOException, ClassNotFoundException {
        Request request = Request.login("johndoe", "password");
        sendRequest(outputStream, request);
        Response response = receiveResponse(inputStream);
        System.out.println("Received response for LOGIN request: " + response);
    }

    private static void handleAddProductRequest(ObjectOutputStream outputStream, ObjectInputStream inputStream)
            throws IOException, ClassNotFoundException {
        Request request = Request.addProduct(Product.ProductType.ELECTRONICS, 499.99, 2022,
                "Black", Product.ProductCondition.NEW);
        sendRequest(outputStream, request);
        Response response = receiveResponse(inputStream);
        System.out.println("Received response for ADD_PRODUCT request: " + response);
    }

    private static void handleAllProductsRequest(ObjectOutputStream outputStream, ObjectInputStream inputStream)
            throws IOException, ClassNotFoundException {
        Request request = Request.allProducts();
        sendRequest(outputStream, request);
        Response response = receiveResponse(inputStream);
        System.out.println("Received response for ALL_PRODUCTS request: " + response);
    }

    private static void handleSearchProductRequest(ObjectOutputStream outputStream, ObjectInputStream inputStream)
            throws IOException, ClassNotFoundException {
        Request request = Request.searchProduct("Electronics", 100, 1000, Product.ProductCondition.USED);
        sendRequest(outputStream, request);
        Response response = receiveResponse(inputStream);
        System.out.println("Received response for SEARCH_PRODUCT request: " + response);
    }

    private static void handleSellProductRequest(ObjectOutputStream outputStream, ObjectInputStream inputStream)
            throws IOException, ClassNotFoundException {
        Request request = Request.sellProduct(123, "seller123");
        sendRequest(outputStream, request);
        Response response = receiveResponse(inputStream);
        System.out.println("Received response for SELL_PRODUCT request: " + response);
    }

    private static void handleMakeOfferRequest(ObjectOutputStream outputStream, ObjectInputStream inputStream)
            throws IOException, ClassNotFoundException {
        Request request = Request.makeOffer(456, "buyer456", 200.00);
        sendRequest(outputStream, request);
        Response response = receiveResponse(inputStream);
        System.out.println("Received response for Make_Offer request: " + response);
    }

    private static void handleRegisterInterestRequest(ObjectOutputStream outputStream, ObjectInputStream inputStream)
            throws IOException, ClassNotFoundException {
        Request request = Request.registerInterest("Electronics", "user123");
        sendRequest(outputStream, request);
        Response response = receiveResponse(inputStream);
        System.out.println("Received response for REGISTER_INTEREST request: " + response);
    }

    private static void handleGetPurchaseHistoryRequest(ObjectOutputStream outputStream, ObjectInputStream inputStream)
            throws IOException, ClassNotFoundException {
        Request request = Request.getPurchaseHistory(123, LocalDate.of(2022, 1, 1), LocalDate.of(2022, 12, 31));
        sendRequest(outputStream, request);
        Response response = receiveResponse(inputStream);
        System.out.println("Received response for GET_PURCHASE_HISTORY request: " + response);
    }

    private static void sendRequest(ObjectOutputStream outputStream, Request request) throws IOException {
        outputStream.writeObject(request);
        System.out.println("Request sent to the server: " + request.getType());
    }

    private static Response receiveResponse(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Response response = (Response) inputStream.readObject();
        System.out.println(response.toString());
        return response;
    }
}


