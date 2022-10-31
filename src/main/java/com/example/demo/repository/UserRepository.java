package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.User;


public interface UserRepository extends JpaRepository<User, Integer> {
	@Modifying
	@Transactional
	@Query("delete from User u where u.role=:role")
	public void deleteByRole(@Param("role") String role);
	
	
}
