package com.ex.springboot.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.json.simple.JSONObject;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BandController {
//	public static String UPLOAD_BAND_DIRECTORY = System.getProperty("user.dir")+"\\src\\main\\webapp\\resources\\band_thumbnail";
	public static String UPLOAD_BAND_DIRECTORY = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\img\\uploadBandThumnail";
	public static String UPLOAD_BANDFEED_DIRECTORY = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\img\\uploadBandFeed";
	
	@Autowired
	private com.ex.springboot.dao.IBandDAO bandDao; // 다형성
	
	@GetMapping("/band")
	public String band(Model model) {
		model.addAttribute("bnadList",bandDao.bandList());
		
		return "thymeleaf/band/band";
	}
	@GetMapping("/myBand")
	public String myBand(Model model, HttpServletRequest request) {
		String band_code = request.getParameter("bandUrl");
		model.addAttribute("myBandList",bandDao.myBand(band_code));
		model.addAttribute("bandUrl", band_code);
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
			
			if(file.isEmpty()) {
				bandDao.bandCreateDao(band_name, band_admin, band_content, "gray_bg.png");
				return "redirect:band";
			}
			
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
	@GetMapping("/bandFeedWrite")
	public String bandFeedWrite(Model model, HttpServletRequest request) {
//		model.addAttribute("bandUrl", request.getParameter("bandUrl"));
		return "thymeleaf/band/bandFeedWrite";
	}
	
//	썸머노트로 글을 작성하였을 때 
	@RequestMapping("/writeFeed")
	public String writeFeed(Model model, HttpServletRequest request) {
//		String band_code = request.getParameter("bandUrl");
		
//		return "redirect:myBand?bandUrl="+band_code;
		return "redirect:band";
	}
	
	

	@PostMapping("/uploadSummernoteImageFile")
	   @ResponseBody
	   public JSONObject uploadImage(Model model, @RequestParam("file") MultipartFile file, Object msg) throws IOException {
	      JSONObject obj = new JSONObject();
//	      System.out.println(UPLOAD_DIRECTORY_Edit);
	      StringBuilder fileNames = null;
	        UUID uuidOne = UUID.randomUUID();

	      try {
	         fileNames = new StringBuilder();
	         Path fileNameAndPath = Paths.get(UPLOAD_BANDFEED_DIRECTORY, uuidOne+file.getOriginalFilename());
	         // => Returns a {@code Path} by converting a path string => 이미지가 저장되는 경로
	         fileNames.append(uuidOne+file.getOriginalFilename());
	         byte[] fileSize = file.getBytes();

	         Files.write(fileNameAndPath, fileSize);
	         model.addAttribute("msg", fileNameAndPath);

	         System.out.println("파일이 저장되는 경로 : " + fileNameAndPath);
	         System.out.println("fileNames에서 얻은 이미지 파일명 : " + fileNames);
	         System.out.println("모델에 저장한 메세지 : " + model.getAttribute("msg"));
	         System.out.println("==============================================");
	         obj.put("success", true);
	         obj.put("업로드 결과", "성공");
	         obj.put("url", "img/uploadBandFeed/"+fileNames.toString());
	         obj.put("파일 저장명", fileNameAndPath);
	         obj.put("파일 용량", fileSize.length + "byte");
	         model.addAttribute("fileName", fileNames);
	      } catch (Exception e) {
	         e.printStackTrace();
	         obj.put("success", false);
	         obj.put("업로드 결과", "실패");
	      }
	      try {
	         Thread.sleep(3000);
	      } catch (InterruptedException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      } // 이미지 폴더 새로고침 시간용 딜레이
	      
	      return obj;
//	      return obj.toJSONString();
	   }

	
	
	
	
}



