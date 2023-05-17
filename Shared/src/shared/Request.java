package Shared.src.shared;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;

    public Response getResponse() {
        return this.response;
    }

    // request types
    public enum RequestType {
        REGISTER,
        LOGIN,
        ADD_PRODUCT,
        ALL_PRODUCTS,
        SEARCH_PRODUCT,
        SELL_PRODUCT,
        Make_Offer,
        REGISTER_INTEREST,
        GET_PURCHASE_HISTORY
    }

    // request type and base constructor for factory method
    private RequestType type;

    private Response response;

    public Request(RequestType type) {
        this.type = type;
    }

    //Additional information
    private int[] concernedUserId; // this id help identify which user we need to notify


    // user data
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String username;
    private String password;

    // product data
    private Product.ProductType productType;
    private double price;
    private int yearOfProduction;
    private String color;
    private Product.ProductCondition condition;

    // search criteria
    private String searchProductType;
    private double minPrice;
    private double maxPrice;
    private Product.ProductCondition searchCondition;

    // buy product data
    private int productId;

    // buy product data
    private int offerId;

    // register interest data
    private String interestedProductType;
    private int userId;

    // purchase history data
    private int userIdForHistory;
    private LocalDate startDate;
    private LocalDate endDate;



    // factory methods for creating specific request types
    public static Request register(String firstName, String lastName, LocalDate dateOfBirth, String email,
                                   String username, String password) {
        Request request = new Request(RequestType.REGISTER);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setDateOfBirth(dateOfBirth);
        request.setEmail(email);
        request.setUsername(username);
        request.setPassword(password);
        return request;
    }

    public static Request login(String username, String password) {
        Request request = new Request(RequestType.LOGIN);
        request.setUsername(username);
        request.setPassword(password);
        return request;
    }

    public static Request addProduct(Product.ProductType productType, double price, int yearOfProduction,
                                     String color, Product.ProductCondition condition) {
        Request request = new Request(RequestType.ADD_PRODUCT);
        request.setProductType(productType);
        request.setPrice(price);
        request.setYearOfProduction(yearOfProduction);
        request.setColor(color);
        request.setCondition(condition);
        return request;
    }

    public static Request allProducts() {
        return new Request(RequestType.ALL_PRODUCTS);
    }

    public static Request searchProduct(String productType, double minPrice, double maxPrice, Product.ProductCondition searchCondition) {
        Request request = new Request(RequestType.SEARCH_PRODUCT);
        request.setSearchProductType(productType);
        request.setMinPrice(minPrice);
        request.setMaxPrice(maxPrice);
        request.setSearchCondition(searchCondition);
        return request;
    }

    public static Request sellProduct(int offerID, int sellerId) {
        Request request = new Request(RequestType.SELL_PRODUCT);
        request.setOfferId(offerID);
        request.setUserId(sellerId);
        return request;
    }

    public static Request makeOffer(int productId, int buyerId, double price) {
        Request request = new Request(RequestType.Make_Offer);
        request.setProductId(productId);
        request.setUserId(buyerId);
        request.setPrice(price);
        return request;
    }

    public static Request registerInterest(String interestedProductType, int userId) {
        Request request = new Request(RequestType.REGISTER_INTEREST);
        request.setInterestedProductType(interestedProductType);
        request.setUserId(userId);
        return request;
    }

    public static Request getPurchaseHistory(int userIdForHistory, LocalDate startDate, LocalDate endDate) {
        Request request = new Request(RequestType.GET_PURCHASE_HISTORY);
        request.setUserIdForHistory(userIdForHistory);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        return request;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Product.ProductType getProductType() {
        return productType;
    }

    public void setProductType(Product.ProductType productType) {
        this.productType = productType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Product.ProductCondition getCondition() {
        return condition;
    }

    public void setCondition(Product.ProductCondition condition) {
        this.condition = condition;
    }

    public String getSearchProductType() {
        return searchProductType;
    }

    public void setSearchProductType(String searchProductType) {
        this.searchProductType = searchProductType;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Product.ProductCondition getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(Product.ProductCondition searchCondition) {
        this.searchCondition = searchCondition;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getInterestedProductType() {
        return interestedProductType;
    }

    public void setInterestedProductType(String interestedProductType) {
        this.interestedProductType = interestedProductType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserIdForHistory() {
        return userIdForHistory;
    }

    public void setUserIdForHistory(int userIdForHistory) {
        this.userIdForHistory = userIdForHistory;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public int[] getConcernedUserId() {
        return concernedUserId;
    }

    public void setConcernedUserId(int[] concernedUserId) {
        this.concernedUserId = concernedUserId;
    }

    public RequestType getType() {
        return type;
    }
    public void setResponse(Response response) {
        this.response = response;
    }

}
