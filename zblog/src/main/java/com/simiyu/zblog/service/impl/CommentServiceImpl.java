package com.simiyu.zblog.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simiyu.zblog.model.Comment;
import com.simiyu.zblog.model.Post;
import com.simiyu.zblog.repository.CommentRepository;
import com.simiyu.zblog.repository.PostRepository;
import com.simiyu.zblog.service.CommentService;
import com.simiyu.zblog.service.UserService;
/**
 * 
 * @author simiyu
 *
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService{

	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private static final int MAX_COMMENT_LEVEL = 5;
		
	@Autowired
	private UserService userService;	
	
	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
		this.commentRepository =  commentRepository;
		this.postRepository = postRepository;
		
	}
	@Override
	public Long save(Comment comment,Post post) {
		comment.setPost(post);
		comment.setDateTime(LocalDateTime.now());
		 commentRepository.saveAndFlush(comment);
		 return comment.getId(); 
	}
	@Override
	public Comment findById(Long id) {
		
		return commentRepository.findOne(id);
	}

	
}
