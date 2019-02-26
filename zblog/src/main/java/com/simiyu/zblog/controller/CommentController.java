package com.simiyu.zblog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

import com.simiyu.zblog.form.Message;
import com.simiyu.zblog.model.Comment;
import com.simiyu.zblog.model.Post;
import com.simiyu.zblog.model.Reply;
import com.simiyu.zblog.service.CommentService;
import com.simiyu.zblog.service.PostService;
import com.simiyu.zblog.service.ReplyService;

/**
 * 
 * @author simiyu
 *
 */
@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private PostService postService;
	
	@Autowired
	private ReplyService replyService;
	
	    @RequestMapping(value = "/post/{postId}/comment/{commentId}", method = RequestMethod.GET)
	    public String replyComments(@PathVariable("commentId") Long commentId, ModelMap model) {
		 
		Comment comm = commentService.findById(commentId);
	       

	       
	        
	       List<Reply> comments = new ArrayList<>(comm.getChildrenComments());

	        model.addAttribute("comment", comm);
	        model.addAttribute("comments", comments);
	        
	        return "commentForm";
	    }
	 
    @RequestMapping(value = "/post/{postId}/comments", method = RequestMethod.POST)
	public @ResponseBody String createComment(@Valid @ModelAttribute("comment") Comment comment,
			BindingResult bindingResult,@PathVariable("postId") Long postId, @RequestParam(value="parentId", defaultValue="") Long parentId, Model model,Locale locale) {
    	
		if(bindingResult.hasErrors()) {
			
			model.addAttribute("message", new Message("error",messageSource.getMessage("user_save_fail", new Object[]{}, locale)));
				return "comment/create";
					}
		Post post = postService.get(postId);
		if (post == null) {
            return "/error";
		}
	
		commentService.save(comment, post);
            return "redirect:/post"; //comment.getPost().getId();
       	}

    @RequestMapping(value = "/post/{postId}/comments/{commentId}/reply", method = RequestMethod.POST)
	public @ResponseBody String createReply(@Valid @ModelAttribute("reply") Reply reply,
			BindingResult bindingResult,@PathVariable("commentId") Long commentId, @RequestParam(value="parentId", defaultValue="") Long parentId, Model model,Locale locale) {
    	
		if(bindingResult.hasErrors()) {
			
			model.addAttribute("message", new Message("error",messageSource.getMessage("user_save_fail", new Object[]{}, locale)));
				return "comment/create";
					}
		Comment comment = commentService.findById(commentId);
		if (comment == null) {
            return "/error";
		}
	
		replyService.save(reply, comment, parentId);
            return "redirect:/post"; //comment.getPost().getId();
       	}


}
