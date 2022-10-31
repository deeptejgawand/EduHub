package com.example.demo.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Attendance;
import com.example.demo.entities.Session;
import com.example.demo.entities.Student;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.SessionRepository;
import com.example.demo.repository.StudentRepository;

@Service
public class StudentService {
	@Autowired
	public StudentRepository studentrepo;
	@Autowired 
	public SessionRepository sessRepo;
	@Autowired
	public AttendanceRepository attRepo;
	
	
	public Student saveStudent(Student student) {
		Student studentr=studentrepo.save(student);
		List<Student> s=studentrepo.findAll();
		s.get(s.size()-1).getId();
		return studentr;
		
	}
	
	public List<Student> getAllStudents() {
		List<Student> employee = studentrepo.findAll();
		return employee;
	}
	
	public String countStudents() {
		long count=studentrepo.count();
		return Long.toString(count);
		
	}
	public void deleteAll() {
		studentrepo.deleteAll();
	}
	public void deleteStudent(Integer id) {
		studentrepo.deleteById(id);
	}
	public Integer findLastId() {
		List<Student> s=studentrepo.findAll();
		if(s.isEmpty()) {
			return 0;
		}else {
			Integer id=s.get((s.size())-1).getId();
			return id;
			
		}
		
		
	}
	public Student findOneStudent(String userid) {
		return studentrepo.getStudentByUserId(userid);
	}
	public String findPercentageAttendance(Integer id) {
		Student stu=studentrepo.getById(id);
		List <Attendance> attend= attRepo.findByStudentId(stu.getUserid());
		long present=attend.size();
		long total=sessRepo.count();
		Double result=((double)present/total)*100;
		
		
		return String.valueOf(Math.ceil(result));
		
		
	}
	public Student getStudentByid(Integer id) {
		return studentrepo.getById(id);
	}
	
	

}
