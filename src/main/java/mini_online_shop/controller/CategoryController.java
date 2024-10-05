package mini_online_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import mini_online_shop.model.Category;
import mini_online_shop.service.CategoryService;

@Controller
public class CategoryController {
	@Autowired
    private CategoryService categoryService;
	 @GetMapping("/showAddcategoryForm")
	    public String showAddCategoryForm(Model model) {
	        model.addAttribute("category", new Category());
	        return "add-category"; 
	    }

	    @PostMapping("/addCategory")
	    public String addCategory(@ModelAttribute Category category, Model model) {
	        int result = categoryService.addCategory(category);

	        if (result > 0) {
	            return "sucess"; 
	        } else {
	            model.addAttribute("error", "Failed to add category. Please try again.");
	            return "add-category";
	        }
	    }
	}
