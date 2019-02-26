package com.simiyu.zblog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.simiyu.zblog.model.Post;
/**
 * 
 * @author simiyu
 *
 */
public interface PostService {

	Post findById(Long id);
    Page<Post> getPostsPage(int pageNumber, int pageSize);
    
    void save(Post post);
    Post edit(Post post );
    void delete(Long  postId);  
    Page<Post> findByStatusOrderByPostDateAsc(String status, Pageable pageable);
	Post get(Long id);
	
}
