package handlers;
import Shared.src.shared.Product;
import Shared.src.shared.Request;
import Shared.src.shared.Response;
import java.io.IOException;
import java.util.List;

/**
 * This class will is part of the application of the chain of responsibility software design pattern
 * The purpose of this class is to handle requests of purchase*/

public class RequestHandler extends Handler {

    private final Database database;
    protected Handler nextHandler;
    public RequestHandler(Database database, Handler nextHandler){
        super(nextHandler);
        this.database = database;
    }

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }


    @Override
    public void handleRequest(Request request, ClientHandler clientHandler) {
        Response res = null;
        switch (request.getType().toString()) {
            case "REGISTER" ->  res = registerUser(request, clientHandler);
            case "LOGIN" ->  res = loginUser(request, clientHandler);
            case "ADD_PRODUCT" -> res =addProduct(request, clientHandler);
            case "ALL_PRODUCTS" -> res = getAllProducts(request, clientHandler);
            case "SEARCH_PRODUCT" -> res = searchProducts(request, clientHandler);
            case "SELL_PRODUCT" -> res = sellProduct(request, clientHandler);
            case "MAKE_OFFER" -> res = makeOffer(request, clientHandler);
            case "REGISTER_INTEREST" -> res =registerInterest(request, clientHandler);
            case "GET_PURCHASE_HISTORY" -> res = getPurchases(request, clientHandler);
            case "Get_Current_Offers" -> res = getCurrentOffers(request,clientHandler);
            default -> {
            }
        }
        try {
            clientHandler.writeToClient(res);
            setResponse(res);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Response registerUser(Request request, ClientHandler requester) {
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String dob = request.getDateOfBirth().toString();
        String email = request.getEmail();
        String username = request.getUsername();
        String password = request.getPassword();

        return database.registerUser(username, password, firstName, lastName, email, dob );
    }

    public Response addProduct(Request request, ClientHandler requester) {
        String seller = request.getUsername();
        String type = request.getProductType().toString();
        double price = request.getPrice();
        int year = request.getYearOfProduction();
        String color = request.getColor();
        String condition = String.valueOf(request.getCondition().getValue());
        return database.addProduct(seller, type, price, year, color, condition);
    }

    public Response loginUser(Request request, ClientHandler requester) {
        String username = request.getUsername();
        String password = request.getPassword();
        return database.loginUser(username, password);
    }

    public Response getAllProducts(Request request, ClientHandler requester) {
        return database.allProducts();
    }

    public Response searchProducts(Request request, ClientHandler requester) {
        String type = request.getSearchProductType().toString();
        double minPrice = request.getMinPrice();
        double maxPrice = request.getMaxPrice();
        String condition = String.valueOf(request.getSearchCondition().getValue());
        return database.searchProducts(type, minPrice, maxPrice, condition);
    }

    public Response sellProduct(Request request, ClientHandler requester) {
        String seller = request.getUsername();
        int offerId = request.getOfferId();
        int productid = request.getProductId();
        return database.sellProduct(seller, offerId,productid);
    }

    public Response makeOffer(Request request, ClientHandler requester) {
        String buyer = request.getUsername();
        int productId = request.getProductId();
        double price = request.getPrice();
        return database.makeOffer(productId,buyer, price);
    }

    public Response getPurchases(Request request, ClientHandler requester) {
        String buyer = request.getUserIdForHistory();
        String startDate = request.getStartDate().toString();
        String endDate = request.getEndDate().toString();
        return database.getPurchases(buyer, startDate, endDate);
    }

    public Response registerInterest(Request request, ClientHandler requester) {
        String buyer = request.getUsername();
        String type = request.getInterestedProductType();
        Double price = request.getPrice();
        return database.registerInterest(buyer, type,price);

    }

    public Response getCurrentOffers(Request request, ClientHandler clientHandler){
        return database.getCurrentOffers(request.getUsername());
    }


    public Response getNotifications(Request request) {
        String user = request.getUsername();
        return database.getNotifications(user);
    }



}
