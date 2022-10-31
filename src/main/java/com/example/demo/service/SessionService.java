package com.example.demo.service;


import java.util.LinkedList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Attendance;
import com.example.demo.entities.Session;
import com.example.demo.entities.Student;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.SessionRepository;
import com.example.demo.repository.StudentRepository;

@Service
public class SessionService {
	@Autowired
	public SessionRepository sessRepo;
	@Autowired 
	public StudentRepository studentRepo;
	@Autowired
	public AttendanceRepository attRepo;

	
	public void deleteSessionAttendance(Integer sessionId) {
		attRepo.deleteBySessionId(sessionId);
		
	}
	public void saveSession(Session session) {
		sessRepo.save(session);
		
	}
	public List<Session> getSessions(){
		return sessRepo.findAll();
	}

	public void setAttendance(String arr[],int id) {
		for(String x:arr) {
			Attendance temp=new Attendance();
			temp.setSessionId(id);
			temp.setUserid(x);
			attRepo.save(temp);
		}
		
		
		
	}
	public Session getOneSession(Integer id) {
		return sessRepo.getById(id);
	}
	public void deleteSession(Integer id) {
		sessRepo.deleteById(id);
	}
	public String countSession() {
		
		return Long.toString(sessRepo.count());
	}
	public List<Student> getStudentsPresent(Integer id) {
		List<Attendance> presentData=attRepo.findBySessionId(id);
		
		List<Student> astu=new LinkedList<Student>();
		for(Attendance x:presentData) {
			astu.add(studentRepo.getStudentByUserId(x.getUserid()));
		}
		return astu;
		
		
		
	}

}
