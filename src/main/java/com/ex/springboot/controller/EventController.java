package com.ex.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ex.springboot.dto.EventDTO;

import jakarta.servlet.http.HttpServletRequest;

import com.ex.springboot.dao.IEventDAO;

@Controller
@RequestMapping
public class EventController {

	@GetMapping("/")
	public String main() {
		System.out.println(eventDAO.list());
		return "thymeleaf/home/home";
	}

	@GetMapping("/info")
	public String info(Model model) {
		System.out.println(eventDAO.list() + "호출");
		model.addAttribute("list",eventDAO.list());
		return "thymeleaf/info/info";
	}
	@GetMapping("/Event_view")
	public String Event_view(HttpServletRequest request, Model model) {
		String eid=request.getParameter("id");
		System.out.println(eventDAO.EventView(eid) + "호출");
		model.addAttribute("view",eventDAO.EventView(eid));
		return "thymeleaf/info/Event_view";
	}


	@Autowired
	private com.ex.springboot.dao.IEventDAO eventDAO; // 다형성

	@RequestMapping("/events")
	public String userlistPage(Model model) {
		model.addAttribute("events",eventDAO.list());
		return "eventlist";
	}


}