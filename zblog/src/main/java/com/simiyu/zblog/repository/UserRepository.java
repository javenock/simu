package com.simiyu.zblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simiyu.zblog.model.User;


public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsernameIgnoreCase(String username);

    User findByEmailIgnoreCase(String email);
    
    User findByUsernameOrEmail(String username, String email);
}
