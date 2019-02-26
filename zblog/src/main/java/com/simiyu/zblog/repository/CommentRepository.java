package com.simiyu.zblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simiyu.zblog.model.Comment;
/**
 * 
 * @author simiyu
 *
 */
public interface CommentRepository extends JpaRepository<Comment, Long>{

}
