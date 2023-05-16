package Handlers;


import Shared.Notification;
import Shared.Product;
import Shared.Product.ProductType;
import Shared.Product.ProductState;

import Shared.Response.ResponseType;



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


    public static ResponseType registerUser(String username, String password, String firstName, String lastName, String dob, String email) {
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
                return ResponseType.SUCCESS;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ResponseType.FAILURE;
    }

    public ResponseType loginUser(String username, String password) {
        String query = "UPDATE \"user\" SET isLoggedIn = true WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                return ResponseType.FAILURE;
            } else {
                return ResponseType.SUCCESS;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return ResponseType.FAILURE;
        }
    }


    public ResponseType addProduct(String seller, String type, double price, int year, String color, String condition) {
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
                return ResponseType.SUCCESS;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ResponseType.FAILURE;
    }


    public List<Product> searchProducts(String type, double minPrice, double maxPrice, String condition) {
        List<Product> products = new ArrayList<>();
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
                        ProductType.valueOf(rs.getString("type")),
                        rs.getString("username"),
                        rs.getDouble("price"),
                        rs.getInt("year"),
                        rs.getString("color"),
                        rs.getString("condition"),
                        ProductState.valueOf(rs.getString("state"))
                );
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return products;
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


    public boolean sellProduct(int seller, int offerId) {
        return false;
    }

    public ResponseType makeOffer(int buyer, int productId) {
        String query = "INSERT INTO offer (buyer, productid) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, buyer);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();
            return ResponseType.SUCCESS;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ResponseType.FAILURE;
    }


    public List<Product> getPurchases(int buyer, String startDate, String endDate) {
        return null;
    }

    public boolean registerInterest(int user, String type) {
        return false;
    }

    public List<Notification> getNotifications(int user) {
        return null;
    }

    public boolean addNotification(String user, String message) {
        return false;
    }

    public static void main(String[] args) {

        //dbHandler.registerUser("Alice123","Carrier123","Alice","Wattson","2001-12-21","test@testsson.gmail.com");


        Database dbHandler = new Database();
        dbHandler.registerUser("janedoe","Carrier123","Alice","Wattson","2001-12-21","test@testsson.gmail.com");
        //System.out.println(dbHandler.loginUser("janedoe","Tesst123"));
        List<Product> results = dbHandler.searchProducts("ELECTRONICS", 300.00, 800.00, "New");
        System.out.println("size: " + results.size());
        for (Product p:
                results) {
            System.out.println(p.getType());

        }


        //dbHandler.addProduct("Johns Doe", "Electronics", 99.99, 2022, "Black", "New");

    }


}
