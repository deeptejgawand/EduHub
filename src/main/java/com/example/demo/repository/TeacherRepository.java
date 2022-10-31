package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Student;
import com.example.demo.entities.Teacher;
@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Integer> {
	@Query("select t from Teacher t where t.userid = :user_id")
	public Teacher getTeacherByUserId(@Param("user_id")String user_id);
	

}
