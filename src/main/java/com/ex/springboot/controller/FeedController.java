package com.ex.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.springboot.dao.IFeedDAO;
import com.ex.springboot.dto.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping
public class FeedController {
	public static String UPLOAD_MEMBER_DIRECTORY = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\static\\img\\member_thumbnail";

	@Autowired
	IFeedDAO feed_dao;

	// 피드 글쓰기
	@GetMapping("/feed_write")
	public String addMember() {
		return "thymeleaf/feed/feed_write";
	}
	
	// 피드 전체 보기
	@PostMapping("/feed")
	public String feed(MemberDTO dto, HttpServletRequest request, Model model) {

		model.addAttribute("MemberList", feed_dao.feedList());

		return "thymeleaf/feed/feed2";
	}

}
