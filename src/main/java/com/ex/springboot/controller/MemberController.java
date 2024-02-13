package com.ex.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.springboot.dao.IMemberDAO;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping
public class MemberController {

	@Autowired
	IMemberDAO member_dao;
	
	@GetMapping("/addMember")
	public String addMember() {
		return "thymeleaf/Member/addMember";
	}
	
	@PostMapping("/addMember")
	public String signup(HttpServletRequest request, Model model) {
		
		String Member_Email = request.getParameter("mail1")+request.getParameter("mail2");
		int Member_Age = Integer.parseInt(request.getParameter("birth"));
		String Member_Phone = request.getParameter("phone1")+request.getParameter("phone2")+request.getParameter("phone3");
		
		
		
		
		
		
		member_dao.addMember(
				request.getParameter("Member_Name"), 
				Member_Age, 
				request.getParameter("Member_Id"),
				Member_Email,
				Member_Phone,
				request.getParameter("Member_Pw"),
				request.getParameter("Member_Area"),
				request.getParameter("Member_Thema")
		);
		
		
		return "thymeleaf/Member/login";
	}

}
