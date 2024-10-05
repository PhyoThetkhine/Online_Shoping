package mini_online_shop.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class User {
	private int id;
	private String name;
	private String email;
	private String password;
	private String role;
	 private String PhotoURL;
	  private MultipartFile imageFile;

}
