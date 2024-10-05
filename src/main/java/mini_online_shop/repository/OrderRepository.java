package mini_online_shop.repository;
import mini_online_shop.model.order;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import java.sql.SQLException;


public class OrderRepository {
    public int insertOrder(order order) {
        String sql = "INSERT INTO `orders` (date, totalamount, status, user_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, new Date(order.getDate().getTime())); // Convert java.util.Date to java.sql.Date
            pstmt.setDouble(2, order.getTotalAmount());
            pstmt.setString(3, order.getStatus());
            pstmt.setInt(4, order.getUserId().getId());

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting order: " + e.getMessage());
            return 0;
        }
    }
}