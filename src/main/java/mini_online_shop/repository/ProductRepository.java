package mini_online_shop.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mini_online_shop.model.Category;
import mini_online_shop.model.ProductBean;

@Repository
public class ProductRepository {
    @Autowired
    private CategoryRepository categoryRepository; 
    
    public int insertProduct(ProductBean product) {
        String sql = "INSERT INTO product (name, description, price, imageURL, category_id, quantity) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getDescription());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setString(4, product.getImageURL()); // Use String for imageURL
            pstmt.setInt(5, product.getCategory().getId()); // Category ID from selected category
            pstmt.setInt(6, product.getQuantity());

            return pstmt.executeUpdate();  // Returns the number of rows affected

        } catch (SQLException e) {
            System.out.println("Error inserting product: " + e.getMessage());
            return 0;  
        }
    }
    
    public List<ProductBean> getAllProducts() {
        List<ProductBean> products = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.description, p.price, p.imageURL, p.quantity, "
                + "c.id AS category_id, c.name AS category_name "
                + "FROM product p "
                + "JOIN category c ON p.category_id = c.id "
                + "WHERE p.status = '1'"; 
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProductBean product = new ProductBean();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImageURL(rs.getString("imageURL")); // Retrieve as String
                product.setQuantity(rs.getInt("quantity"));
                
                // Setting category
                Category category = new Category();
                category.setId(rs.getInt("category_id"));
                category.setName(rs.getString("category_name"));
                product.setCategory(category);

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // You can replace this with better error handling
        }
        return products;
    }
    
    public ProductBean getProductById(int id) {
        String sql = "SELECT id, name, description, price, imageURL, category_id, quantity FROM product WHERE id = ?";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ProductBean product = new ProductBean();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setPrice(rs.getDouble("price"));
                    product.setImageURL(rs.getString("imageURL"));

                    // Fetch and set the category
                    int categoryId = rs.getInt("category_id");
                    Category category = categoryRepository.getCategoryById(categoryId);
                    product.setCategory(category);

                    product.setQuantity(rs.getInt("quantity")); // Set the quantity field
                    return product;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching product by ID: " + e.getMessage());
        }
        return null; // Return null if no product is found
    }
    
    public boolean doesProductExist(String productName) {
        String sql = "SELECT COUNT(*) FROM product WHERE name = ?";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, productName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error checking if product exists: " + e.getMessage());
        }
        return false;
    }
    
    public void softDeleteProduct(int id) {
        String sql = "UPDATE product SET status = '0' WHERE id = ?";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public int updateProduct(ProductBean product) {
        String sql = "UPDATE product SET name = ?, description = ?, price = ?, imageURL = ?, category_id = ?, quantity = ? WHERE id = ?";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getDescription());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setString(4, product.getImageURL());
            pstmt.setInt(5, product.getCategory().getId());
            pstmt.setInt(6, product.getQuantity());
            pstmt.setInt(7, product.getId());

            return pstmt.executeUpdate();  // Returns the number of rows affected

        } catch (SQLException e) {
            System.out.println("Error updating product: " + e.getMessage());
            return 0;  // Update failed
        }
    }
}
