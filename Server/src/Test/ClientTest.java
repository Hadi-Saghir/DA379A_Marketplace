package Test;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;

import Shared.src.shared.Response;
import Shared.src.shared.Request;
import Shared.src.shared.Product.*;


public class ClientTest {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        try {
            // Connect to the server
            Thread.sleep(2000);
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            System.out.println("Connected to the server.");


            // Create input and output streams for communication
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            // Handle each request and response
            /*
            handleRegisterRequest(outputStream, inputStream);
            handleLoginRequest(outputStream, inputStream);
            handleAddProductRequest(outputStream, inputStream);
            handleAllProductsRequest(outputStream, inputStream);
            handleSearchProductRequest(outputStream, inputStream);
            handleSellProductRequest(outputStream, inputStream);
            handleMakeOfferRequest(outputStream, inputStream);
            handleRegisterInterestRequest(outputStream, inputStream);
            handleGetPurchaseHistoryRequest(outputStream, inputStream);


             */
            handleMakeOfferRequest(outputStream, inputStream);


            // Close the connections
            inputStream.close();
            outputStream.close();
            socket.close();
            System.out.println("Connection closed.");
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void handleRegisterRequest(ObjectOutputStream outputStream, ObjectInputStream inputStream)
            throws IOException, ClassNotFoundException {
        Request request = Request.register("Hadi", "Saghir", LocalDate.of(1995, 5, 10),
                "hadisaghir@mau.com", "HD", "password1");
        sendRequest(outputStream, request);
        Response response = receiveResponse(inputStream);
        System.out.println("Received response for REGISTER request: " + response);
    }

    private static void handleLoginRequest(ObjectOutputStream outputStream, ObjectInputStream inputStream)
            throws IOException, ClassNotFoundException {
        Request request = Request.login("Azam", "Suleiman");
        sendRequest(outputStream, request);
        Response response = receiveResponse(inputStream);
        System.out.println("Received response for LOGIN request: " + response);
    }

    private static void handleAddProductRequest(ObjectOutputStream outputStream, ObjectInputStream inputStream)
            throws IOException, ClassNotFoundException {
        Request request = Request.addProduct(ProductType.ELECTRONICS, "HD", 499.99, 2022,
                "Black", ProductCondition.New);
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
        Request request = Request.searchProduct("Electronics", 100, 1000, ProductCondition.Used);
        sendRequest(outputStream, request);
        Response response = receiveResponse(inputStream);
        System.out.println("Received response for SEARCH_PRODUCT request: " + response);
    }

    private static void handleSellProductRequest(ObjectOutputStream outputStream, ObjectInputStream inputStream)
            throws IOException, ClassNotFoundException {
        Request request = Request.sellProduct(1, "HD");
        sendRequest(outputStream, request);
        Response response = receiveResponse(inputStream);
        System.out.println("Received response for SELL_PRODUCT request: " + response);
    }

    private static void handleMakeOfferRequest(ObjectOutputStream outputStream, ObjectInputStream inputStream)
            throws IOException, ClassNotFoundException {
        Request request = Request.makeOffer(1, "Alice123", 200.00);
        sendRequest(outputStream, request);
        Response response = receiveResponse(inputStream);
        System.out.println("Received response for MAKE_OFFER request: " + response);
    }

    private static void handleRegisterInterestRequest(ObjectOutputStream outputStream, ObjectInputStream inputStream)
            throws IOException, ClassNotFoundException {
        Request request = Request.registerInterest("Electronics", "HD");
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
        return response;
    }
}


