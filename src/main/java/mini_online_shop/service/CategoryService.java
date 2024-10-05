package mini_online_shop.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mini_online_shop.model.Category;
import mini_online_shop.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
    private CategoryRepository categoryRepository;
	public int addCategory(Category category) {
	    return categoryRepository.addCategory(category);  
	}
	public List<Category> getAllCategories() {
        // You could apply any additional business logic here if needed
        return categoryRepository.getAllCategories();
    }
	 public Category getCategoryById(int id) {
		 return categoryRepository.getCategoryById(id);
	 }
	 

}
