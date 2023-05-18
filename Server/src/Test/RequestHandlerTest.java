package Test;
/*

import Handlers.*;
import Shared.src.shared.Request;
import Shared.src.shared.Request.*;
import Shared.src.shared.User;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;




           case "REGISTER" ->  res = registerUser(request, clientHandler);
            case "LOGIN" ->  res = loginUser(request, clientHandler);
            case "ADD_PRODUCT" -> res =addProduct(request, clientHandler);
            case "SEARCH_PRODUCT" -> res = searchProducts(request, clientHandler);
            case "SELL_PRODUCT" -> res = sellProduct(request, clientHandler);
            case "MAKE_OFFER" -> res = makeOffer(request, clientHandler);
            case "REGISTER_INTEREST" -> res =registerInterest(request, clientHandler);
            case "GET_PURCHASE_HISTORY" -> res = getPurchases(request, clientHandler);


import Shared.src.shared.Response;
import Shared.src.shared.Response.*;

import static org.junit.Assert.*;

public class RequestHandlerTest {


    private static Database db = new Database();
    private static RequestHandler requestHandler;

    @BeforeClass
    public static void setUp() throws SQLException, IOException {
        // Set up test database
        db = new Database();
         requestHandler = new RequestHandler(db,null);

    }

    @Test
    public void testRegisterUserSuccess() {
        // Create a new request with valid user information
        Request request = new Request(RequestType.REGISTER);
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setDateOfBirth(LocalDate.of(1990, 1, 1));
        request.setEmail("john.doe@example.com");
        request.setUsername("johndoe");
        request.setPassword("password123");

        // Call the registerUser method with the request

        Response response = db.registerUser(request.getUsername(),request.getPassword(),
                request.getFirstName(), request.getLastName(), request.getDateOfBirth().toString(),
                request.getEmail() );

        // Verify that the response type is REGISTER
        assertEquals(Response.ResponseType.REGISTER, response.RESPONSE_TYPE());

        // Verify that the response result is SUCCESS
        assertEquals(Response.ResponseResult.SUCCESS, response.RESPONSE_RESULT());
    }



    @Test
    public void testRegisterUserFail() {
        // Create a new request with valid user information
        Request request = new Request(RequestType.REGISTER);
        request.setFirstName("Alice");
        request.setLastName("Doe");
        request.setDateOfBirth(LocalDate.of(1990, 1, 1));
        request.setEmail("john.doe@example.com");
        request.setUsername("johndoe");
        request.setPassword("password123");

        // Call the registerUser method with the request

        Response response = requestHandler.registerUser(request,null);

        // Verify that the response type is REGISTER
        assertEquals(ResponseType.REGISTER, response.RESPONSE_TYPE());

        // Verify that the response result is SUCCESS
        assertEquals(ResponseResult.FAILURE, response.RESPONSE_RESULT());
    }






    @Test
    public void ADD_PRODUCT() throws IOException {

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
*/

