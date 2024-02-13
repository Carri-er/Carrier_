package com.ex.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class thymeleafController {
	@GetMapping("/")
	public String main() {
		return "home/home";
	}
	@GetMapping("/board")
	public String board() {
		return "thymeleaf/board";
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
}