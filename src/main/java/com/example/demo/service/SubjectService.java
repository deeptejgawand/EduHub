package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Subject;
import com.example.demo.repository.SubjectRepository;

@Service
public class SubjectService {
	@Autowired
	public SubjectRepository subRepo;
	
	public void saveSubject(Subject subject) {
		subRepo.save(subject);
	}
	
	public List<Subject> showAllSubjects(){
		return subRepo.findAll();
	}
	
	public void deleteAll() {
		subRepo.deleteAll();
	}
	public String countSubject() {
		Long count=subRepo.count();
		return count.toString();
	}
	

}
