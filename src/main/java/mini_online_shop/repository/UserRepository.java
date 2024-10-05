package mini_online_shop.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import mini_online_shop.model.LoginBean;
import mini_online_shop.model.User;


@Repository
public class UserRepository {

	public int insertUser(User user) {
	    String sql = "INSERT INTO user (name, email, password, role) VALUES (?, ?, ?, 'user')";
	    try (Connection conn = MyConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	    	 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	        pstmt.setString(1, user.getName());
	        pstmt.setString(2, user.getEmail());
	        pstmt.setString(3, passwordEncoder.encode(user.getPassword())); // Hashing the password

	        return pstmt.executeUpdate(); 

	    } catch (SQLException e) {
	        System.out.println("Error inserting user: " + e.getMessage());
	        return 0;  
	    }
	}
	
	 public boolean doesUserExist(String UserEamil) {
	        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
	        try (Connection conn = MyConnection.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setString(1,UserEamil);

	            try (ResultSet rs = pstmt.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getInt(1) > 0;
	                }
	            }

	        } catch (SQLException e) {
	            System.out.println("Error checking if email exists: " + e.getMessage());
	        }
	        return false;
	    }
	 
	 public boolean checkEmail(String email) {
			boolean check=false;
			Connection con = MyConnection.getConnection();
			String sql ="select * from user where email = ?";
			 try {
				PreparedStatement ps=con.prepareStatement(sql);
				ps.setString(1,email);
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()) {
					check=true;
				}
				
			} catch (SQLException e) {
				System.out.println("Check email:"+e.getMessage());
			}
			
			return check;
	
	
	 }
	 
	// Check email and hashed password for login
	 public User loginUser(LoginBean bean) {
		    User user = null;
		    Connection con = MyConnection.getConnection();
		    String sql = "SELECT * FROM user WHERE email = ?";

		    try {
		        PreparedStatement ps = con.prepareStatement(sql);
		        ps.setString(1, bean.getEmail());

		        ResultSet rs = ps.executeQuery();

		        if (rs.next()) {
		            String storedHashedPassword = rs.getString("password");

		            // Use BCrypt to compare the passwords
		            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		            if (passwordEncoder.matches(bean.getPassword(), storedHashedPassword)) {
		                user = new User();
		                user.setId(rs.getInt("id"));
		                user.setName(rs.getString("name"));
		                user.setEmail(rs.getString("email"));
		                user.setRole(rs.getString("role"));
		            }
		        }

		    } catch (SQLException e) {
		        System.out.println("Login user: " + e.getMessage());
		    }

		    return user;
		}
	 
	 public User findById(Integer id) {
	        String sql = "SELECT * FROM user WHERE id = ?";
	        try (Connection conn = MyConnection.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setInt(1, id);
	            ResultSet rs = pstmt.executeQuery();

	            if (rs.next()) {
	                User user = new User();
	                user.setId(rs.getInt("id"));
	                user.setName(rs.getString("name"));
	                user.setEmail(rs.getString("email"));
	                user.setRole(rs.getString("role"));
	                // Add other fields if necessary
	                return user;
	            }

	        } catch (SQLException e) {
	            System.out.println("Error fetching user by ID: " + e.getMessage());
	        }
	        return null; // Return null if no user found or error occurs
	    }
	
}
		

