package handlers;


import Shared.Shared.src.Product;
import Shared.Shared.src.Product.ProductType;
import Shared.Shared.src.Product.ProductState;

import Shared.Shared.src.Response;
import Shared.Shared.src.Response.ResponseType;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * This class will is part of the application of the chain of responsibility software design pattern
 * The purpose of this class is to handles queries to the database
 */
public class Database {
    private static final String DB_USER = "am4404";
    private static final String DB_PASSWORD = "qvbm0y1x";
    private static final String DB_URL = "jdbc:postgresql://pgserver.mau.se/mini";

    public Database() {

    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }


    public synchronized Response registerUser(String username, String password, String firstName, String lastName, String dob, String email) {
        String query = "INSERT INTO \"user\" (username, password, firstName, lastName, email, isloggedin, dob) VALUES (?, ?, ?, ?, ?, ?, ?)";

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
                return new Response(ResponseType.REGISTER, Response.ResponseResult.SUCCESS, null);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Response(ResponseType.REGISTER , Response.ResponseResult.SUCCESS, null);
    }

    public synchronized Response loginUser(String username, String password) {
        String query = "UPDATE \"user\" SET isLoggedIn = true WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);


            int res = pstmt.executeUpdate();
            if(res==1){
                return new Response(ResponseType.LOGIN, Response.ResponseResult.SUCCESS, null);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Response(ResponseType.LOGIN , Response.ResponseResult.SUCCESS, null);
    }


    public synchronized Response addProduct(String seller, String type, double price, int year, String color, String condition) {
        String query = "INSERT INTO product (productid, username, type, price, year, color, condition, state) VALUES (?, ?, ?, ?, ?, ?, ?,?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, 0);
            pstmt.setString(2, seller);
            pstmt.setString(3, type);
            pstmt.setDouble(4, price);
            pstmt.setInt(5, year);
            pstmt.setString(6, color);
            pstmt.setString(7, condition);
            pstmt.setString(8, "New");


            int res = pstmt.executeUpdate();
            if(res==1){
                return new Response(ResponseType.ADD_PRODUCT , Response.ResponseResult.SUCCESS, null);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Response(ResponseType.ADD_PRODUCT , Response.ResponseResult.SUCCESS, null);
    }


    public synchronized Response searchProducts(String type, double minPrice, double maxPrice, String condition) {
        List<Object> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE type = ? AND price BETWEEN ? AND ? AND condition = ?";

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
                        Product.ProductCondition.valueOf(rs.getString("condition")),
                        ProductState.valueOf(rs.getString("state"))
                );
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return new Response(ResponseType.LOGIN , Response.ResponseResult.SUCCESS, products);
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


    public Response sellProduct(int seller, int offerId) {
        String updateStatement = "UPDATE product SET state = 'SOLD' WHERE username = ? AND offerId = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateStatement)) {
            pstmt.setString(1, seller);
            pstmt.setInt(2, offerId);
            int rowsAffected = pstmt.executeUpdate();
            int res = pstmt.executeUpdate();
            if(res>0){
                return new Response(ResponseType.ADD_PRODUCT , Response.ResponseResult.SUCCESS, null);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Response(ResponseType.ADD_PRODUCT , Response.ResponseResult.SUCCESS, null);
    }

    public Response makeOffer(int buyer, int productId) {
        String query = "INSERT INTO offer (buyer, productid) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, buyer);
            pstmt.setInt(2, productId);
            int res = pstmt.executeUpdate();
            if(res==1){
                return new Response(ResponseType.ADD_PRODUCT , Response.ResponseResult.SUCCESS, null);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Response(ResponseType.ADD_PRODUCT , Response.ResponseResult.SUCCESS, null);
    }


    public Response getPurchases(int buyer, String startDate, String endDate) {
        return null;
    }

    public Response registerInterest(int user, String type) {
        return null;
    }

    public Response getNotifications(int user) {
        return null;
    }

    }


    public static void main(String[] args) {

        //dbHandler.registerUser("Alice123","Carrier123","Alice","Wattson","2001-12-21","test@testsson.gmail.com");

/*
        Database dbHandler = new Database();
        dbHandler.registerUser("janedoe","Carrier123","Alice","Wattson","2001-12-21","test@testsson.gmail.com");
        System.out.println(dbHandler.loginUser("janedoe","Tesst123"));
        List<Product> results = dbHandler.searchProducts("ELECTRONICS", 300.00, 800.00, "New");
        System.out.println("size: " + results.size());
        for (Product p:
                results) {
            System.out.println(p.getType());

        }

        //dbHandler.addProduct("Johns Doe", "Electronics", 99.99, 2022, "Black", "New");*/

    }


}
