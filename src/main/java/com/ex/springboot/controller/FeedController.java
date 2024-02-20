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
import org.springframework.web.bind.annotation.RequestParam;
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
		/*List<FeedDTO> feedList = feed_dao.feedList();*/
		model.addAttribute("feedList", feed_dao.feedList());
		
		return "thymeleaf/feed/feed2";
	}
	
	// 피드 세부 - 페이지
	@GetMapping("/feedShow")
	public String feedShow(FeedDTO dto, Model model, HttpServletRequest request) {
		int Feed_num = Integer.parseInt(request.getParameter("num"));

		model.addAttribute("FeedList", feed_dao.feedShow(Feed_num));
		
		return "thymeleaf/feed/feed_show?num="+Feed_num;
	}

	// 피드 글쓰기 - 페이지
	@GetMapping("/feed_write")
	public String feed_write() {
		return "thymeleaf/feed/feed_write";
	}

	// 피드 글쓰기 - action
	@PostMapping("/feed_write_action")
	public String feed_write_action(Model model, HttpServletRequest request, @RequestParam("Feed_thumbnail") MultipartFile file) {
		String Member_Id = request.getParameter("Member_Id");
		String Member_profileimage = request.getParameter("Member_profileimage");
		String Feed_title = request.getParameter("Feed_title");
		String Feed_content = request.getParameter("Feed_content");
		String Feed_tripday = request.getParameter("yyyy")+"."+request.getParameter("mm")+"."+request.getParameter("dd");
		String Feed_theme = request.getParameter("Feed_theme");
		String Feed_area = request.getParameter("Feed_area");
		String Feed_thumbnail="";
		
		// 사용자가 파일을 안 넣었을 때
		if (file.isEmpty()) {
			Feed_thumbnail = "user.png";

			feed_dao.feedWrite(Member_Id, Member_profileimage, Feed_title, Feed_content, Feed_theme, Feed_area, Feed_tripday, Feed_thumbnail);
		} else {
			// 사용자가 파일을 넣었을 때
			try {
				StringBuilder fileNames = new StringBuilder();

				if (!fileNames.equals(null)) {

					Path fileNameAndPath = Paths.get(UPLOAD_MEMBER_DIRECTORY, file.getOriginalFilename());
					// 설정한 디렉토리에 파일 업로드
					fileNames.append(file.getOriginalFilename());
					byte[] fileSize = file.getBytes(); // 이미지에 대한 정보 값을 바이트 배열로 가져온다.
					Files.write(fileNameAndPath, fileSize);

					model.addAttribute("fileName", fileNames); // 이미지 이름 저장
							
					feed_dao.feedWrite(Member_Id, Member_profileimage, Feed_title, Feed_content, Feed_theme, Feed_area, Feed_tripday, fileNames.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(" :: 글쓰기 완 :: ");
			
		return "redirect:/feed";
	}


	

	// 피드 전체 보기
	/*
	 * @RequestMapping("/feed") public String feed(MemberDTO dto, HttpServletRequest
	 * request, Model model) {
	 * 
	 * model.addAttribute("MemberList", feed_dao.feedList());
	 * 
	 * return "thymeleaf/feed/feed2"; }
	 */

}
