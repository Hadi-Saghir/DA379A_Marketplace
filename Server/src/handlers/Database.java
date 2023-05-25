package handlers;

import Shared.src.shared.Product;
import Shared.src.shared.Product.*;
import Shared.src.shared.Request;
import Shared.src.shared.Response;
import Shared.src.shared.Response.*;

import javax.xml.crypto.Data;
import java.sql.*;
import java.sql.Date;
import java.util.*;


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


    public synchronized Response registerUser(String username, String password, String firstName, String lastName, String email, String dob) {
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
            if(res==1){
                fetchInterestedUsers(type);
                return new Response(ResponseType.ADD_PRODUCT , ResponseResult.SUCCESS, null);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Response(ResponseType.ADD_PRODUCT , ResponseResult.FAILURE, null);
    }

    public synchronized Response allProducts() {
        List<Object> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE state != 'SOLD'";

        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("Creating prod" +  rs.getInt("productid"));
                Product product = new Product(
                        rs.getInt("productid"),
                        rs.getString("username"),
                        ProductType.valueOf(rs.getString("type")),
                        rs.getDouble("price"),
                        rs.getInt("year"),
                        rs.getString("color"),
                        ProductCondition.fromValue(rs.getString("condition")),
                        ProductState.valueOf(rs.getString("state"))
                );
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new Response(ResponseType.ALL_PRODUCTS , ResponseResult.FAILURE, products);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new Response(ResponseType.ALL_PRODUCTS , ResponseResult.SUCCESS, products);
    }

    public synchronized Response searchProducts(String type) {
        List<Object> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE type ILIKE ? AND state != 'SOLD'";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, type+"%");
            System.out.println(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("productid"),
                        rs.getString("username"),
                        ProductType.valueOf(rs.getString("type")),
                        rs.getDouble("price"),
                        rs.getInt("year"),
                        rs.getString("color"),
                        ProductCondition.fromValue(rs.getString("condition")),
                        ProductState.valueOf(rs.getString("state"))
                );
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e);
            return new Response(ResponseType.SEARCH_PRODUCT , ResponseResult.FAILURE, products);
        }
        return new Response(ResponseType.SEARCH_PRODUCT , ResponseResult.SUCCESS, products);

    }
    public synchronized Response searchProducts(String type, double minPrice, double maxPrice) {
        List<Object> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE type ILIKE ? AND price BETWEEN ? AND ? AND state != 'SOLD'";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, type+"%");
            pstmt.setDouble(2, minPrice);
            pstmt.setDouble(3, maxPrice);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("productid"),
                        rs.getString("username"),
                        ProductType.valueOf(rs.getString("type")),
                        rs.getDouble("price"),
                        rs.getInt("year"),
                        rs.getString("color"),
                        ProductCondition.fromValue(rs.getString("condition")),
                        ProductState.valueOf(rs.getString("state"))
                );
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e);
            return new Response(ResponseType.SEARCH_PRODUCT , ResponseResult.FAILURE, products);
        }
        return new Response(ResponseType.SEARCH_PRODUCT , ResponseResult.SUCCESS, products);
    }

    public synchronized Response searchProducts(String type, double minPrice, double maxPrice, String condition) {
        List<Object> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE type ILIKE ? AND price BETWEEN ? AND ? AND condition ILIKE ? AND state != 'SOLD'";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, type+"%");
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
                        ProductCondition.fromValue(rs.getString("condition")),
                        ProductState.valueOf(rs.getString("state"))
                );
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e);
            return new Response(ResponseType.SEARCH_PRODUCT , ResponseResult.FAILURE, products);
        }
        return new Response(ResponseType.SEARCH_PRODUCT , ResponseResult.SUCCESS, products);

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


    public Response sellProduct(String seller, int offerId, int productid) {
        String updateStatement = "WITH offer_product AS (\n" +
                "  SELECT productId FROM offer WHERE offerId = ?\n" +
                "),\n" +
                "update_product AS (\n" +
                "  UPDATE product SET state = 'SOLD' WHERE productId = (SELECT productId FROM offer_product)\n" +
                ")\n" +
                "UPDATE offer SET approved = true WHERE offerId = ?;\n";
        ;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateStatement)) {
            //System.out.println("Statement: " + updateStatement);
            pstmt.setInt(1, offerId);
            pstmt.setInt(2, offerId);
            int res = pstmt.executeUpdate();
            if(res>0){
                System.out.println("in if where offerid: " + offerId);
                if(addToPurchaseHistory(productid,offerId)){
                    System.out.println("Added to history");
                    return new Response(ResponseType.SELL_PRODUCT , ResponseResult.SUCCESS, null);
                }
                return new Response(ResponseType.SELL_PRODUCT , ResponseResult.FAILURE, null);
            }
            else {
                System.out.println("Not in if");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Response(ResponseType.SELL_PRODUCT , ResponseResult.FAILURE, null);
    }

    public boolean addToPurchaseHistory(int productId,int offerId) {
        String username = getBuyer(offerId);
        String insertSql = "INSERT INTO purchasehistory (productId, username, offerId) VALUES (?, ?, ?)";
        System.out.println("TEst: " + insertSql);
        try (Connection conn = getConnection();
             PreparedStatement insertPstmt = conn.prepareStatement(insertSql)) {
            insertPstmt.setInt(1, productId);
            insertPstmt.setString(2, username);
            insertPstmt.setInt(3, offerId);
            insertPstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
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
        String testquery1 ="SELECT offerid FROM purchasehistory WHERE username = "+username+" AND date BETWEEN " +
                ""+Date.valueOf(startDate)+" AND "+Date.valueOf(endDate);
        System.out.println(testquery1);
        String sql = "SELECT productid FROM purchasehistory WHERE username = ? AND date BETWEEN ? AND ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setDate(2, Date.valueOf(startDate));
            pstmt.setDate(3, Date.valueOf(endDate));
            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    int productid = rs.getInt("productid");
                    System.out.println("Got offer " + productid);
                    String productSql = "SELECT * FROM product WHERE productid = ? ;";
                    try (PreparedStatement productPstmt = conn.prepareStatement(productSql)) {
                        productPstmt.setInt(1, productid);

                        try (ResultSet productRs = productPstmt.executeQuery()) {
                            System.out.println("Size: " + productRs.getFetchSize());
                            while (productRs.next()) {

                                productid = productRs.getInt("productid");
                                ProductType type = ProductType.valueOf(productRs.getString("type"));
                                double price = productRs.getDouble("price");
                                int year = productRs.getInt("year");
                                String color = productRs.getString("color");
                                ProductState state = ProductState.valueOf(productRs.getString("state"));
                                Product product = new Product(productid, username, type, price, year, color, ProductCondition.fromValue(productRs.getString("condition")), state);
                                purchases.add(product);
                                System.out.println("Created");
                            }
                            if (purchases.size() > 0) {
                                return new Response(ResponseType.GET_PURCHASE_HISTORY, ResponseResult.SUCCESS, Collections.singletonList(purchases));
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
        String query = "SELECT username FROM interest WHERE producttype = ?";

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
            return buyer;

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

    public synchronized Response getCurrentOffers(String username) {
        List<Object> products = new ArrayList<>();
        HashMap<Integer, Product> result = new HashMap<Integer, Product>();
        String query = " SELECT Offer.offerid,Product.productid,Product.type, Product.condition,Product.state,Product.year,Product.color, Offer.username,Offer.price\n" +
                "        FROM Offer\n" +
                "        JOIN Product\n" +
                "        ON Offer.productid = Product.productid\n" +
                "        WHERE Offer.username = ? \n" +
                "\t\tAND Product.state != 'Sold'\n" +
                "\t\tAND Offer.approved = false";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            System.out.println(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int offerid = rs.getInt("offerid");
                Product product = new Product(
                        rs.getInt("productid"),
                        rs.getString("username"),
                        ProductType.valueOf(rs.getString("type")),
                        rs.getDouble("price"),
                        rs.getInt("year"),
                        rs.getString("color"),
                        ProductCondition.fromValue(rs.getString("condition")),
                        ProductState.valueOf(rs.getString("state"))
                );
                result.put(offerid,product);
            }
        } catch (SQLException e) {
            System.out.println(e);
            return new Response(ResponseType.GET_CURRENT_OFFERS , ResponseResult.FAILURE, Collections.singletonList(result));
        }
        System.out.println("Done");
        return new Response(ResponseType.GET_CURRENT_OFFERS , ResponseResult.SUCCESS, Collections.singletonList(result));

    }



    public static void main(String[] args) {


        //dbHandler.registerUser("Alice123","Carrier123","Alice","Wattson","2001-12-21","test@testsson.gmail.com");

/*
 ;

        dbHandler.registerUser("janedoe","Carrier123","Alice","Wattson","2001-12-21","test@testsson.gmail.com");
        System.out.println(dbHandler.loginUser("janedoe","Tesst123"));
        List<shared.Product> results = dbHandler.searchProducts("ELECTRONICS", 300.00, 800.00, "New");
        System.out.println("size: " + results.size());
        for (shared.Product p:
                results) {
            System.out.println(p.getType());

        }


        */
        Database db = new Database();
        //System.out.println(db.searchProducts("spor",100,1000));
        //List<?> s = db.getPurchases("Alice123","2023-01-01","2023-05-27").MESSAGE();

        //System.out.println("Res" + db.getCurrentOffers("Alice123"));

        db.addToPurchaseHistory(62,26);
        //db.sellProduct("JohnnyBoy",28,57);
        //System.out.println("Res: " + db.getBuyer(27));
        //db.makeOffer(57,"Batman",900);


    }
}
