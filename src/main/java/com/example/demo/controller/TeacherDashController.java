package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entities.Notification;
import com.example.demo.entities.Results;
import com.example.demo.entities.Session;
import com.example.demo.entities.Student;
import com.example.demo.entities.StudentQuery;
import com.example.demo.entities.Subject;
import com.example.demo.entities.Teacher;
import com.example.demo.entities.User;
import com.example.demo.helper.FileUploadUtil;
import com.example.demo.repository.SessionRepository;
import com.example.demo.service.NotificationService;
import com.example.demo.service.ResultsService;
import com.example.demo.service.SessionService;
import com.example.demo.service.StudentQueryService;
import com.example.demo.service.StudentService;
import com.example.demo.service.SubjectService;
import com.example.demo.service.TeacherService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/teacher")
public class TeacherDashController {
	@Autowired
	public SessionService sessServ;
	
	@Autowired
	public StudentService stuServ;
	
	@Autowired
	public NotificationService notServ;
	
	@Autowired
	public StudentQueryService sqs;
	
	@Autowired
	public ResultsService resServ;
	
	@Autowired 
	public TeacherService teaServ;
	@Autowired
	public BCryptPasswordEncoder bcrypt;
	@Autowired
	public UserService userServ;
	@Autowired
	public SubjectService subServ;

	
	
	
	@GetMapping("/showSession")
	public String showSessions(Model model,Principal principal) {
		List<Session> sessions=sessServ.getSessions();
		model.addAttribute("sessions", sessions);
		String id=principal.getName();
		List<Subject> subjects=subServ.showAllSubjects();
		model.addAttribute("subjects", subjects);
		Teacher tea=teaServ.getTeacherByUserId(id);
		model.addAttribute("teaName", tea.getFname());
		model.addAttribute("teaUser", tea.getUserid());
		return "teachersHome";
		
	}
	@GetMapping("/results")
	public String showResults(Model model ,Principal principal) {
		List<Results>  results=resServ.getAllResults();
		String id=principal.getName();
		Teacher tea=teaServ.getTeacherByUserId(id);
		model.addAttribute("teaName", tea.getFname());
		model.addAttribute("teaUser", tea.getUserid());
		
		model.addAttribute("results",results);
		return "teacherResults";
	}
	@PostMapping("/saveSession")
	public String saveSession(@ModelAttribute Session session,@RequestParam("notes") MultipartFile multipartFile1  , Model model) throws Exception {
		String filePath = StringUtils.cleanPath(multipartFile1.getOriginalFilename());
		String arr[]=session.getVlink().split("=");
		System.out.println(arr[1]);
		String link="https://www.youtube.com/embed/"+arr[1];
		session.setVlink(link);
		
		session.setStatus(false);
		session.setNotespath(filePath);
		sessServ.saveSession(session);
		
		
		
		FileUploadUtil.saveFile("static/notes/", filePath, multipartFile1);
		List<Session> sessions=sessServ.getSessions();
		model.addAttribute("sessions", sessions);
		
		
		return "teachersHome";
	}
	@GetMapping("/takeAttend")
	public String takeAttendance (@RequestParam("sessid")Integer sessid,Model model,Principal principal) {
		Session sess=sessServ.getOneSession(sessid);
		if(sess.getStatus()) {
		
			List<Student> students=sessServ.getStudentsPresent(sessid);
		String id=principal.getName();
		
		Teacher tea=teaServ.getTeacherByUserId(id);
		model.addAttribute("teaName", tea.getFname());
		model.addAttribute("students",students);
		model.addAttribute("status", sess.getStatus());
		System.out.println(sess.getStatus());
		model.addAttribute("sessid",sess.getId() );
			System.out.println(sessid);
			return "attendance";
			}
			else{
				List<Student> studentsAttend=stuServ.getAllStudents();
				

				String id=principal.getName();
				
				Teacher tea=teaServ.getTeacherByUserId(id);
				model.addAttribute("teaName", tea.getFname());
				model.addAttribute("students",studentsAttend);
				model.addAttribute("status", sess.getStatus());
				System.out.println(sess.getStatus());
				model.addAttribute("sessid",sess.getId() );
					System.out.println(sessid);
				return "attendance";
				
			}
		
	}
	@PostMapping("/saveAttend")
	public String saveAttend(@RequestParam("attendrecords") String[] checkarr,@RequestParam("sessid") String id,Model model,Principal principal ) {
		
		Integer sessid=Integer.parseInt(id);
		
		
		
		if(checkarr !=null) {
			sessServ.setAttendance(checkarr, sessid);
			Session sess=sessServ.getOneSession(sessid);
			sess.setStatus(true);
			sessServ.saveSession(sess);

			List<Student> students=sessServ.getStudentsPresent(sessid);
		String teaid=principal.getName();
		
		Teacher tea=teaServ.getTeacherByUserId(teaid);
		model.addAttribute("teaName", tea.getFname());
		model.addAttribute("students",students);
		model.addAttribute("status", sess.getStatus());
		System.out.println(sess.getStatus());
		model.addAttribute("sessid",sess.getId() );
		return "attendance";
			
		}
		else {
			return "attendance";
			
		}
		
		
		
	}
	@GetMapping("/showVideo")
	public String showVideo (@RequestParam("sessid")Integer sessid,Model model) {
		Session sess=sessServ.getOneSession(sessid);
		model.addAttribute("sess",sess);
		
			System.out.println(sessid);
		return "studymaterial";
	}
	@GetMapping("/showProfile")
	public String showProfile(@RequestParam("teaid")String userid,Model model,Principal principal) {
		Teacher teacher=teaServ.getTeacherByUserId(userid);
		model.addAttribute("teacher", teacher);
		model.addAttribute("teaName", teacher.getFname());
		model.addAttribute("teaid",principal.getName());
		return "teacherProfile";
		
	}
	@GetMapping("/deleteSession")
	public ModelAndView deleteSession (@RequestParam("sessid")Integer sessid,Model model) {
		sessServ.deleteSession(sessid);
		sessServ.deleteSessionAttendance(sessid);
		
		
			System.out.println(sessid);
		return new ModelAndView("redirect:/teacher/showSession");
	}
	@GetMapping("/showNotices")
	public String showNotices(Model model,Principal principal) {
		List<Notification> notices=notServ.showAllNotifications();
		Teacher tea=teaServ.getTeacherByUserId(principal.getName());
		model.addAttribute("teaName", tea.getFname());
		model.addAttribute("teaUser",tea.getUserid());
		model.addAttribute("notices", notices);
		
		return "notifications";
	}
	@GetMapping("/showQueries")
	public String ShowQueries(Model model,Principal principal) {
		String id=principal.getName();
		Teacher tea=teaServ.getTeacherByUserId(id);
		model.addAttribute("teaName", tea.getFname());
		model.addAttribute("teaUser", tea.getUserid());
		List<StudentQuery>sq=sqs.NotificationsByTeacherId(id);
		model.addAttribute("studentQuery", sq);
		
		return "queriesTeachers";
	}
	@PostMapping("/sendAnswer")
	public ModelAndView sendAnswer(@RequestParam("qid")String qid,@RequestParam("answer")String answer) {
		Integer id=Integer.parseInt(qid);
		StudentQuery sq=sqs.getSingleQuery(id);
		sq.setAnswer(answer);
		sqs.savesq(sq);
		return new ModelAndView("redirect:/teacher/showQueries");
	}
	@GetMapping("/changePassword")
	public String changePassword(Model model,Principal principal) {
		model.addAttribute("userid", principal.getName());
		return "changePasswordT";
		
	}
	@PostMapping("/updatePassword")
	public String updatePassword(@RequestParam("password")String password,Model model,Principal principal) {
		Integer userid=Integer.parseInt(principal.getName());
		User user=userServ.findOneUser(userid);
		user.setPassword(bcrypt.encode(password));
		userServ.saveUser(user);
		return "changePasswordT";
	}
	
	

}
