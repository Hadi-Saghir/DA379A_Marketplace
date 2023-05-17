package controller.subcontrollers;

import controller.MainController;
import model.ServerHandler;
import shared.Request;
import shared.Response;
import shared.User;
import view.View;

import java.io.IOException;
import java.time.LocalDate;

public class ConnectionController {
    private final MainController mainController;
    private final ServerHandler serverHandler;
    private View view;


    public ConnectionController(MainController mainController, String host, int port) {
        this.mainController = mainController;
        this.serverHandler = new ServerHandler(host, port, this);
    }

    public void setView(View view) {
        this.view = view;
    }

    public void connectToServer() {
        serverHandler.connectToServer();
    }

    public void disconnectFromServer() {
        serverHandler.disconnectFromServer();
    }

    public boolean doLogin(String username, String password) throws IOException, ClassNotFoundException {
        Request request = Request.login(username, password);
        return serverHandler.sendRequest(request).RESPONSE_TYPE() == Response.ResponseType.SUCCESS;
    }


    public boolean doCreateNewUser(User user) throws IOException, ClassNotFoundException {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        LocalDate dateOfBirth = user.getDateOfBirth();
        String email = user.getEmailAddress();
        String username = user.getUsername();
        String password = user.getPassword();
        Request request = Request.register(firstName, lastName, dateOfBirth, email, username, password);
        return serverHandler.sendRequest(request).RESPONSE_TYPE() == Response.ResponseType.SUCCESS;
    }

    public void showMessage(String message) {
        view.showMessage(message);
    }

    public void showError(String message) {
        view.showError(message);
    }

    public void doProductSearch(String productType, double minPrice, double maxPrice, String searchCondition) {
        Request request = Request.searchProduct(productType, minPrice, maxPrice, searchCondition);
        try {
            Response products = serverHandler.sendRequest(request);
            // products.MESSAGE() //TODO
            // view.listProducts();
        } catch(IOException ex) {
            view.connectionError(ex.getMessage());
            mainController.buyProducts();
        } catch(ClassNotFoundException ex) {
            view.parseError(ex.getMessage());
            mainController.buyProducts();
        }
    }
}
