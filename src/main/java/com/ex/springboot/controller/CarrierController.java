package com.ex.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class CarrierController {

	@GetMapping("/")
	public String main() {
		return "thymeleaf/home/home";
	}

//	@GetMapping("/feed")
//	public String feed() {
//		return "thymeleaf/feed/feed2";
//	}
//	@GetMapping("/feedshow")
//	public String feedshow() {
//		return "thymeleaf/feed/feed_show";
//	}
	@GetMapping("/inner")
	public String inner() {
		return "thymeleaf/inner/inner";
	}

//	@GetMapping("/band")
//	public String band() {
//		return "thymeleaf/band/band";
//	}

	@GetMapping("/aicc")
	public String aicc() {
		return "thymeleaf/aicc/aicc";
	}

//	@GetMapping("/info")
//	public String info(Model model) {
//		System.out.println(eventDAO.list() + "호출");
//		model.addAttribute("list", eventDAO.list());
//		return "thymeleaf/info/info";
//	}

//	@GetMapping("/Event_view")
//	public String Event_view(HttpServletRequest request, Model model) {
//		String eid = request.getParameter("id");
//		System.out.println(eventDAO.EventView(eid) + "호출");
//		model.addAttribute("view", eventDAO.EventView(eid));
//		return "thymeleaf/info/Event_view";
//	}

	@GetMapping("/home")
	public String home() {
		return "thymeleaf/home/home";
	}
	
//	@GetMapping("/mypage")
//	public String mypage(HttpServletRequest request, Model model) {
//		String id = request.getParameter("id");
//		
//		model.addAttribute("loginMember", member_dao.memberList(id));
//		
//		return "thymeleaf/mypage/mypage";
//	}

//	@GetMapping("/addMember")
//	public String addMember() {
//		return "thymeleaf/Member/addMember";
//	}

	@GetMapping("/map")
	public String map() {
		return "thymeleaf/map/map";
	}

	
//	@GetMapping("/login")
//	public String login() {
//		return "thymeleaf/Member/login";
//	}

//	@GetMapping("/FindId")
//	public String FindId() {
//		return "thymeleaf/Member/FindId";
//	}

//	@GetMapping("/FindPw")
//	public String FindPw() {
//		return "thymeleaf/Member/FindPw";
//	}

	@GetMapping("/nav")
	public String nav() {
		return "nav/nav";
	}

//	@Autowired
//	private com.ex.springboot.dao.IEventDAO eventDAO; // 다형성
//
//	@RequestMapping("/events")
//	public String userlistPage(Model model) {
//		model.addAttribute("events",eventDAO.list());
//		return "eventlist";
//	}

}