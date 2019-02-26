package com.simiyu.zblog.controller;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.simiyu.zblog.form.Message;
import com.simiyu.zblog.model.Comment;
import com.simiyu.zblog.model.Post;
import com.simiyu.zblog.model.User;
import com.simiyu.zblog.service.CommentService;
import com.simiyu.zblog.service.PostService;
import com.simiyu.zblog.service.UserService;
/**
 * 
 * @author simiyu
 *
 */
@Controller
public class PostController {

	private UserService userService;    
   	private PostService postService;	
	private MessageSource messageSource;
	
	@Autowired
	private CommentService commentService;
	@Autowired
	public PostController(UserService userService,PostService postService,MessageSource messageSource) {
		this.userService = userService;
		this.postService = postService;
		this.messageSource = messageSource;
	}
	
	private Logger logger = LoggerFactory.getLogger(PostController.class);
	
	@RequestMapping(value = {"/", "/posts"}, method = RequestMethod.GET)
    public String showPostsList(@RequestParam(value = "page", defaultValue = "0") Integer pageNumber, ModelMap model) {
        Page<Post> postsPage = postService.getPostsPage(pageNumber, 10);
        List<Post> postBlog = new ArrayList<>(postsPage.getContent());
        
        
        model.addAttribute("postBlog", postBlog);

        // should implement custom Spring Security  UserDetails instead of this, so it will be stored in session
        User currentUser = userService.currentUser();
        if (currentUser != null)
            model.addAttribute("userId", currentUser.getId());

        return "post_page";
    }	
	 
	 @RequestMapping(value = "/photo/{id}", method = RequestMethod.GET)
	 @ResponseBody
	 public byte[] downloadPhoto(@PathVariable("id") Long id) {
	 Post posts = postService.get(id);
	 
	 
	 		 return posts.getPhoto();
	 }
	
	    @RequestMapping(value = "/post/{postId}", method = RequestMethod.GET)
	    public String showPost(@PathVariable("postId") Long postId, Model model) {
	        Post post = postService.findById(postId);

	        if (post== null)
	            throw new ResourceNotFoundException();

	       List<Comment> comments = post.getComment();
	        
	       int comNumber = comments.size();
	        
	        model.addAttribute("post", post);
	        model.addAttribute("comments", comments);
	        model.addAttribute("comNumber", comNumber);
	        model.addAttribute("comment", new Comment());
	        

	       
	       
	        return "post";
	    }
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
		@RequestMapping(value="/create", method = RequestMethod.POST)
		public String create(@Valid @ModelAttribute("post") Post posts, BindingResult bindingResult, Model model,
				HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale,
				@RequestParam(value="file", required=false) Part file) {
			
			logger.info("create post");
			
			if(bindingResult.hasErrors()) {
				model.addAttribute("message", new Message("error",messageSource.getMessage("user_save_fail", new Object[]{}, locale)));
				
						model.addAttribute("posts", posts);
						return "posts/create";
						}
						model.asMap().clear();
						redirectAttributes.addFlashAttribute("message", new Message("success",messageSource.getMessage("user_save_success", new Object[]{}, locale)));
						logger.info("post id: " + posts.getId());
						
						if (file != null) {
							byte[] fileContent = null;
							try {
								InputStream inputStream = file.getInputStream();
								if (inputStream == null)logger.info("File inputstream is null");
									fileContent = IOUtils.toByteArray(inputStream);
								posts.setPhoto(fileContent);
							} catch (IOException ex) {
								logger.error("Error saving uploaded file");
							}
							posts.setPhoto(fileContent);
						}        
							
						postService.save(posts);
						return "redirect:/posts";
						}
		
	     @PreAuthorize("hasRole('ROLE_ADMIN')")
		 @RequestMapping(value="/create", method=RequestMethod.GET)
		 public String createForm( Model model, @Valid @ModelAttribute("post") Post posts) {
			 
				 	model.addAttribute("posts", posts);	
				 	
			 return "posts/create";
		 }
	     
	     @PreAuthorize("hasRole('ROLE_ADMIN')")
	     @RequestMapping(value="/create/{id}", params="form", method=RequestMethod.GET)
	     public String upDateForm(@PathVariable("id") Long id, Model model) {
	    	 model.addAttribute("posts", postService.findById(id));
	    	 
	    	 return "post/update";
	     }
	     
	     
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    @RequestMapping(value="/create/{id}",params="form", method = RequestMethod.POST)
	 	public String postUpdate(@Valid @ModelAttribute("post") Post posts, BindingResult bindingResult, Model model,
	 			HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale,
	 			@RequestParam(value="file", required=false) Part file) {
	 		
	 		logger.info("create post");
	 		
	 		if(bindingResult.hasErrors()) {
	 			model.addAttribute("message", new Message("error",messageSource.getMessage("user_save_fail", new Object[]{}, locale)));
	 			
	 					model.addAttribute("posts", posts);
	 					return "posts/create";
	 					}
	 					model.asMap().clear();
	 					redirectAttributes.addFlashAttribute("message", new Message("success",messageSource.getMessage("user_save_success", new Object[]{}, locale)));
	 					logger.info("post id: " + posts.getId());
	 					
	 					if (file != null) {
	 						byte[] fileContent = null;
	 						try {
	 							InputStream inputStream = file.getInputStream();
	 							if (inputStream == null)logger.info("File inputstream is null");
	 								fileContent = IOUtils.toByteArray(inputStream);
	 							posts.setPhoto(fileContent);
	 						} catch (IOException ex) {
	 							logger.error("Error saving uploaded file");
	 						}
	 						posts.setPhoto(fileContent);
	 					}        
	 						
	 					postService.save(posts);
	 					return "redirect:/posts";
	 			
	    }
	    
	      
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    @RequestMapping(value = "/post/{postId}/delete", method = RequestMethod.GET)
	    public String deletePost(@PathVariable("postId") Long postId, Model model) {
	    	postService.delete(postId);
	    	return "redirect:/posts";
	    }
	    
	   }

