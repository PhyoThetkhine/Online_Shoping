package mini_online_shop.controller;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import mini_online_shop.model.Category;
import mini_online_shop.model.ProductBean;

import mini_online_shop.service.CategoryService;
import mini_online_shop.service.ProductService;

@Controller
public class ProductController {
	@Autowired
    private final CategoryService categoryService;
	@Autowired
    private final ProductService productService;
	@Autowired
    private Cloudinary cloudinary;

	
   

    public ProductController(CategoryService categoryService, ProductService productService) {
		super();
		this.categoryService = categoryService;
		this.productService = productService;
	}

    @GetMapping("/showProductForm")
    public String showAddProductForm(Model model, HttpSession session) {
        if (isLoggedIn(session)) {
            List<Category> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("product", new ProductBean());
            return "addProduct";
        } else {
            return "redirect:/";
        }
    }
    
    
    @RequestMapping(value = "/addingProduct", method = RequestMethod.POST)
    public String addProduct(@ModelAttribute ProductBean product,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            String imageUrl = handleImageUpload(product.getImageFile());
            if (imageUrl != null) {
                product.setImageURL(imageUrl);
            }

            int result = productService.insertProduct(product);
            if (result > 0) {
                redirectAttributes.addFlashAttribute("message", "Product added successfully!");
                return "redirect:/products";
            } else if (result == -1) {
                model.addAttribute("error", "Product already exists. Please choose a different name.");
            } else {
                model.addAttribute("error", "Failed to add product. Please try again.");
            }
        } catch (IOException e) {
            model.addAttribute("error", "Error uploading image: " + e.getMessage());
        }

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "addProduct";
    }

    
    
    
    
    
     @GetMapping("/products")
    public ModelAndView showAllProducts(Model model, HttpSession session) {
        if (isLoggedIn(session)) {
            List<ProductBean> products = productService.getAllProducts();
            model.addAttribute("products", products);
            return new ModelAndView("productList");
        } else {
            return new ModelAndView("redirect:/");
        }
    }
    
    
    
    
    
     @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("id") int productId, RedirectAttributes redirectAttributes, HttpSession session) {
        if (isLoggedIn(session)) {
            productService.softDeleteProduct(productId);
            redirectAttributes.addFlashAttribute("message", "Product deleted successfully!");
            return "redirect:/products";
        } else {
            return "redirect:/";
        }
    }

    
    
     @GetMapping("/editProduct")
    public String showEditForm(@RequestParam("id") int id, Model model, HttpSession session) {
        if (isLoggedIn(session)) {
            ProductBean product = productService.getProductById(id);
            List<Category> categories = categoryService.getAllCategories();
            model.addAttribute("product", product);
            model.addAttribute("categories", categories);
            return "editProduct";
        } else {
            return "redirect:/";
        }
    }
    
    
    
        @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    public String updateProduct(@ModelAttribute ProductBean product,
                                @RequestParam("existingImageURL") String existingImageURL,
                                Model model,
                                RedirectAttributes redirectAttributes, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/";

        try {
            String imageUrl = handleImageUpload(product.getImageFile());
            if (imageUrl != null) {
                product.setImageURL(imageUrl);
            } else {
                product.setImageURL(existingImageURL);
            }

            int result = productService.updateProduct(product);
            if (result > 0) {
                redirectAttributes.addFlashAttribute("message", "Product updated successfully!");
                return "redirect:/products";
            } else {
                model.addAttribute("error", "Failed to update product. Please try again.");
            }

        } catch (IOException e) {
            model.addAttribute("error", "Error uploading image: " + e.getMessage());
        }

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "editProduct";
    }

    
    
    
    private boolean isLoggedIn(HttpSession session) {
        String email = (String) session.getAttribute("email");
        return email != null;
    }
    
    
     private String handleImageUpload(MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("secure_url");
        }
        return null;
    }
    
}
