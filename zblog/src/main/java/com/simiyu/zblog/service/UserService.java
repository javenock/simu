package com.simiyu.zblog.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.simiyu.zblog.model.User;

/**
 * 
 * @author simiyu
 *
 */
public interface UserService extends UserDetailsService{

	 User findByEmail(String email);

	    User findByUsername(String username);
	    
	    boolean isAuthenticated();
	    
	    boolean emailExists(String email);

	    boolean usernameExists(String username);
	    
	    User currentUser();
	    
	    void register(User user);
	    
	    void authenticate(User user);
}
