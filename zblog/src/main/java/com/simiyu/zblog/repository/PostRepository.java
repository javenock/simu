package com.simiyu.zblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.simiyu.zblog.model.Post;

/**
 * 
 * @author simiyu
 *
 */
public interface PostRepository extends PagingAndSortingRepository<Post, Long>{

	//Post findOne(Long id);

}
