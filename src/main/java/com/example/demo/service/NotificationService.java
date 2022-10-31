package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Notification;
import com.example.demo.repository.NotificationRepository;

@Service
public class NotificationService {
	@Autowired
	public NotificationRepository nfRepo;
	
	public void saveNotification(Notification notification) {
		nfRepo.save(notification);
		
	}
	
	public List<Notification> showAllNotifications(){
		return nfRepo.findAll();
	}
	public void deleteByid(Integer id) {
		nfRepo.deleteById(id);
	}

}
