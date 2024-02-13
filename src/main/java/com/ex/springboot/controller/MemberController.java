package com.ex.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ex.springboot.dto.MemberDTO;
import com.ex.springboot.dao.IMemberDAO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MemberController {
	
	@Autowired
	IMemberDAO dao;
	
	
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	
	
	@GetMapping("/addMember")
	public String addMember(Model model) {
			
		model.addAttribute("title", "회원가입");
		
		return "thymeleaf/Member/addMember";
	}
	
	@PostMapping("/signup")
	public String addMember(Model model, MemberDTO dto, HttpServletRequest request, @RequestParam("Member_profileimage") MultipartFile file) {
		
//		dao.addMember(
//				request.getParameter("writer"), 
//				request.getParameter("title"), 
//				request.getParameter("content"),
//				request.getParameter("content"),
//				request.getParameter("content"),
//				request.getParameter("content"),
//		);
		
		log.info("회원가입폼에서 입력받은 데이터: {}", dto);
		
		System.out.println("--회원가입 완--");
		
		return "thymeleaf/Member/login";
	}

}


