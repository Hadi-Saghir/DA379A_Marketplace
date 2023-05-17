package Test;

import Handlers.*;
import Shared.src.shared.Request;
import Shared.src.shared.Request.*;
import Shared.src.shared.User;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

/*
           case "REGISTER" ->  res = registerUser(request, clientHandler);
            case "LOGIN" ->  res = loginUser(request, clientHandler);
            case "ADD_PRODUCT" -> res =addProduct(request, clientHandler);
            case "SEARCH_PRODUCT" -> res = searchProducts(request, clientHandler);
            case "SELL_PRODUCT" -> res = sellProduct(request, clientHandler);
            case "MAKE_OFFER" -> res = makeOffer(request, clientHandler);
            case "REGISTER_INTEREST" -> res =registerInterest(request, clientHandler);
            case "GET_PURCHASE_HISTORY" -> res = getPurchases(request, clientHandler);
 */

import Shared.src.shared.Response;
import Shared.src.shared.Response.*;

import static org.junit.Assert.*;

public class RequestHandlerTest {


    private static Database db = new Database();
    @BeforeClass
    public static void setUp() throws SQLException, IOException {
        // Set up test database
        db = new Database();
        startServer();
    }

    public static void startServer(){
        ServerController serverController = null;
        try {
            serverController = new ServerController(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread thread = new Thread(serverController);
        thread.start();
    }

    public boolean connectClient(){

        return false;
    }
    @Test
    public void testRegisterUser() throws IOException {
        // Create a stub database object with the expected response
        Response expectedResponse = new Response(ResponseType.REGISTER, ResponseResult.SUCCESS, null);
        // Create a Request object with test data
        Request request = new Request(RequestType.REGISTER);
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setDateOfBirth(LocalDate.of(2000, 1, 1));
        request.setEmail("john.doe@example.com");
        request.setUsername("johndoe");
        request.setPassword("password");
        ClientHandler clientHandler = new ClientHandler(new Client(new Socket()));
        Handler handler = clientHandler.getHandler();
        ResponseType responseType = handler.handle(request,clientHandler);




        // Call the registerUser method with the test data

        // Verify that the response is as expected
        assertEquals(expectedResponse, actualResponse);
    }





    @Test
    public void ADD_PRODUCT() {

    }

    @Test
    public void SEARCH_PRODUCT() {

    }

    @Test
    public void SELL_PRODUCT() {

    }

    @Test
    public void MAKE_OFFER() {

    }

    @Test
    public void REGISTER_INTEREST() {

    }

    @Test
    public void GET_PURCHASE_HISTORY() {

    }

}


