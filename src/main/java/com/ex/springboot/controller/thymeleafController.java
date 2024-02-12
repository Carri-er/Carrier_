package com.ex.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ex.springboot.jdbc.EventDTO;
import com.ex.springboot.jdbc.IEventDAO;

@Controller
@RequestMapping
public class thymeleafController {

	@GetMapping("/")
	public String main() {
		System.out.println(eventDAO.list());
		return "thymeleaf/home/home";
	}

	@GetMapping("/feed")
	public String feed() {
		return "feed/feed2";
	}

	@GetMapping("/inner")
	public String inner() {
		return "inner/inner";
	}

	@GetMapping("/band")
	public String band() {
		return "band/band";
	}

	@GetMapping("/aicc")
	public String aicc() {
		return "aicc/aicc";
	}

	@GetMapping("/info")
	public String info() {
		return "info/info";
	}

	@GetMapping("/home")
	public String home() {
	
		return "home/home";
	}

	@GetMapping("/mypage")
	public String mypage() {
		return "mypage/mypage";
	}

	@GetMapping("/addMember")
	public String addMember() {
		return "Member/addMember";
	}

	@GetMapping("/login")
	public String login() {
		return "Member/login";
	}

	@GetMapping("/FindId")
	public String FindId() {
		return "Member/FindId";
	}

	@GetMapping("/FindPw")
	public String FindPw() {
		return "Member/FindPw";
	}

	@GetMapping("/nav")
	public String nav() {
		return "nav/nav";
	}

	@Autowired
	private IEventDAO eventDAO; // 다형성

	@RequestMapping("/events")
	public String userlistPage(Model model) {
		model.addAttribute("events",eventDAO.list());
		return "eventlist";
	}

	@RequestMapping("/welcome")
	public String welcome(Model model) {
		System.out.println(eventDAO.list() + "호출");
		model.addAttribute("list",eventDAO.list());
		return "welcome";
	}

}