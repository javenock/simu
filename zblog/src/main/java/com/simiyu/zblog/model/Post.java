package com.simiyu.zblog.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.simiyu.zblog.utils.LocalDatePersistenceConverter;
import com.simiyu.zblog.utils.LocalDateTimePersistanceConverter;


//import com.simiyu.zblog.utils.LocalDateTimePersistanceConverter;
/**
 * 
 * @author simiyu
 *
 */
@Entity
@Table(name = "posts")
public class Post {

	    private Long Id;
	    private String title;	    
	    private String body;
	    private LocalDateTime dateTime;
	    private LocalDate date;
	    private byte[] photo;
	    private List<Comment> comment; 
	    private User user;
	    
	    @Id
	    @GeneratedValue(strategy=GenerationType.AUTO)
		public Long getId() {
			return Id;
		}
		public void setId(Long id) {
			Id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		@Lob
		@Column(name = "BODY")
		public String getBody() {
			return body;
		}
		public void setBody(String body) {
			this.body = body;
		}
		@Column(nullable = false)
	   	@Convert(converter = LocalDateTimePersistanceConverter.class)
		public LocalDateTime getDateTime() {
			return dateTime;
		}
		public void setDateTime(LocalDateTime dateTime) {
			this.dateTime = dateTime;
		}
		@Column(nullable = false)
	   	@Convert(converter = LocalDatePersistenceConverter.class)
		public LocalDate getDate() {
			return date;
		}
		public void setDate(LocalDate date) {
			this.date = date;
		}
		@Basic(fetch=FetchType.LAZY)
		@Lob @Column(name = "PHOTO")
		public byte[] getPhoto() {
			return photo;
		}
		public void setPhoto(byte[] photo) {
			this.photo = photo;
		}		
		@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "post")
		public List<Comment> getComment() {
			return comment;
		}

		public void setComment(List<Comment> comment) {
			this.comment = comment;
		}
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "user_id", nullable = false)
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
		
		public List<Comment> topLevelComments() {
	        return comment.stream().collect(Collectors.toList());
	    }
		
		
	    
	    
}
