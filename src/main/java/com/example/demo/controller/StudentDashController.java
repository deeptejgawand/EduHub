package com.example.demo.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entities.Notification;
import com.example.demo.entities.Results;
import com.example.demo.entities.Session;
import com.example.demo.entities.Student;
import com.example.demo.entities.StudentQuery;
import com.example.demo.entities.Teacher;
import com.example.demo.entities.User;
import com.example.demo.service.NotificationService;
import com.example.demo.service.ResultsService;
import com.example.demo.service.SessionService;
import com.example.demo.service.StudentQueryService;
import com.example.demo.service.StudentService;
import com.example.demo.service.TeacherService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("student")
public class StudentDashController {
	@Autowired 
	public StudentService stuser;
	@Autowired 
	public SessionService sessServ;
	@Autowired
	public StudentQueryService stuQserv;
	@Autowired
	public TeacherService teaServ;
	@Autowired
	public NotificationService notServ;
	@Autowired
	public ResultsService resServ;
	@Autowired
	public BCryptPasswordEncoder bcrypt;
	@Autowired
	public UserService userServ;
	@GetMapping("/home")
	public	String showHome(Model model,Principal principal) {
		List<Session> sessions=sessServ.getSessions();
		Student stu=stuser.findOneStudent(principal.getName());
		model.addAttribute("attper", stuser.findPercentageAttendance(stu.getId()));
		model.addAttribute("sessions", sessions);
		model.addAttribute("stuUser",principal.getName());
		model.addAttribute("stuName", stu.getFname());		
		
		
		return "studentHome";
	}
	@GetMapping("/showQueries")
	public String fillQuestionForm(Model model,Principal principal) {
		Student student=stuser.findOneStudent(principal.getName());
		List<Teacher> teachers=teaServ.showAllTeacher();
		model.addAttribute("stuUser", principal.getName());
		
		List<StudentQuery> queries=stuQserv.allNotifications(student.getUserid());
		model.addAttribute("stuque", queries);
		model.addAttribute("studentId", principal.getName());
		model.addAttribute("stuName", student.getFname());
		model.addAttribute("teachers", teachers);
		
		
		return "questionsStudent";
		
	}
	@PostMapping("sendQuestion")
	public ModelAndView sendQuestion(@ModelAttribute StudentQuery sq) {
		stuQserv.savesq(sq);
		
		
		return new ModelAndView("redirect:/student/showQueries");
		
	}
	@GetMapping("/showSession")
	public ModelAndView showSessions(Model model,Principal principal) {
		Student stu=stuser.findOneStudent(principal.getName());
		model.addAttribute("attper", stuser.findPercentageAttendance(stu.getId()));
		List<Session> sessions=sessServ.getSessions();
		model.addAttribute("sessions", sessions);
		model.addAttribute("stuUser", principal.getName());
		model.addAttribute("studentId",principal.getName());
		return new ModelAndView("redirect:/student/home");
		
	}
	@GetMapping("/showNotices")
	public String showNotices(Model model,Principal principal){
		Student stu=stuser.findOneStudent(principal.getName());
		List<Notification> notices=notServ.showAllNotifications();
		model.addAttribute("notices", notices);
		model.addAttribute("stuUser", principal.getName());
		model.addAttribute("stuName", stu.getFname());
		return "notificationsStudents";
		
	}
	@GetMapping("/results")
	public String showResults(Model model,Principal principal) {
		Student stu=stuser.findOneStudent(principal.getName());
		model.addAttribute("stuName", stu.getFname());
		List<Results>  results=resServ.getAllResults();
		model.addAttribute("stuUser", principal.getName());
		model.addAttribute("results",results);
		return "studentResults";
	}
	@GetMapping("/showVideo")
	public String showVideo (@RequestParam("sessid")Integer sessid,Model model,Principal principal) {
		Session sess=sessServ.getOneSession(sessid);
		model.addAttribute("sess",sess);
		model.addAttribute("stuUser", principal.getName());
		
			System.out.println(sessid);
		return "studentStudy";
	}
	@GetMapping("/showProfile")
	public String showProfile(@RequestParam("stuid")String userid,Model model) {
		Student student=stuser.findOneStudent(userid);
		model.addAttribute("student", student);
		model.addAttribute("stuName", student.getFname());
		return "studentProfile";
		
	}
	@GetMapping("/changePassword")
	public String changePassword(Model model,Principal principal) {
		model.addAttribute("userid", principal.getName());
		return "changePassword";
		
	}
	@PostMapping("updatePassword")
	public String updatePassword(@RequestParam("password")String password,Model model,Principal principal) {
		Integer userid=Integer.parseInt(principal.getName());
		User user=userServ.findOneUser(userid);
		user.setPassword(bcrypt.encode(password));
		userServ.saveUser(user);
		String msg="Password changed successfully";
		model.addAttribute("message", msg);
				
		return "changePassword";
	}
	
	

}
