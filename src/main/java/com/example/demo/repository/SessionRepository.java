package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Session;

public interface SessionRepository extends JpaRepository<Session, Integer>{

}
