package mini_online_shop.controller;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import mini_online_shop.model.User;
import mini_online_shop.service.EmailService;
import mini_online_shop.service.OtpService;
import mini_online_shop.service.OtpStorageService;
import mini_online_shop.service.UserService;

@Controller
public class RegistrationController {

	@Autowired
    private UserService userService;
    
    @Autowired
    private OtpService otpService;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private OtpStorageService otpStorageService;
    
    @RequestMapping("/showregister")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; 
    }
    
    @PostMapping("/register")
    public ModelAndView registerUserWithNameEmail(@ModelAttribute("user") User user, HttpSession session) {
        ModelAndView mav = new ModelAndView();

        
        if (userService.checkEmail(user.getEmail())) {
            mav.setViewName("register");
            mav.addObject("errorEmail", "Email already exists.");
            return mav;
        }

       
        session.setAttribute("userDetails", user); 

        // Generate and send OTP
        String otp = otpService.generateOtp();
        otpStorageService.storeOtp(user.getEmail(), otp);
        emailService.sendOtpEmail(user.getEmail(), otp);

       
        session.setAttribute("pendingEmail", user.getEmail());

        mav.setViewName("verify-otp");
        mav.addObject("message", "OTP has been sent to your email.");
        return mav;
    }
    

    @PostMapping("/verifyOtp")
    public ModelAndView verifyOtp(@RequestParam("otp") String otp, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        String pendingEmail = (String) session.getAttribute("pendingEmail");

        if (pendingEmail == null) {
            mav.setViewName("register");
            mav.addObject("errorMessage", "Session expired. Please register again.");
            return mav;
        }

        boolean isValid = otpStorageService.validateOtp(pendingEmail, otp);
        if (isValid) {
           
            mav.setViewName("create-password");
        } else {
            mav.setViewName("verify-otp");
            mav.addObject("errorOtp", "Invalid or expired OTP. Please try again.");
        }

        return mav;
    }

    @PostMapping("/createPassword")
    public ModelAndView createPassword(@RequestParam("password") String password, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        
        User user = (User) session.getAttribute("userDetails"); 
        if (user == null) {
            mav.setViewName("register");
            mav.addObject("errorMessage", "Session expired. Please register again.");
            return mav;
        }

       
        user.setPassword(password);
        
       
        int result = userService.insertUser(user);
        if (result > 0) {
            session.removeAttribute("userDetails");
            session.removeAttribute("pendingEmail");
            mav.setViewName("login");
            mav.addObject("successMessage", "Registration successful! You can now log in.");
        } else {
            mav.setViewName("create-password");
            mav.addObject("errorMessage", "There was an error registering the user.");
        }

        return mav;
    }

    
}