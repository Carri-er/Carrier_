package com.ex.springboot.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BandController {
	public static String UPLOAD_BAND_DIRECTORY = System.getProperty("user.dir")+"\\src\\main\\webapp\\resources\\band_thumnail";
	
	@Autowired
	private com.ex.springboot.dao.IBandDAO bandDao; // 다형성
	
	@GetMapping("/band")
	public String band(Model model) {
		model.addAttribute("bnadList",bandDao.bandList());
		
		return "thymeleaf/band/band";
	}
	@GetMapping("/myBand")
	public String myBand(Model model, HttpServletRequest request) {
		String band_code =request.getParameter("bandUrl");
		model.addAttribute("myBandList",bandDao.myBand(band_code));
		return "thymeleaf/band/myBand";
	}

//	밴드 만들기 페이지로 이동
	@RequestMapping("/bandCreate")
	public String bandCreate() {
		return "thymeleaf/band/band_create";
	}
	

	@RequestMapping("/band_create")
	public String band_Create(Model model, HttpServletRequest request, @RequestParam("band_thumnail") MultipartFile file) {
		
		try {

			String band_admin = request.getParameter("band_admin"); //밴드 만든 사람 : 관리자
			String band_thumnail = request.getParameter("band_thumnail"); //밴드 대표 이미지
			String band_name = request.getParameter("band_name"); // 밴드 이름
			String band_content = request.getParameter("band_content"); // 밴드 소개
			//이미지 저장 
			
			//A mutable sequence of characters
			StringBuilder fileNames = new StringBuilder();
			
			Path fileNameAndPath = Paths.get(UPLOAD_BAND_DIRECTORY, file.getOriginalFilename());
			// => Returns a {@code Path} by converting a path string : 이미지가 저장되는 경로
			fileNames.append(file.getOriginalFilename());
			byte[] fileSize = file.getBytes(); //이미지에 대한 정보 값을 바이트 배열로 가져온다.
			Files.write(fileNameAndPath, fileSize);
			
			model.addAttribute("fileName", fileNames); //밴드 대표 이미지 이름 저장.
			
			bandDao.bandCreateDao(band_name, band_admin, band_content, fileNames.toString());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:band";
	}
	
//	밴드 피드 글쓰기 페이지로 이동
	@RequestMapping("/bandFeedWrite")
	public String bandFeedWrite() {
		return "thymeleaf/band/bandFeedWrite";
	}
	
}
