package Shared;

import java.io.Serializable;
import java.time.LocalDate;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    // request types
    public enum RequestType {
        REGISTER,
        LOGIN,
        ADD_PRODUCT,
        SEARCH_PRODUCT,
        BUY_PRODUCT,
        Make_Offer,
        REGISTER_INTEREST,
        GET_PURCHASE_HISTORY
    }

    // request type and base constructor for factory method
    private RequestType type;

    public Request(RequestType type) {
        this.type = type;
    }

    public static final int REGISTER = 1;
    public static final int LOGIN = 2;
    public static final int ADD_PRODUCT = 3;
    public static final int SEARCH_PRODUCT = 4;
    public static final int BUY_PRODUCT = 5;
    public static final int REGISTER_INTEREST = 6;
    public static final int GET_PURCHASE_HISTORY = 7;



    // user data
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String username;
    private String password;

    // product data
    private String productType;
    private double price;
    private int yearOfProduction;
    private String color;
    private String condition;

    // search criteria
    private String searchProductType;
    private double minPrice;
    private double maxPrice;
    private String searchCondition;

    // buy product data
    private int productId;
    private int sellerId;

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

    public static Request addProduct(String productType, double price, int yearOfProduction,
                                     String color, String condition) {
        Request request = new Request(RequestType.ADD_PRODUCT);
        request.setProductType(productType);
        request.setPrice(price);
        request.setYearOfProduction(yearOfProduction);
        request.setColor(color);
        request.setCondition(condition);
        return request;
    }

    public static Request searchProduct(String productType, double minPrice, double maxPrice, String searchCondition) {
        Request request = new Request(RequestType.SEARCH_PRODUCT);
        request.setSearchProductType(productType);
        request.setMinPrice(minPrice);
        request.setMaxPrice(maxPrice);
        request.setSearchCondition(searchCondition);
        return request;
    }

    public static Request buyProduct(int productId, int sellerId) {
        Request request = new Request(RequestType.BUY_PRODUCT);
        request.setProductId(productId);
        request.setSellerId(sellerId);
        return request;
    }

    public static Request offer(int offerId, int buyerId) {
        Request request = new Request(RequestType.Make_Offer);
        request.setOfferId(offerId);
        request.setUserId(buyerId);
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

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
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

    public String getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
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

    public RequestType getType() {
        return type;
    }
}