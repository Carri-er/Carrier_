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

import com.ex.springboot.dao.IMemberDAO;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping
public class MemberController {
	
	public static String UPLOAD_MEMBER_DIRECTORY = System.getProperty("user.dir")+"\\src\\main\\webapp\\resources\\member_thumbnail";
	
	@Autowired
	IMemberDAO member_dao;
	
	@GetMapping("/addMember")
	public String addMember() {
		return "thymeleaf/Member/addMember";
	}
	
	@PostMapping("/signup")
	public String signup(HttpServletRequest request, Model model, @RequestParam("Member_profileimage") MultipartFile file) {
//		if(!(request.getParameter("Member_Pw").equals(request.getParameter("passwordCK"))){
//			
//		}
		try {
				
			String Member_Email = request.getParameter("mail1")+"@"+request.getParameter("mail2");
			int Member_Age = Integer.parseInt(request.getParameter("Member_Age"));
			String Member_Phone = request.getParameter("phone1")+"-"+request.getParameter("phone2")+"-"+request.getParameter("phone3");
	
			StringBuilder fileNames = new StringBuilder();
			
			String fileEmpty = "file_empty";
			
			// 사용자가 파일을 넣었을 때
			if(!fileNames.equals(null)) {
			
				Path fileNameAndPath = Paths.get(UPLOAD_MEMBER_DIRECTORY, file.getOriginalFilename());
				// 설정한 디렉토리에 파일 업로드
				fileNames.append(file.getOriginalFilename());
				byte[] fileSize = file.getBytes(); // 이미지에 대한 정보 값을 바이트 배열로 가져온다.
				Files.write(fileNameAndPath, fileSize);
				
				model.addAttribute("fileName", fileNames); // 이미지 이름 저장
				
				member_dao.addMember(
						request.getParameter("Member_Name"), 
						Member_Age, 
						request.getParameter("Member_Id"),
						Member_Email,
						Member_Phone,
						request.getParameter("Member_Pw"),
						request.getParameter("Member_Area"),
						request.getParameter("Member_Thema"),
						fileNames.toString()
				);
				System.out.println(fileNames.toString());
				
			}else {
				model.addAttribute("Member_profileimage", fileEmpty); // 이미지 이름 저장
				
				member_dao.addMember(
						request.getParameter("Member_Name"), 
						Member_Age, 
						request.getParameter("Member_Id"),
						Member_Email,
						Member_Phone,
						request.getParameter("Member_Pw"),
						request.getParameter("Member_Area"),
						request.getParameter("Member_Thema"),
						request.getParameter("Member_profileimage")
				);
			}
			System.out.println("--회원가입 완--");
		}catch(Exception e) {
			e.printStackTrace();
		}
	
		return "thymeleaf/Member/login";
	}

}
