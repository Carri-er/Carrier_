package com.ex.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.springboot.dto.MemberDTO;

@Controller
@RequestMapping
public class MemberController {
	
	@GetMapping("/addMember")
	public String addMember() {
		return "thymeleaf/Member/addMember";
	}
	
	@PostMapping("/addMember")
	public String addMemberForm(MemberDTO dto) {
		
		
		
		return "redirect:";
	}

}
