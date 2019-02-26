package com.simiyu.zblog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.simiyu.zblog.model.User;
import com.simiyu.zblog.service.UserService;

/**
 * 
 * @author simiyu
 *
 */
@Controller
public class UserController {

	
    private UserService userService;    
       
	
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;		
	}    
	 
	 @PreAuthorize("hasRole('ROLE_ADMIN')")
	 @RequestMapping(value = "/register", method = RequestMethod.GET)
	    public String showRegistrationForm(ModelMap model, HttpServletRequest request, HttpSession session) {
	        model.addAttribute("user", new User());

	        String ref = request.getHeader("referer");

	        if (ref != null && !ref.contains("/register"))
	            session.setAttribute("regRef", ref);

	        return "registration";
	    }
		
		@RequestMapping(value = "/register", method = RequestMethod.POST)
	    public String registerUser(@Valid @ModelAttribute(value = "user") User user, BindingResult result, HttpSession session) {
	       
	        if (result.hasErrors())
	        {
	            return "registration";
	        }

	        userService.register(user);        

	        return "registration";
		}
    
    
   
}
