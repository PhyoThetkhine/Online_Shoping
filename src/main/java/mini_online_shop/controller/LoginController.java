package mini_online_shop.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import mini_online_shop.model.LoginBean;
import mini_online_shop.service.UserService;
import mini_online_shop.model.User;



@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/doLogin")
    public ModelAndView loginUser(@RequestParam("email") String email,
                                  @RequestParam("password") String password, HttpSession session) {
        LoginBean loginBean = new LoginBean();
        loginBean.setEmail(email);
        loginBean.setPassword(password);
        
        boolean result = userService.checkEmail(email);
        if(result) {
            User user = userService.loginUser(loginBean);

            if (user != null) {
            	 session.setAttribute("currentUserId", user.getId());
                session.setAttribute("email", user.getEmail());
                session.setAttribute("role", user.getRole());

                
                if ("admin".equalsIgnoreCase(user.getRole())) {
                    return new ModelAndView("redirect:/products");
                } else if ("user".equalsIgnoreCase(user.getRole())) {
                    return new ModelAndView("redirect:/home");
                }
            }
        }

        ModelAndView mav = new ModelAndView("login");
        mav.addObject("error", "Invalid email or password");
        return mav;
    }
}
