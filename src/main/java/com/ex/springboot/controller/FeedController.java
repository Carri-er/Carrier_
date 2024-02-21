package com.ex.springboot.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.ex.springboot.dao.IFeedDAO;
import com.ex.springboot.dto.FeedDTO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping
public class FeedController {
	public static String UPLOAD_MEMBER_DIRECTORY = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\static\\img\\feed_thumbnail";

	@Autowired
	IFeedDAO feed_dao;

	// 피드 리스트
	@GetMapping("/feed")
	public String feed(Model model, HttpServletRequest request) {
		/* List<FeedDTO> feedList = feed_dao.feedList(); */
		model.addAttribute("feedList", feed_dao.feedList());

		return "thymeleaf/feed/feed2";
	}

	// 피드 세부 - 페이지
	@GetMapping("/feed_show")
	public String feedShow(FeedDTO dto, Model model, HttpServletRequest request) {
		int Feed_num = Integer.parseInt(request.getParameter("num"));

		model.addAttribute("feedList", feed_dao.feedShow(Feed_num));

		return "thymeleaf/feed/feed_show";
	}

	// 피드 글쓰기 - 페이지
	@GetMapping("/feed_write")
	public String feed_write() {
		return "thymeleaf/feed/feed_write";
	}

	// 피드 글쓰기 - action
	@PostMapping("/feed_write_action")
	public String feed_write_action(Model model, HttpServletRequest request,
			@RequestPart(value = "Feed_thumbnail1", required = false) MultipartFile file1,
			@RequestPart(value = "Feed_thumbnail2", required = false) MultipartFile file2,
			@RequestPart(value = "Feed_thumbnail3", required = false) MultipartFile file3) {

		String Member_Id = request.getParameter("Member_Id");
		String Member_profileimage = request.getParameter("Member_profileimage");
		String Feed_title = request.getParameter("Feed_title");
		String Feed_content = request.getParameter("Feed_content");
		String Feed_theme = request.getParameter("Feed_theme");
		String Feed_area = request.getParameter("Feed_area");

//		System.out.println(Member_Id + "/" + Member_profileimage + "/" + Feed_title + "/" + Feed_content + "/"
//				+ Feed_theme + "/" + Feed_area);
		try {
			StringBuilder fileNames = new StringBuilder();

			if (file1 != null && !file1.isEmpty()) {
				String fileName1 = saveFile(file1);
				fileNames.append(fileName1);
			}

			if (file2 != null && !file2.isEmpty()) {
				String fileName2 = saveFile(file2);
				if (fileNames.length() > 0) {
					fileNames.append(",");
				}
				fileNames.append(fileName2);
			}

			if (file3 != null && !file3.isEmpty()) {
				String fileName3 = saveFile(file3);
				if (fileNames.length() > 0) {
					fileNames.append(",");
				}
				fileNames.append(fileName3);
			}

			// 파일명이 없는 경우 기본 이미지 사용
			if (fileNames.length() == 0 || fileNames.isEmpty()) {
				fileNames.append("user.png");
			}

			// DB에 게시물 정보 저장
			feed_dao.feedWrite(Member_Id, Member_profileimage, Feed_title, Feed_content, Feed_theme, Feed_area,
					fileNames.toString());
			System.out.println("fileNames.toString():" + fileNames.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/feed";
	}

	private String saveFile(MultipartFile file) throws Exception {
		String fileName = file.getOriginalFilename();
		Path fileNameAndPath = Paths.get(UPLOAD_MEMBER_DIRECTORY, fileName);
		Files.write(fileNameAndPath, file.getBytes());
		return fileName;
	}

	// 피드 세부 - 페이지
	@GetMapping("/feed_update")
	public String feed_update(FeedDTO dto, Model model, HttpServletRequest request) {
		int Feed_num = Integer.parseInt(request.getParameter("num"));

		model.addAttribute("feedList", feed_dao.feedShow(Feed_num));

		return "thymeleaf/feed/feed_update";
	}
	
	// 피드 세부 - 페이지
	@PostMapping("/feed_update_action")
	public String feed_update_action(Model model, HttpServletRequest request,
			@RequestPart(value = "Feed_thumbnail1", required = false) MultipartFile file1,
			@RequestPart(value = "Feed_thumbnail2", required = false) MultipartFile file2,
			@RequestPart(value = "Feed_thumbnail3", required = false) MultipartFile file3) {
		int Feed_num = Integer.parseInt(request.getParameter("num"));
		
		String Feed_title = request.getParameter("Feed_title");
		String Feed_content = request.getParameter("Feed_content");
		String Feed_theme = request.getParameter("Feed_theme");
		String Feed_area = request.getParameter("Feed_area");
		
		System.out.println(Feed_title + "/" + Feed_content + "/"
				+ Feed_theme + "/" + Feed_area);
		
		try {
			StringBuilder fileNames = new StringBuilder();

			if (file1 != null && !file1.isEmpty()) {
				String fileName1 = saveFile(file1);
				fileNames.append(fileName1);
			}

			if (file2 != null && !file2.isEmpty()) {
				String fileName2 = saveFile(file2);
				if (fileNames.length() > 0) {
					fileNames.append(",");
				}
				fileNames.append(fileName2);
			}

			if (file3 != null && !file3.isEmpty()) {
				String fileName3 = saveFile(file3);
				if (fileNames.length() > 0) {
					fileNames.append(",");
				}
				fileNames.append(fileName3);
			}

			// 파일명이 없는 경우 기본 이미지 사용
			if (fileNames.length() == 0 || fileNames.isEmpty()) {
				fileNames.append("user.png");
			}

			// DB에 게시물 정보 저장
			feed_dao.feedUpdate(Feed_title, Feed_content, Feed_theme, Feed_area,
					fileNames.toString(), Feed_num);
			
			System.out.println("fileNames.toString():" + fileNames.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/feed";
	}
	
	
	
}
