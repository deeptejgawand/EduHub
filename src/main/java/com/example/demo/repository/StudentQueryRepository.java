package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.StudentQuery;
@Repository
public interface StudentQueryRepository extends JpaRepository<StudentQuery, Integer> {
	@Query("select StuQue from StudentQuery StuQue where StuQue.studentid = :l ")
	public List<StudentQuery> findByStudentId(@Param("l") String stuid);
	
	@Query("select StuQue from StudentQuery StuQue where StuQue.teacherid = :l")
	public List<StudentQuery> findByTeacherId(@Param("l") String teaid);

}
