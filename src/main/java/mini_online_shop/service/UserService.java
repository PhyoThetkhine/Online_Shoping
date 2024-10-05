package mini_online_shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mini_online_shop.model.LoginBean;
import mini_online_shop.model.User;
import mini_online_shop.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	
	public int insertUser(User user) {
		if (userRepository.doesUserExist(user.getEmail())) {
            // Product already exists
            return -1; // Indicate failure due to existing email
        }
		return userRepository.insertUser(user);
	}
	
	 public boolean checkEmail(String email) {
		 if(userRepository.checkEmail(email)!=false) {
			 return userRepository.checkEmail(email);
		 }else {
			 return false;
		 }
	 }
	 public User loginUser(LoginBean bean) {
		 if(userRepository.loginUser(bean)!=null) {
			 return userRepository.loginUser(bean);
		 }else {
			 return null;
		 }
		 
	 }

	 public User getUserById(Integer userId) {
	        return userRepository.findById(userId); // Call the repository method to get the user
	    }
	 
	}


