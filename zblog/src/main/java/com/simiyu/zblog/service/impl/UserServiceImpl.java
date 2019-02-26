package com.simiyu.zblog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simiyu.zblog.model.Role;
import com.simiyu.zblog.model.User;
import com.simiyu.zblog.repository.RoleRepository;
import com.simiyu.zblog.repository.UserRepository;
import com.simiyu.zblog.service.UserService;



@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	 @Autowired
	 private RoleRepository roleRepository;
	
	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameOrEmail(s, s);
		if(user==null) {
			throw new UsernameNotFoundException("User Not Found");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(Role roles: user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(roles.getName()));
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}

	@Transactional(readOnly=true)
	public User findByEmail(String email) {
		
		return  userRepository.findByEmailIgnoreCase(email);
	}

	@Transactional(readOnly=true)
	public User findByUsername(String username) {
		return userRepository.findByUsernameIgnoreCase(username);
	}

	@Transactional(readOnly=true)
	public boolean isAuthenticated() {
		
		SecurityContext securityContext = SecurityContextHolder.getContext();

        Authentication auth = securityContext.getAuthentication();

        return auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated();
	}

	@Transactional(readOnly=true)
	public boolean emailExists(String email) {
		return findByEmail(email) != null;
	}

	@Transactional(readOnly=true)
	public boolean usernameExists(String username) {
		return findByUsername(username) != null;
	}

	@Transactional(readOnly=true)
	public User currentUser() {
		if (!isAuthenticated())
            return null;

        SecurityContext securityContext = SecurityContextHolder.getContext();

        Authentication auth = securityContext.getAuthentication();

        return userRepository.findByUsernameIgnoreCase(auth.getName());
	}

	
	public void register(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.getRoles().add(roleRepository.findByName("ROLE_USER"));

        user.setEnabled(true);

        userRepository.saveAndFlush(user);
		
	}

	@Transactional(readOnly=true)
	public void authenticate(User user) {
		UserDetails userDetails = loadUserByUsername(user.getUsername());

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
		
	}

}
