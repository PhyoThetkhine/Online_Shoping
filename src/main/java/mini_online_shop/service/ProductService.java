package mini_online_shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mini_online_shop.model.ProductBean;
import mini_online_shop.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	
	public int insertProduct(ProductBean product) {
        if (productRepository.doesProductExist(product.getName())) {
            // Product already exists
            return -1; // Indicate failure due to existing product
        }
        return productRepository.insertProduct(product);
    }
	
	
	 
	 public ProductBean getProductById(int id) {
		 return productRepository.getProductById(id);
	 }
	 
	 
	 public List<ProductBean> getAllProducts() {
		 return productRepository.getAllProducts();
	 }
	 
	 
	 public void softDeleteProduct(int id) {
		 productRepository.softDeleteProduct(id);
	 }
	 
	 public int updateProduct(ProductBean product) {
		 return productRepository.updateProduct(product);
	 }

}
