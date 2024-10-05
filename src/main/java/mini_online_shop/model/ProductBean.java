package mini_online_shop.model;


import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductBean {
    private int id;
    private String name;
    private String description;
    private double price;
    private String imageURL;
    private Category category;
    private int quantity;
    private String status;
    
    private MultipartFile imageFile;
}
