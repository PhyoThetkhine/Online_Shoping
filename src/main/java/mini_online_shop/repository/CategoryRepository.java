package mini_online_shop.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import mini_online_shop.model.Category;

@Repository
public class CategoryRepository {

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM category";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                categories.add(category);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching categories: " + e.getMessage());
        }
        return categories;
    }
       
    
    public int addCategory(Category category) {
        int i = 0;
        String sql = "INSERT INTO category (name) VALUES (?)";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, category.getName());
            i = pstmt.executeUpdate(); 

        } catch (SQLException e) {
            System.out.println("Error adding category: " + e.getMessage());
        }
        return i;
    }
    
    public Category getCategoryById(int id) {
        String sql = "SELECT id, name FROM category WHERE id = ?";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Category category = new Category();
                    category.setId(rs.getInt("id"));
                    category.setName(rs.getString("name"));
                    return category;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching category by ID: " + e.getMessage());
        }
        return null; // Return null if no category is found
    }

}
