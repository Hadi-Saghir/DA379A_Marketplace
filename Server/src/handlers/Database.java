package handlers;

import Shared.src.shared.Product;
import Shared.src.shared.Product.*;
import Shared.src.shared.Request;
import Shared.src.shared.Response;
import Shared.src.shared.Response.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * This class will is part of the application of the chain of responsibility software design pattern
 * The purpose of this class is to handles queries to the database
 */
public class Database {
    private static final String DB_USER = "am4404";
    private static final String DB_PASSWORD = "qvbm0y1x";
    private static final String DB_URL = "jdbc:postgresql://pgserver.mau.se/mini";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }


    public synchronized Response registerUser(String username, String password, String firstName, String lastName, String dob, String email) {
        String query = "INSERT INTO \"users\" (username, password, firstName, lastName, email, isloggedin, dob) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, firstName);
            pstmt.setString(4, lastName);
            pstmt.setString(5, email);
            pstmt.setBoolean(6, false);
            pstmt.setString(7, dob);

            int res = pstmt.executeUpdate();
            if(res==1){
                return new Response(ResponseType.REGISTER, ResponseResult.SUCCESS, null);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Response(ResponseType.REGISTER , ResponseResult.FAILURE, null);
    }

    public synchronized Response loginUser(String username, String password) {
        String query = "UPDATE \"users\" SET isLoggedIn = true WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);


            int res = pstmt.executeUpdate();
            if(res==1){
                return new Response(ResponseType.LOGIN, ResponseResult.SUCCESS, null);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Response(ResponseType.LOGIN , ResponseResult.FAILURE, null);
    }


    public synchronized Response addProduct(String seller, String type, double price, int year, String color, String condition) {
        String query = "INSERT INTO product (username, type, price, year, color, condition, state) VALUES (?, ?, ?, ?, ?, ?,?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, seller);
            pstmt.setString(2, type);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, year);
            pstmt.setString(5, color);
            pstmt.setString(6, condition);
            pstmt.setString(7, "AVAILABLE");


            int res = pstmt.executeUpdate();
            System.out.println("Database returned " + res);
            if(res==1){
                fetchInterestedUsers(type);
                return new Response(ResponseType.ADD_PRODUCT , ResponseResult.SUCCESS, null);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("im here");
        return new Response(ResponseType.ADD_PRODUCT , ResponseResult.FAILURE, null);
    }

    public synchronized Response allProducts() {
        List<Object> products = new ArrayList<>();
        String query = "SELECT * FROM product";

        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("productid"),
                        rs.getString("username"),
                        ProductType.valueOf(rs.getString("type")),
                        rs.getDouble("price"),
                        rs.getInt("year"),
                        rs.getString("color"),
                        ProductCondition.valueOf(rs.getString("condition")),
                        ProductState.valueOf(rs.getString("state"))
                );
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new Response(ResponseType.ALL_PRODUCTS , ResponseResult.FAILURE, null);
        }

        return new Response(ResponseType.ALL_PRODUCTS , ResponseResult.FAILURE, products);
    }

    public synchronized Response searchProducts(String type, double minPrice, double maxPrice, String condition) {
        System.out.println("DB: Searching");
        System.out.println("Type: " + type);
        System.out.println("minPrice: " + minPrice);
        System.out.println("maxPrice: " + maxPrice);
        System.out.println("condition: " + condition);

        List<Object> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE type ILIKE ? AND price BETWEEN ? AND ? AND condition ILIKE ?";
        System.out.println("Built query: " + query);
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, type);
            pstmt.setDouble(2, minPrice);
            pstmt.setDouble(3, maxPrice);
            pstmt.setString(4, condition);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("productid"),
                        rs.getString("username"),
                        ProductType.valueOf(rs.getString("type")),
                        rs.getDouble("price"),
                        rs.getInt("year"),
                        rs.getString("color"),
                        ProductCondition.valueOf(rs.getString("condition")),
                        ProductState.valueOf(rs.getString("state"))
                );
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e);
            return new Response(ResponseType.SEARCH_PRODUCT , ResponseResult.FAILURE, products);
        }
        if(products.size()>0){
            return new Response(ResponseType.SEARCH_PRODUCT , ResponseResult.SUCCESS, products);

        } else {
            return new Response(ResponseType.SEARCH_PRODUCT , ResponseResult.FAILURE, products);
        }
    }


    public boolean checkLogin(String username, String password) {
        boolean isValid = false;
        String query = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    isValid = (count > 0);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return isValid;
    }


    public Response sellProduct(String seller, int offerId) {
        String updateStatement = "UPDATE product SET state = 'SOLD' WHERE username = ? AND offerId = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateStatement)) {
            pstmt.setString(1, seller);
            pstmt.setInt(2, offerId);
            int rowsAffected = pstmt.executeUpdate();
            int res = pstmt.executeUpdate();
            if(res>0){
                return new Response(ResponseType.SELL_PRODUCT , ResponseResult.SUCCESS, null);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Response(ResponseType.SELL_PRODUCT , ResponseResult.SUCCESS, null);
    }

    public Response makeOffer(int productId,String buyer, double price) {
        String query = "INSERT INTO offer (productid, username, approved, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, productId);
            pstmt.setString(2, buyer);
            pstmt.setBoolean(3,false);
            pstmt.setDouble(4, price);
            int res = pstmt.executeUpdate();
            if(res==1){
                System.out.println("Ending in succ");
                return new Response(ResponseType.MAKE_OFFER , ResponseResult.SUCCESS, null);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Ending in fail");
        return new Response(ResponseType.MAKE_OFFER , ResponseResult.FAILURE, null);
    }


    public Response getPurchases(String username, String startDate, String endDate) {
        System.out.println("Searching " + endDate);
        List<Product> purchases = new ArrayList<>();
        String sql = "SELECT offerid FROM purchasehistory WHERE username = ? AND date BETWEEN ? AND ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setDate(2, Date.valueOf(startDate));
            pstmt.setDate(3, Date.valueOf(endDate));
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("Got offer " + rs.getFetchSize());
                while (rs.next()) {
                    int offerid = rs.getInt("offerid");
                    String productSql = "SELECT * FROM product WHERE productid IN (SELECT productid FROM offer WHERE offerid = ?)";
                    try (PreparedStatement productPstmt = conn.prepareStatement(productSql)) {
                        productPstmt.setInt(1, offerid);

                        try (ResultSet productRs = productPstmt.executeQuery()) {
                            System.out.println("Size: " + productRs);
                            while (productRs.next()) {

                                int productid = productRs.getInt("productid");
                                ProductType type = ProductType.valueOf(productRs.getString("type"));
                                double price = productRs.getDouble("price");
                                int year = productRs.getInt("year");
                                String color = productRs.getString("color");
                                String condition = productRs.getString("condition");
                                ProductState state = ProductState.valueOf(productRs.getString("state"));
                                Product product = new Product(productid, username, type, price, year, color, ProductCondition.valueOf(condition), state);
                                purchases.add(product);
                                System.out.println("Created");
                            }
                            if (purchases.size() > 0) {
                                return new Response(ResponseType.GET_PURCHASE_HISTORY, ResponseResult.SUCCESS, null);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Response(ResponseType.GET_PURCHASE_HISTORY, ResponseResult.FAILURE, Collections.singletonList(purchases));
    }


    public Response registerInterest(String username, String productType, Double state) {
        String sql = "INSERT INTO interest (username, productType, state) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, productType);
            pstmt.setDouble(3, state);
            int res = pstmt.executeUpdate();
            if (res > 0) {
                return new Response(ResponseType.REGISTER_INTEREST, ResponseResult.SUCCESS, null);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new Response(ResponseType.REGISTER_INTEREST, ResponseResult.FAILURE, null);
    }

    public List<String> fetchInterestedUsers(String type) {
        List<String> interestedUsers = new ArrayList<>();
        String query = "SELECT username FROM interest WHERE type = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, type.toString());

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                interestedUsers.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return interestedUsers;
    }

    public Response getNotifications(String user) {
        return null;
    }

    public void addNotification(String user, String message) {
        String sql = "INSERT INTO notification (username, message) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user);
            pstmt.setString(2, message);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getBuyer(int offerId) {
        String buyer = null;
        String query = "SELECT username FROM offer WHERE offerId = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, offerId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                buyer = rs.getString("username");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return buyer;
    }

    public String getSeller(int productId) {
        String seller = null;
        String query = "SELECT username FROM product WHERE productId = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, productId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                seller = rs.getString("username");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return seller;
    }


    public static void main(String[] args) {

        //dbHandler.registerUser("Alice123","Carrier123","Alice","Wattson","2001-12-21","test@testsson.gmail.com");

/*

        dbHandler.registerUser("janedoe","Carrier123","Alice","Wattson","2001-12-21","test@testsson.gmail.com");
        System.out.println(dbHandler.loginUser("janedoe","Tesst123"));
        List<shared.Product> results = dbHandler.searchProducts("ELECTRONICS", 300.00, 800.00, "New");
        System.out.println("size: " + results.size());
        for (shared.Product p:
                results) {
            System.out.println(p.getType());

        }


        */

    }
}
