package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Course;
import com.example.demo.repository.CourseRepository;

@Service
public class CourseService {
	@Autowired
	public CourseRepository courserepo;
	
	public void saveCourse(Course course) {
		courserepo.save(course);
	}
	
	public List<Course> getAllCourse(){
		return courserepo.findAll();
		
	}
	public String countCourses() {
		Long count=courserepo.count();
		return count.toString();
	}
	public void deleteAll() {
		courserepo.deleteAll();
	}

}
