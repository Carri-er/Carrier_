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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ex.springboot.dto.BandFeedDTO;

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
		model.addAttribute("bandList",bandDao.bandList());
		
		return "thymeleaf/band/band";
	}
	
	//밴드 세부 페이지로 이동
	@GetMapping("/myBand")
	public String myBand(Model model, HttpServletRequest request) {
		String band_code = request.getParameter("bandUrl");
		
		model.addAttribute("myBandList",bandDao.myBand(band_code));
		model.addAttribute("myBandFeedList",bandDao.bandFeedList(band_code));
		
//		model.addAttribute("feedwriterImg", bandDao.bandWriterImg(band_code));
		
	
		
		
		model.addAttribute("bandUrl", band_code);
		
		return "thymeleaf/band/myBand";
	}

//	밴드 만들기 페이지로 이동
	@RequestMapping("/bandCreate")
	public String bandCreate() {
		return "thymeleaf/band/band_create";
	}
	
	//
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
			model.addAttribute("bandUrl", request.getParameter("bandUrl")); // band_code 데이터
			return "thymeleaf/band/bandFeedWrite";
		}

		//bnadFeedWrite 페이지에서 글쓰기 버튼을 눌렸을 때
		@PostMapping("/writeFeed")
		public String writeFeed(Model model, HttpServletRequest request) {
			String member_id = request.getParameter("member_id");
			String band_code_str = request.getParameter("bandUrl");
			int band_code = Integer.parseInt(band_code_str);
			String band_feed_content = request.getParameter("band_feed_content");
			
			bandDao.bandFeedWrite(band_code, member_id, band_feed_content);
			
			String go = "redirect:myBand?bandUrl="+request.getParameter("bandUrl");
			
			return go;
		}
		
		//밴드 피드 글 수정하기 
		@RequestMapping("/bandFeedUpdate")
		public String bandFeedUpdate(Model model, HttpServletRequest request) {
			System.out.println(request.getParameter("bandUrl"));
			System.out.println(request.getParameter("bandfeednum"));
			System.out.println(request.getParameter("member_id"));
			
			model.addAttribute("member_id", request.getParameter("member_id"));
			model.addAttribute("bandUrl", request.getParameter("bandUrl"));
			model.addAttribute("bandfeednum", request.getParameter("bandfeednum"));
			model.addAttribute("feedcontent", request.getParameter("feedcontent"));
			
			return "thymeleaf/band/bandFeedUpdate";
		}
		
		@PostMapping("/updateFeed")
		public String updateFeed(Model model, HttpServletRequest request) {
			String member_id = request.getParameter("member_id");
			String bandfeednum_str = request.getParameter("bandfeednum");
			String band_code_str = request.getParameter("bandUrl");
			
			System.out.println(member_id);
			System.out.println(bandfeednum_str);
			System.out.println(band_code_str);
			
			int band_code = Integer.parseInt(band_code_str);
			int bandfeednum = Integer.parseInt(bandfeednum_str);
			
			String band_feed_content = request.getParameter("band_feed_content");
			
			
			
			bandDao.bandFeedUpdate(band_feed_content,member_id, band_code, bandfeednum);
			
			String go = "redirect:myBand?bandUrl="+request.getParameter("bandUrl");
			
			
			
			return go;
		}
		
		@RequestMapping("/deleteFeed")
		public String deleteFeed(Model model, HttpServletRequest request) {
			String bandfeednum_str = request.getParameter("bandfeednum");
			String band_code_str = request.getParameter("bandUrl");
			
			int band_code = Integer.parseInt(band_code_str);
			int bandfeednum = Integer.parseInt(bandfeednum_str);
			
			System.out.println("band_code = "+ band_code);
			System.out.println("bandfeednum = "+ bandfeednum);
			
			bandDao.bandFeedDelete(band_code, bandfeednum);
			
			String go = "redirect:myBand?bandUrl="+request.getParameter("bandUrl");
			
			return go;
		}
		
		
		// 썸머노트 ajax
		@PostMapping("/uploadSummernoteImageFile")
		@ResponseBody
		public JSONObject uploadImage(Model model, @RequestParam("file") MultipartFile file, Object msg)
				throws IOException {
			JSONObject obj = new JSONObject();
//		      System.out.println(UPLOAD_DIRECTORY_Edit);
			StringBuilder fileNames = null;
			UUID uuidOne = UUID.randomUUID();

			try {
				fileNames = new StringBuilder();
				Path fileNameAndPath = Paths.get(UPLOAD_BANDFEED_DIRECTORY, uuidOne + file.getOriginalFilename());
				// => Returns a {@code Path} by converting a path string => 이미지가 저장되는 경로
				fileNames.append(uuidOne + file.getOriginalFilename());
				byte[] fileSize = file.getBytes();

				Files.write(fileNameAndPath, fileSize);
				model.addAttribute("msg", fileNameAndPath);

				System.out.println("파일이 저장되는 경로 : " + fileNameAndPath);
				System.out.println("fileNames에서 얻은 이미지 파일명 : " + fileNames);
				System.out.println("모델에 저장한 메세지 : " + model.getAttribute("msg"));
				System.out.println("==============================================");
				obj.put("success", true);
				obj.put("업로드 결과", "성공");
				obj.put("url", "img/uploadBandFeed/" + fileNames.toString());
				obj.put("파일 저장명", fileNameAndPath);
				obj.put("파일 용량", fileSize.length + "byte");
				model.addAttribute("fileName", fileNames);
			} catch (Exception e) {
				e.printStackTrace();
				obj.put("success", false);
				obj.put("업로드 결과", "실패");
			}
			
			// 이미지 폴더 새로고침 시간용 딜레이
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return obj;
		}
	
		
}



