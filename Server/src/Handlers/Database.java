package Handlers;


import Shared.Notification;
import Shared.Product;
import Shared.Product.ProductType;
import Shared.Product.ProductState;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * This class will is part of the application of the chain of responsibility software design pattern
 * The purpose of this class is to handle queries to the database
 */
public class Database {
    private static final String DB_URL = "jdbc:mysql://aws.connect.psdb.cloud/miniproject?sslMode=VERIFY_IDENTITY";
    private static final String DB_USER = "gzc7fczvnm5de9xmp95j";
    private static final String DB_PASSWORD = "pscale_pw_U1oLxwSlAtMhQIOYywm0M1QJrqbUOtMUfSQzqapiGMz";

    public Database() {

    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }


    public static String registerUser(String username, String password, String firstName, String lastName, String dob, String email) {
        String query = "INSERT INTO user (username, password, firstName, lastName, dob, email) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, firstName);
            pstmt.setString(4, lastName);
            pstmt.setString(5, dob);
            pstmt.setString(6, email);

            int res = pstmt.executeUpdate();
            if(res==1){
                return username;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String loginUser(String username, String password) {
        String query = "UPDATE user SET isLoggedIn = true WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                return "Incorrect username or password";
            } else {
                return username;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }


    public boolean addProduct(String seller, String type, double price, int year, String color, String condition) {
        String query = "INSERT INTO product (productid, seller, type, price, year, color, condition) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, null);

            pstmt.setString(2, seller);
            pstmt.setString(3, type);
            pstmt.setDouble(4, price);
            pstmt.setInt(5, year);
            pstmt.setString(6, color);
            pstmt.setString(7, condition);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
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
                        rs.getString("seller"),
                        ProductType.valueOf(rs.getString("type")),
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

    public boolean makeOffer(int buyer, int productId) {
        return false;
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

    public String getUsername() {
        String username = "";
        return username;
    }

    public static void main(String[] args) {
        //Database dbHandler = new Database(null);
        //dbHandler.addProduct("Johns Doe", "Electronics", 99.99, 2022, "Black", "New");
        //dbHandler.registerUser("Alice123","Carrier123","Alice","Wattson","2001-12-21","test@testsson.gmail.com");
        //System.out.println(dbHandler.loginUser("Alice123","Tesst123"));
        /*List<Product> results = dbHandler.searchProducts("ELECTRONICS", 300.00, 800.00, "New");
        System.out.println("size: " + results.size());
        for (Product p:
             results) {
            System.out.println(p.getType());

        }
         */
    }


}
