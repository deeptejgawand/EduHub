package com.example.demo.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@RequestMapping("/")
	public String ShowLandigPage() {
		return "landingpage";
	}
	@GetMapping("/ABC")
	public String ShowLandPage() {
		return "landingpage";
	}
	@GetMapping("/login")
	public String ShowLogin() {
		return "loginpage";
	}
	@GetMapping("/about")
	public String showAbout() {
		return "about";
	}
	@GetMapping("/contact")
	public String showContact() {
		return "contact";
	}

}
