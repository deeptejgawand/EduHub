package com.example.demo.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;

public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Integer n=Integer.parseInt(username);
		
		User user=userRepo.getById(n);
		   
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
		
		
		CustomUserDetails cud=new CustomUserDetails(user);
		
		
		return cud;
	}

}
