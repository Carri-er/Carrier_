package com.ex.springboot.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ex.springboot.dao.IMemberDAO;
import com.ex.springboot.dto.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping
public class FeedController {
	public static String UPLOAD_MEMBER_DIRECTORY = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\static\\img\\member_thumbnail";

	@Autowired
	IMemberDAO member_dao;

	// 회원가입 - Page
	@GetMapping("/feed_write")
	public String addMember() {
		return "thymeleaf/feed/feed_write";
	}

}
