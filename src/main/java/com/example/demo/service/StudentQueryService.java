package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.StudentQuery;
import com.example.demo.repository.StudentQueryRepository;

@Service
public class StudentQueryService {
	@Autowired
	public StudentQueryRepository stuQueryRepo;
	
	public List<StudentQuery> allNotifications(String id){
		return stuQueryRepo.findByStudentId(id);
	}
	public void savesq(StudentQuery sq) {
		stuQueryRepo.save(sq);
	}
	public List<StudentQuery> NotificationsByTeacherId(String id){
		return stuQueryRepo.findByTeacherId(id);
	}
	public StudentQuery getSingleQuery(Integer id){
		return stuQueryRepo.getById(id);
	}
	public void deleteQuery(Integer id) {
		stuQueryRepo.deleteById(id);
	}


}
