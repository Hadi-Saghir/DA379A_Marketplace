package Test;

import Handlers.Database;
import Shared.src.shared.Product;
import Shared.src.shared.Response;
import Shared.src.shared.Response.*;
import Shared.src.shared.User;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;
import java.util.List;

import static Handlers.Database.getConnection;
import static org.junit.Assert.*;

public class DatabaseTest {
    private static Database db = new Database();
    @BeforeClass
    public static void setUp() throws SQLException {
        // Set up test database
         db = new Database();
    }

    @Test
    public void testRegisterUser() {
        Response response = db.registerUser("test123", "password", "firstName", "lastName", "dob", "email");
        assertEquals(ResponseResult.SUCCESS.toString(), response.RESPONSE_RESULT().toString());

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM \"user\" WHERE username = ?")) {
            pstmt.setString(1, "test123");
            ResultSet rs = pstmt.executeQuery();
            assertTrue(rs.next());
        } catch (SQLException e) {
            fail("SQLException thrown: " + e.getMessage());
        }
    }

    @Test
    public void testLoginUser() {
        Response response = db.loginUser("username", "password");
        assertEquals(ResponseResult.SUCCESS.toString(), response.RESPONSE_RESULT().toString());

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM \"user\" WHERE username = ?")) {
            pstmt.setString(1, "username");
            ResultSet rs = pstmt.executeQuery();
            assertTrue(rs.next());
            assertTrue(rs.getBoolean("isLoggedIn"));
        } catch (SQLException e) {
            fail("SQLException thrown: " + e.getMessage());
        }
    }

    @Test
    public void testAddProduct() {
        Response response = db.addProduct("seller", "type", 1.0, 2022, "color", "condition");
        assertEquals(ResponseResult.SUCCESS.toString(), response.RESPONSE_RESULT().toString());

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM product WHERE username = ?")) {
            pstmt.setString(1, "seller");
            ResultSet rs = pstmt.executeQuery();
            assertTrue(rs.next());
            assertEquals("type", rs.getString("type"));
            assertEquals(1.0, rs.getDouble("price"), 0.001);
            assertEquals(2022, rs.getInt("year"));
            assertEquals("color", rs.getString("color"));
            assertEquals("condition", rs.getString("condition"));
        } catch (SQLException e) {
            fail("SQLException thrown: " + e.getMessage());
        }
    }

    @Test
    public void testSearchProducts() {
        // Test 1, Products found




    }


    @Test
    public void testSearchProductsFound() {
        Response response = db.searchProducts("TOYS", 700, 900, "New");
        assertEquals(ResponseType.SEARCH_PRODUCT.toString(), response.RESPONSE_TYPE().toString());
        assertEquals(ResponseResult.SUCCESS, response.RESPONSE_RESULT());
        List<Object> products = response.MESSAGE();
        for (Object product : products) {
            assertTrue(product instanceof Product);
            Product p = (Product) product;
            assertEquals("TOYS", p.getType().toString());
            assertTrue(p.getPrice() >= 10 && p.getPrice() <= 20);
            assertEquals("new", p.getCondition().toString());
        }
    }

    @Test
    public void testSearchProductsFound2() {
        // Test 2, Products found
        Response response = db.searchProducts("ELECTRONICS", 300, 500, "New");
        assertEquals(ResponseType.SEARCH_PRODUCT, response.RESPONSE_TYPE());
        assertEquals(ResponseResult.SUCCESS, response.RESPONSE_RESULT());
        List<Object> products = response.MESSAGE();
        for (Object product : products) {
            assertTrue(product instanceof Product);
            Product p = (Product) product;
            assertEquals("ELECTRONICS", p.getType().toString());
            assertTrue(p.getPrice() >= 300 && p.getPrice() <= 500);
            assertEquals("New", p.getCondition().toString());
        }
    }

    @Test
    public void testSearchProductsNotFound() {
        // Test 2, Products not found
        Response response = db.searchProducts("TOYS", 600, 2000, "New");
        assertEquals(ResponseType.SEARCH_PRODUCT, response.RESPONSE_TYPE());
        assertEquals(ResponseResult.SUCCESS, response.RESPONSE_RESULT());
    }


}


