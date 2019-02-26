package com.simiyu.zblog.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.simiyu.zblog.utils.LocalDateTimePersistanceConverter;

//import com.simiyu.zblog.utils.LocalDateTimePersistanceConverter;
/**
 * 
 * @author simiyu
 *
 */
@Entity
@Table(name = "comments")
public class Comment {

	private Long id;
	private String username;
	private String email;
	private String commentText; 
    private LocalDateTime dateTime;
    private Post post;
    
    private List<Reply> childrenComments = new ArrayList<>();
    
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "username", nullable = false)
    @Length(min = 5, message = "*Your username must have at least 5 characters")
    @NotEmpty(message = "*Please provide your name")
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Column(name = "email",nullable = false)
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	
	@Column(nullable = false)
   	@Convert(converter = LocalDateTimePersistanceConverter.class)
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
	public Post getPost() {
		return post;
	}
	
	public void setPost(Post post) {
		this.post = post;
	}
		
	@OneToMany(mappedBy="parentComment", cascade = CascadeType.ALL)
    @OrderBy("dateTime ASC")
	public List<Reply> getChildrenComments() {
		return childrenComments;
	}
	public void setChildrenComments(List<Reply> childrenComments) {
		this.childrenComments = childrenComments;
	}
	
	 public List<Reply> LevelComments() {
	        return childrenComments.stream().filter(c -> c.getParentComment() == null).collect(Collectors.toList());
	    }
}
