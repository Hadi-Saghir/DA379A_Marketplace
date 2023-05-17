package controller.subcontrollers;

import controller.MainController;
import model.RequestHandler;
import model.ResponseHandler;
import model.ServerHandler;
import shared.Product;
import shared.Request;
import shared.Response;
import shared.User;
import view.View;

import java.io.IOException;
import java.time.LocalDate;

public class ConnectionController {
    private final MainController mainController;
    private View view;
    private final ServerHandler serverHandler;
    private ResponseHandler responseHandler;
    private RequestHandler requestHandler;


    public ConnectionController(MainController mainController, String host, int port) {
        this.mainController = mainController;
        this.serverHandler = new ServerHandler(host, port, this);
    }

    public void setView(View view) {
        this.view = view;
    }

    public void connectToServer() throws IOException {
        serverHandler.connectToServer();
        view.showMessage("Connected to server");

        requestHandler = new RequestHandler(mainController, serverHandler.getOut());
        responseHandler = new ResponseHandler(mainController, serverHandler.getIn());

        requestHandler.start();
        responseHandler.start();
    }

    public void disconnectFromServer() {
        try {
            serverHandler.disconnectFromServer();
            view.showMessage("Disconnected from server");
        } catch (Exception e) {
            view.showError("Error disconnecting from server");
            view.showMessage(e.getMessage());
        }
    }

    public void doLogin(String username, String password) {
        Request request = Request.login(username, password);
        requestHandler.sendRequest(request);
    }


    public void doCreateNewUser(User user) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        LocalDate dateOfBirth = user.getDateOfBirth();
        String email = user.getEmailAddress();
        String username = user.getUsername();
        String password = user.getPassword();
        Request request = Request.register(firstName, lastName, dateOfBirth, email, username, password);

        requestHandler.sendRequest(request);
    }

    public void doProductSearch(String productType, double minPrice, double maxPrice, String searchCondition) {
        Request request = Request.searchProduct(productType, minPrice, maxPrice, Product.ProductCondition.valueOf(searchCondition));
        requestHandler.sendRequest(request);
    }

    public void doAllProducts() {
        Request request = Request.allProducts();
        requestHandler.sendRequest(request);
    }

    public void makeOffer(Product product) {
        Request request = Request.makeOffer(product.getProductid(), mainController.getUserId(), product.getPrice());
        requestHandler.sendRequest(request);
    }
}
