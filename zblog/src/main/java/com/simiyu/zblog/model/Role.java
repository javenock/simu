package com.simiyu.zblog.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 
 * @author simiyu
 *
 */
	@Entity
	@Table(name= "role")
	public class Role {

		private Long id;
		private String name;
		private Collection<User> users = new ArrayList<>();
		
		@Id
	    @GeneratedValue
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		@ManyToMany(mappedBy="roles")
		public Collection<User> getUsers() {
			return users;
		}
		public void setUsers(Collection<User> users) {
			this.users = users;
		}
		
}
