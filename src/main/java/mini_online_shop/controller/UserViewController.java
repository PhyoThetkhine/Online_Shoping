package mini_online_shop.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mini_online_shop.model.ProductBean;
import mini_online_shop.model.User;
import mini_online_shop.model.order;
import mini_online_shop.repository.OrderRepository;
import mini_online_shop.service.ProductService;
import mini_online_shop.service.UserService;
import mini_online_shop.model.Cart;
import mini_online_shop.model.CartItem;

@Controller
public class UserViewController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;  
    @GetMapping("/home")
    public String showHome(Model model) {
    	
                List<ProductBean> allProducts = productService.getAllProducts();
        
                model.addAttribute("products", allProducts);
        
        return "home";     }

    @GetMapping("/productDetails")
    public String getProductDetails(@RequestParam("id") int id, Model model) {
        ProductBean product = productService.getProductById(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "productDetails";        
            } 
        else {
            model.addAttribute("message", "Product not found.");
            return "redirect:/productList";        
            }
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("productId") int productId, HttpSession session, Model model) {
 
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }

       
        ProductBean product = productService.getProductById(productId);
        if (product != null && product.getQuantity() > 0) {
            cart.addProduct(product);
            session.setAttribute("cart", cart);
            model.addAttribute("message", "Product added to cart successfully.");

          
            System.out.println("Cart Contents After Adding:");
            for (CartItem item : cart.getItems().values()) {
                System.out.println("Product ID: " + item.getProduct().getId() +
                                   ", Name: " + item.getProduct().getName() +
                                   ", Quantity: " + item.getQuantity());
            }
        } else {
            model.addAttribute("message", "Product is unavailable.");
        }

    
        return "redirect:/productDetails?id=" + productId;
    }

    @GetMapping("/viewCart")
    public String viewCart(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        model.addAttribute("cart", cart);
        model.addAttribute("itemsList", cart.getItems().values());
        return "viewCart"; 
    }

    @PostMapping("/removeFromCart")
    public String removeFromCart(@RequestParam("productId") int productId, HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.removeProduct(productId);
            session.setAttribute("cart", cart);
            model.addAttribute("message", "Product removed from cart successfully.");
        }
        return "redirect:/viewCart";
    }

    @PostMapping("/OrderPlace")
    public String placeOrder(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        
        if (cart == null || cart.getItems().isEmpty()) {
            model.addAttribute("message", "Your cart is empty.");
            return "redirect:/viewCart";
        }

       
        order newOrder = new order();
        newOrder.setDate(new java.util.Date()); 
        newOrder.setTotalAmount(cart.getTotalPrice());
        newOrder.setStatus("Pending");
        
    
        Integer currentUserId = (Integer) session.getAttribute("currentUserId");

        if (currentUserId != null) {
            
            User currentUser = userService.getUserById(currentUserId);  
            newOrder.setUserId(currentUser); 
        } else {
            model.addAttribute("message", "User not logged in.");
            return "redirect:/"; 
        }

     
        OrderRepository orderRepo = new OrderRepository();
        int result = orderRepo.insertOrder(newOrder);
        
        if (result > 0) {
            session.removeAttribute("cart"); 
            model.addAttribute("message", "Order placed successfully.");
        } else {
            model.addAttribute("message", "Failed to place order.");
        }

        return "redirect:/home"; 
    }
}