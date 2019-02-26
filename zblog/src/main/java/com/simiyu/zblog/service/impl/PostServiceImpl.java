package com.simiyu.zblog.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simiyu.zblog.model.Post;
import com.simiyu.zblog.repository.PostRepository;
import com.simiyu.zblog.service.PostService;
import com.simiyu.zblog.service.UserService;

/**
 * 
 * @author simiyu
 *
 */
@Service
@Transactional
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Post findById(Long id) {
		
		return postRepository.findOne(id);
	}

	@Override
	public void save(Post post) {
		post.setDateTime(LocalDateTime.now());
		post.setDate(LocalDate.now());
		post.setUser(userService.currentUser());
		postRepository.save(post);				
	}

	@Override
	public Post edit(Post post) {
		
		return null;
	}

	@Override
	public Page<Post> findByStatusOrderByPostDateAsc(String status, Pageable pageable) {
		
		return postRepository.findAll(pageable);
	}

		@Override
		public Page<Post> getPostsPage(int pageNumber, int pageSize) {
			
			 PageRequest pageRequest = new PageRequest(pageNumber, pageSize, Sort.Direction.DESC, "dateTime");

		        return postRepository.findAll(pageRequest);
		}

			
		@Override
		public void delete(Long postId) {
			postRepository.delete(postId);
			
		}

		@Transactional(readOnly=true)
		public Post get(Long id) {
			
			return postRepository.findOne(id);
		}

}
