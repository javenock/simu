package com.simiyu.zblog.service;

import com.simiyu.zblog.model.Comment;
import com.simiyu.zblog.model.Post;

/**
 * 
 * @author simiyu
 *
 */
public interface CommentService {
	
	Comment findById(Long id);
	public Long save(Comment comment, Post post);
	
}
