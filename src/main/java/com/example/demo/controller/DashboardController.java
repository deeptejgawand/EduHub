package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entities.Notification;
import com.example.demo.entities.Results;
import com.example.demo.entities.Session;
import com.example.demo.entities.Student;
import com.example.demo.entities.Subject;
import com.example.demo.entities.Teacher;
import com.example.demo.entities.User;
import com.example.demo.helper.FileUploadUtil;
import com.example.demo.service.CourseService;
import com.example.demo.service.NotificationService;
import com.example.demo.service.ResultsService;
import com.example.demo.service.SessionService;
import com.example.demo.service.StudentService;
import com.example.demo.service.SubjectService;
import com.example.demo.service.TeacherService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("admin")
public class DashboardController {
	@Autowired
	public StudentService stuServ;
	@Autowired
	public CourseService courServ;
	@Autowired
	public SubjectService subServ;
	@Autowired
	public NotificationService nfServ;
	@Autowired
	public JavaMailSender jms;
	@Autowired
	public TeacherService teacherServ;
	@Autowired
	public UserService userServ;
	@Autowired
	public BCryptPasswordEncoder bcrypt;
	@Autowired
	public SessionService sessServ;
	@Autowired
	public ResultsService resServ;
	@Autowired
	public NotificationService notServ;
	
	@GetMapping("landing")
	public String landingpage() {
		return "landingpage";
	}
	
	@GetMapping("/home")
	public String showHome(Model model,Principal principal) {
		
		String stucount=stuServ.countStudents();
		String subcount=subServ.countSubject();
		
		
		
		model.addAttribute("stucount", stucount);
		model.addAttribute("teacount", teacherServ.countTeacher());
		model.addAttribute("AdminId",principal.getName() );
		model.addAttribute("sesscount", sessServ.countSession());
		model.addAttribute("subcount", subcount);
		return "dashboard";
		
	}
	@PostMapping("sendNotification")
	public ModelAndView sendNotification(@ModelAttribute Notification notification) {
		nfServ.saveNotification(notification);
		return new ModelAndView("redirect:/admin/home");
		
		
	}
	@GetMapping("/showStudents")
	public String showRegisterPage(Model model,Principal principal) {
		List<Student> students = stuServ.getAllStudents();
		model.addAttribute("student", students);
		model.addAttribute("AdminId", principal.getName());
		return "students";
	}
	@PostMapping("/savestudent")
	public String registerEmployee(@ModelAttribute Student student,Model model) {
		
		Integer temp=stuServ.findLastId();
		String userid="20221"+temp;
		Integer uid=Integer.parseInt(userid);
		student.setUserid(userid);
		User user=new User();
		user.setId(uid);
		user.setPassword(bcrypt.encode(userid));
		user.setUserid(userid);
		user.setRole("ROLE_STUDENT");
		userServ.saveUser(user);
		Student re=stuServ.saveStudent(student);
		String subject="Congratulations "+re.getFname()+"Your are now part of ABC BBSR";
		String body="Welcome "+re.getFname()+"\n please login with user id :"+re.getUserid()+" and enter password same as your userid";
		SimpleMailMessage smm=new SimpleMailMessage();
		smm.setTo(re.getEmailId());
		smm.setSubject(subject);
		smm.setText(body);
		jms.send(smm);		
		List<Student> students = stuServ.getAllStudents();
		model.addAttribute("student", students);
		return "students";
	}
	@GetMapping("/deleteAllStudents")
	public String deleteAllStudents() {
		userServ.deleteByRole("ROLE_STUDENT");
		
		stuServ.deleteAll();
		return "students";
	}
	
	@GetMapping("/deleteStudent")
	public ModelAndView deleteOneStudent(@RequestParam("stuid") Integer id) {
		Integer uid=Integer.parseInt(stuServ.getStudentByid(id).getUserid());
		System.out.println(id);
		stuServ.deleteStudent(id);
		userServ.deleteById(uid);
		return new ModelAndView("redirect:/admin/showStudents");
	}
	
