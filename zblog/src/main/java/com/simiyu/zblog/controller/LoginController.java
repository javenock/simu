package com.simiyu.zblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.simiyu.zblog.service.UserService;

/**
 * 
 * @author simiyu
 *
 */

@Controller
@RequestMapping("/administrator")
public class LoginController {

	@Autowired
    private UserService userService;	
	 
	    @RequestMapping(value = "/login", method = RequestMethod.GET)
	    public String login(Model model) {	    	

	        return "log_in";
	    }	      
	    @RequestMapping(value="/login_error", method=RequestMethod.GET)	    
	    public ModelAndView invalidLogin() {
	    
	    	        ModelAndView modelAndView = new ModelAndView("log_in");
	   
	    	        modelAndView.addObject("error", true);
	    
	    	        return modelAndView;
	    
	    	    }

	    // POST request must be used for logout if CSRF enabled, so this page contains hidden form to submit via JS
	    @RequestMapping(value = "/logout", method= RequestMethod.GET)
	    public String logout() {
	        if (!userService.isAuthenticated()) {
	            return "redirect:/posts";
	        }

	        return "logout";
	    }
}
