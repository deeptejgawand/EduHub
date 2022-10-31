package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{
	@Query("select stu from Student stu where stu.userid = :user_id")
	public Student getStudentByUserId(@Param("user_id")String user_id);
	

}
