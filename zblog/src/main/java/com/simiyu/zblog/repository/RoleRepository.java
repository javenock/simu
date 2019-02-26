package com.simiyu.zblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simiyu.zblog.model.Role;
/**
 * 
 * @author simiyu
 *
 */

public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByName(String name);
}
