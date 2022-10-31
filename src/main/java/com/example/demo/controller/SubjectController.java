package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.example.demo.entities.Subject;
import com.example.demo.service.SubjectService;

@Controller
@RequestMapping("subject")
public class SubjectController {
	@Autowired
	public SubjectService subServ;
	
	@GetMapping("/home")
	public String showHome(Model model) {
		List<Subject> subjects=subServ.showAllSubjects();
		model.addAttribute("subjects",subjects);
		return "subjects";
		
	}
	
	@PostMapping("save")
	public String saveSubject(@ModelAttribute Subject subject,Model model) {
		subServ.saveSubject(subject);
		List<Subject> subjects=subServ.showAllSubjects();
		model.addAttribute("subjects",subjects);
		return "subjects";
	}
	
	@GetMapping("/delete")
	public String deleteAllSub(){
		subServ.deleteAll();
		return "subjects";
		
	}
	
	
}
