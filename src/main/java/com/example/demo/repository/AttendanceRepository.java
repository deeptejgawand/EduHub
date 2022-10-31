package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Attendance;


public interface AttendanceRepository extends JpaRepository<Attendance, Integer>{
	@Query("select Attend from Attendance Attend where Attend.userid = :l ")
	public List<Attendance> findByStudentId(@Param("l") String userid);
	
	@Query("select Attend from Attendance Attend where Attend.sessionId = :l ")
	public List<Attendance> findBySessionId(@Param("l") Integer sessionId);
	
	@Modifying
	@Transactional
	@Query("delete from Attendance a where a.sessionId=:sessionId")
	public void deleteBySessionId(@Param("sessionId") Integer sessionId);
}