	@GetMapping("/showTeachers")
	public String showTeachers(Model model) {
		List<Subject> subjects=subServ.showAllSubjects();
		model.addAttribute("subjects", subjects);
		List<Teacher> teachers=teacherServ.showAllTeacher();
		model.addAttribute("teachers", teachers);
		return "teachers";
		
	}
	@PostMapping("saveTeacher")
	public String saveTeacher(@ModelAttribute Teacher teacher,Model model) {
		Integer temp=teacherServ.findLastId();
		String userid="20222"+temp;
		Integer uid=Integer.parseInt(userid);
		teacher.setUserid(userid);
		User user=new User();
		user.setId(uid);
		user.setPassword(bcrypt.encode(userid));
		user.setUserid(userid);
		user.setRole("ROLE_TEACHER");
		userServ.saveUser(user);
		Teacher re=teacherServ.saveTeacher(teacher);
		String subject="Congratulations "+re.getFname()+"Your are now part of ABC BBSR";
		String body="Welcome "+re.getFname()+"\n please login with user id :"+re.getUserid()+" and enter password same as your userid";
		SimpleMailMessage smm=new SimpleMailMessage();
		smm.setTo(re.getEmail());
		smm.setSubject(subject);
		smm.setText(body);
		jms.send(smm);		
		List<Teacher> teachers = teacherServ.showAllTeacher();
		model.addAttribute("teachers", teachers);
		
		List<Subject> subjects=subServ.showAllSubjects();
		model.addAttribute("subjects", subjects);
		
		model.addAttribute("teachers", teachers);
		return "teachers" ;
	}
	@GetMapping("/deleteAllTeachers")
	public String deleteAllTeachers() {
		teacherServ.deleteAll();
		userServ.deleteByRole("ROLE_TEACHER");
		return "teachers";
	}
	@GetMapping("/deleteTeacher")
	public ModelAndView deleteOneTeacher(@RequestParam("teaid") Integer id) {
		System.out.println(id);
		Integer uid=Integer.parseInt(teacherServ.getTeacherByid(id).getUserid());
		teacherServ.deleteTeacher(id);
		
		userServ.deleteById(uid);
		return new ModelAndView("redirect:/admin/showTeachers");
	}
	
	@GetMapping("/showSubjects")
	public String showSubjects(Model model) {
		List<Subject> subjects=subServ.showAllSubjects();
		model.addAttribute("subjects",subjects);
		return "subjects";
		
	}
	@PostMapping("saveSubject")
	public String saveSubject(@ModelAttribute Subject subject,Model model) {
		subServ.saveSubject(subject);
		List<Subject> subjects=subServ.showAllSubjects();
		model.addAttribute("subjects",subjects);
		return "subjects";
	}
	
	@GetMapping("/deleteAllSubjects")
	public String deleteAllSub(){
		subServ.deleteAll();
		return "subjects";
		
	}
	@GetMapping("/setResult")
	public String showResults(Model model ,Principal principal) {
		List<Results>  results=resServ.getAllResults();
		model.addAttribute("adminId", principal.getName());
		model.addAttribute("results",results);
		return "setExam";
	}
	@PostMapping("/saveResult")
	public ModelAndView saveSession(@ModelAttribute Results result,@RequestParam("resultUp") MultipartFile multipartFile1  , Model model) throws Exception {
		String filePath = StringUtils.cleanPath(multipartFile1.getOriginalFilename());
		
		
		
		result.setResultUpload(filePath);
		resServ.saveResults(result);
		
		
		
		
		FileUploadUtil.saveFile("static/results/", filePath, multipartFile1);
		
		
		
		
		return new ModelAndView("redirect:/admin/setResult");
	}
	@GetMapping("/deleteResult")
	public ModelAndView deleteResult(@RequestParam("resid") Integer id) {
		resServ.deleteOneResult(id);
		return new ModelAndView("redirect:/admin/setResult");
		
	}
	@GetMapping("/deleteAllResults")
	public ModelAndView deleteAllResults() {
		resServ.deleteAllResults();
		return new ModelAndView("redirect:/admin/setResult");
		
	}
	@GetMapping("/showNotices")
	public String showNotices(Model model){
		List<Notification> notices=notServ.showAllNotifications();
		model.addAttribute("notices", notices);
		
		return "noticesAdmin";
		
	}
	@GetMapping("/deleteNotice")
	public ModelAndView deleteNotice(@RequestParam("notid") Integer id) {
		nfServ.deleteByid(id);
		return new ModelAndView("redirect:/admin/showNotices");
		
	}
	@GetMapping("/editStudent")
	public String editStudent(@RequestParam("stuid") String userid,Model model) {
		System.out.println(userid);
		model.addAttribute("student", stuServ.findOneStudent(userid));
		
		return "studentEdit";
	}
	@PostMapping("/updateStudent")
	public ModelAndView updateStudent(@ModelAttribute Student student,@RequestParam("stuid")String userid) {
		Student stu=stuServ.findOneStudent(userid);
		stu.setEmailId(student.getEmailId());
		stu.setFname(student.getFname());
		stu.setLname(student.getFname());
		stu.setMnumber(student.getMnumber());
		stuServ.saveStudent(stu);
		return new ModelAndView("redirect:/admin/showStudents");
	}
	@GetMapping("/editTeacher")
	public String editTeacher(@RequestParam("teaid") String userid,Model model) {
		System.out.println(userid);
		model.addAttribute("teacher", teacherServ.getTeacherByUserId(userid));
		
		return "teacherEdit";
	}
	@PostMapping("/updateTeacher")
	public ModelAndView updateTeacher(@ModelAttribute Teacher teacher,@RequestParam("teaid")String userid) {
		Teacher tea=teacherServ.getTeacherByUserId(userid);
		tea.setEmail(teacher.getEmail());
		tea.setFname(teacher.getFname());
		tea.setLname(teacher.getLname());
		tea.setMnumber(teacher.getMnumber());
		teacherServ.saveTeacher(tea);
		return new ModelAndView("redirect:/admin/showTeachers");
	}
	
	
	

}
