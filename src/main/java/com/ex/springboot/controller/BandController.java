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
@RequestMapping
public class BandController {
	public static String UPLOAD_BAND_DIRECTORY = System.getProperty("user.dir")+"/upload/bandThumbnail";
	
	@GetMapping("/band")
	public String band() {
		return "thymeleaf/band/band";
	}
	
	@RequestMapping("/bandCreate")
	public String bandCreate(Model model) {
		return "band_create";
	}
	
	@Autowired
	private com.ex.springboot.dao.IBandDAO bnadDAO; // 다형성
	
	@RequestMapping("/band_create")
	public String band_Create(Model model, HttpServletRequest request, @RequestParam("band_thumnail") MultipartFile file) {
		
		try {
			String band_admin = request.getParameter("band_admin");
			//A mutable sequence of characters
			StringBuilder fileNames = new StringBuilder();
			
			Path fileNameAndPath = Paths.get(UPLOAD_BAND_DIRECTORY, file.getOriginalFilename());
			// => Returns a {@code Path} by converting a path string : 이미지가 저장되는 경로
			fileNames.append(file.getOriginalFilename());
			byte[] fileSize = file.getBytes(); //이미지에 대한 정보 값을 바이트 배열로 가져온다.
			Files.write(fileNameAndPath, fileSize);
			
			model.addAttribute("fileName", fileNames);
		}catch(Exception e) {
			
		}
		 
		
		
		return "redirect:band";
	}
	
}
