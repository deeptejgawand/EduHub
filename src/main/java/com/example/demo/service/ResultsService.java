package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Results;
import com.example.demo.repository.ResultsRepository;

@Service
public class ResultsService  {
	@Autowired
	public ResultsRepository resultRepo;
	
	public void saveResults(Results result) {
		resultRepo.save(result);
	}
	public List<Results> getAllResults(){
		return resultRepo.findAll();
	}
	
	public void deleteOneResult(Integer id) {
		resultRepo.deleteById(id);
		
	}
	public void deleteAllResults() {
		resultRepo.deleteAll();
	}
	

}
