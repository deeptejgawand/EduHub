package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Session;
import com.example.demo.entities.Student;
import com.example.demo.entities.Teacher;
import com.example.demo.repository.SessionRepository;
import com.example.demo.repository.TeacherRepository;

@Service
public class TeacherService {
	@Autowired
	public TeacherRepository teacherRepo;
	@Autowired
	public SessionRepository sessRepo;
	
	
	public Teacher saveTeacher(Teacher teacher) {
		return teacherRepo.save(teacher);
		
	}
	
	public List<Teacher> showAllTeacher(){
		return teacherRepo.findAll();
	}
	public void deleteAll() {
		teacherRepo.deleteAll();
	}
	public Integer findLastId() {
		List<Teacher> t=teacherRepo.findAll();
		if(t.isEmpty()) {
			return 0;
		}else {
			Integer id=t.get((t.size())-1).getId();
			return id;
			
		}
		
		
	}
	public Teacher getTeacherByUserId(String id) {
		return teacherRepo.getTeacherByUserId(id);
	}
public String countTeacher() {
		
		return Long.toString(teacherRepo.count());
	}
	public void deleteTeacher(Integer id) {
		teacherRepo.deleteById(id);
	}
	public Teacher getTeacherByid(Integer id) {
		return teacherRepo.getById(id);
	}


}
