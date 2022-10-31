package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	public UserRepository userRepo;
	
	public void saveUser(User user) {
		userRepo.save(user);
		
	}
	public void deleteByRole(String role) {
		userRepo.deleteByRole(role);
	}
	public User findOneUser(Integer userid) {
		return userRepo.getById(userid);
		
	}
	public void deleteById(Integer id) {
		userRepo.deleteById(id);
	}
}
