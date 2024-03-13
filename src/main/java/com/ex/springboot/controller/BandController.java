package com.ex.springboot.controller;

import java.io.File; 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ex.springboot.dto.BandDTO;
import com.ex.springboot.dto.BandFeedDTO;
import com.ex.springboot.dto.Band_chatDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
public class BandController {
//	public static String UPLOAD_BAND_DIRECTORY = System.getProperty("user.dir")+"\\src\\main\\webapp\\resources\\band_thumbnail";
	public static String UPLOAD_BAND_DIRECTORY = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\img\\uploadBandThumnail";
	public static String UPLOAD_BANDFEED_DIRECTORY = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\img\\uploadBandFeed";
	
	@Autowired
	private com.ex.springboot.dao.IBandDAO bandDao; // 다형성
	
	
	@GetMapping("/band")
	public String band(Model model, HttpServletRequest request, HttpSession session) {
		//session = request.getSession();
		
		if( session.getAttribute("Member_Id") != null ) {
			String loginId = (String) session.getAttribute("Member_Id");
			model.addAttribute("joinBandList", bandDao.joinBandList(loginId));
			System.out.println("로그인 아이디 "+loginId);
		}
		
		
		model.addAttribute("bandList",bandDao.bandList());
		
		model.addAttribute("randomBandList", bandDao.randomBandList());
		
		
		return "thymeleaf/band/band";
	}
	
	
	
	//밴드 세부 페이지로 이동
	@GetMapping("/myBand")
	public String myBand(Model model, HttpServletRequest request,HttpSession session) {
		String band_code = request.getParameter("bandUrl");
		int num_band_code = Integer.parseInt(band_code);
		
		if( session.getAttribute("Member_Id") != null) {
			String loginId = (String) session.getAttribute("Member_Id");
			model.addAttribute("checkMember", bandDao.checkJoinMember(num_band_code, loginId));
			model.addAttribute("bandLoginMemberDto", bandDao.bandLoginMemberDto(loginId));
		}
		
		model.addAttribute("myBandList",bandDao.myBand(num_band_code));
		
		model.addAttribute("myBandFeedList",bandDao.bandFeedList(band_code));
		
		//model.addAttribute(band_code, bandDao.bandFeedCommentCount());
		
		
		model.addAttribute("bandUrl", band_code);
		model.addAttribute("bandFeedCommentAll", bandDao.bandFeedCommentAll());
		
		return "thymeleaf/band/myBand";
	}

	//밴드 피드 세부 페이지
	@RequestMapping("/bandFeedView")
	public String bandFeedView(Model model, HttpServletRequest request,HttpSession session) {
		String band_code = request.getParameter("bandUrl");
		String band_feed_num = request.getParameter("feednum");
		int num_band_code = Integer.parseInt(band_code);
		int num_band_feed_num = Integer.parseInt(band_feed_num);
		
		if( session.getAttribute("Member_Id") != null) {
			String loginId = (String) session.getAttribute("Member_Id");
			model.addAttribute("checkMember",bandDao.checkJoinMember(num_band_code, loginId));
			model.addAttribute("bandLoginMemberDto", bandDao.bandLoginMemberDto(loginId));
		}
		
		model.addAttribute("myBandList",bandDao.myBand(num_band_code));
		
		//model.addAttribute("myBandFeedList",bandDao.bandFeedList(band_code));
		
		//model.addAttribute(band_code, bandDao.bandFeedCommentCount());
		model.addAttribute("myBandFeedList",bandDao.bandFeedViewItem(num_band_feed_num));
		
		model.addAttribute("bandUrl", band_code);
		model.addAttribute("bandFeedCommentAll", bandDao.bandFeedCommentAll());
		
		
		return "thymeleaf/band/myBand_feedView";
	}
	
	//밴드 가입한 멤버리스트 페이지로 이동
		@GetMapping("/mybandMember")
		public String myBandMember(Model model, HttpServletRequest request,HttpSession session) {
			String searchTxt = request.getParameter("searchTxt");
			
			String band_code = request.getParameter("bandUrl");
			int num_band_code = Integer.parseInt(band_code);
			
			if( session.getAttribute("Member_Id") != null) {
				String loginId = (String) session.getAttribute("Member_Id");
				model.addAttribute("checkMember",bandDao.checkJoinMember(num_band_code, loginId));
			}
			
			
			
			model.addAttribute("myBandList",bandDao.myBand(num_band_code));
			
			model.addAttribute("myBandFeedList",bandDao.bandFeedList(band_code));
			
			if(searchTxt == null) {
				model.addAttribute("joinMemberList",bandDao.joinMemberList(num_band_code));
			} else {
				model.addAttribute("joinMemberList",bandDao.searchBandjoinMemberList(num_band_code, searchTxt));
			}
			
			
			model.addAttribute("bandUrl", band_code);
			
			return "thymeleaf/band/myBand_member";
		}
		
//	밴드 만들기 페이지로 이동
	@RequestMapping("/bandCreate")
	public String bandCreate(HttpSession session, Model model) {
		
		if( session.getAttribute("Member_Id") == null) {
			return "redirect:/login?msg=5";
		}
		model.addAttribute("bandList",bandDao.bandList());
		return "thymeleaf/band/band_create";
	}
	
	//밴드 생성하기 
	@RequestMapping("/band_create")
	public String band_Create(Model model, HttpServletRequest request, @RequestParam("band_thumnail") MultipartFile file) {
		
		try {
			String band_admin = request.getParameter("band_admin"); //밴드 만든 사람 : 관리자
			String band_theme = request.getParameter("band_theme"); //밴드 테마
			String band_area = request.getParameter("band_area"); //밴드 테마 
			String band_name = request.getParameter("band_name"); // 밴드 이름
			String band_content = request.getParameter("band_content"); // 밴드 소개
			//이미지 저장 
			
			BandDTO bandDto = new BandDTO();
			
			
			if(file.isEmpty()) {
				bandDao.bandCreateDao(band_name, band_admin, band_content, "gray_bg.png", band_theme, band_area);
				
				bandDto = bandDao.bandName_search(band_name);
				
				int create_band_code = bandDto.getBand_code();
				bandDao.bandJoinMember(create_band_code, band_admin);
				return "redirect:band";
			}
			UUID uuidOne = UUID.randomUUID();
			
			//A mutable sequence of characters
			StringBuilder fileNames = new StringBuilder();
			
			Path fileNameAndPath = Paths.get(UPLOAD_BAND_DIRECTORY, uuidOne + file.getOriginalFilename());
			// => Returns a {@code Path} by converting a path string => 이미지가 저장되는 경로
			fileNames.append(uuidOne + file.getOriginalFilename());
			byte[] fileSize = file.getBytes(); //이미지에 대한 정보 값을 바이트 배열로 가져온다.
			Files.write(fileNameAndPath, fileSize);
			
			model.addAttribute("msg", fileNameAndPath);
			model.addAttribute("fileName", fileNames); //밴드 대표 이미지 이름 저장.
			
			bandDao.bandCreateDao(band_name, band_admin, band_content, fileNames.toString(), band_theme, band_area );

			bandDto = bandDao.bandName_search(band_name);
			
			int create_band_code = bandDto.getBand_code();
			
			bandDao.bandJoinMember(create_band_code, band_admin);
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:band";
	}
	
	//밴드 이름 중복 검사 
	@RequestMapping("/checkBandName")
	@ResponseBody
	public String checkBandName(@RequestParam String bandName ) {
		
		System.out.println("bandName"+bandName);
		
		 if ( bandDao.bandName_search(bandName) != null ) {
			 
	            return "duplicate";
	            
	        } else {
	            return "unique";
	        }
	}
	
	//밴드 삭제하기 
	@RequestMapping("deleteBand")
	public String deleteBand(Model model, HttpServletRequest request) {
		String band_code_str = request.getParameter("bandUrl");
		
		int num_band_code = Integer.parseInt(band_code_str);
		
		bandDao.bandInfoDelete(num_band_code);
		bandDao.bandInfoDelete_member(num_band_code);
		
		return "redirect:/band";
	}
	
	//	밴드 피드 글쓰기 페이지로 이동
		@GetMapping("/bandFeedWrite")
		public String bandFeedWrite(Model model, HttpServletRequest request, HttpSession session) {
			String loginId = (String)session.getAttribute("Member_Id");
			String str_band_code = request.getParameter("bandUrl");
			
			int num_band_code = Integer.parseInt(str_band_code);
			if( loginId != null) {
				model.addAttribute("checkJoinMember", bandDao.checkJoinMember(num_band_code, loginId));
			}
			
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
		
		//밴드 피드 세부 에서 댓글 삭제
		@RequestMapping("/deleteFeedViewComment")
		public String deleteFeedViewComment(Model model, HttpServletRequest request) {
			String bandfeedCommentnum = request.getParameter("bandfeedCommentnum");
			String band_code = request.getParameter("bandUrl");
			String band_feed_num = request.getParameter("feednum");
			
			int num_band_code = Integer.parseInt(band_code);
			int num_band_feed_num = Integer.parseInt(band_feed_num);
			int num_bandfeedCommentnum = Integer.parseInt(bandfeedCommentnum);
			
			bandDao.band_feedComment_del(num_bandfeedCommentnum);
			
			return "redirect:bandFeedView?bandUrl="+num_band_code+"&feednum="+num_band_feed_num;
		}
		
		@RequestMapping("/updateFeedViewComment")
		public String updateFeedViewComment(Model model, HttpServletRequest request) {
			String bandfeedCommentnum = request.getParameter("bandfeedCommentnum");
			String band_code = request.getParameter("bandUrl");
			String band_feed_num = request.getParameter("feednum");
			String bandFeed_commentContent = request.getParameter("bandFeed_commentContent");
			
			int num_band_code = Integer.parseInt(band_code);
			int num_band_feed_num = Integer.parseInt(band_feed_num);
			int num_bandfeedCommentnum = Integer.parseInt(bandfeedCommentnum);
			
			
			bandDao.band_fedComment_update(bandFeed_commentContent, num_bandfeedCommentnum);
			
			return "redirect:bandFeedView?bandUrl="+num_band_code+"&feednum="+num_band_feed_num;
		}
		
		//밴드 멤버로 가입하기 
		@RequestMapping("/addBandMember")
		public String addBandMember(Model model, HttpServletRequest request, BandDTO bandDto) {
			String str_band_code = request.getParameter("bandCode");
			String joinMemberId = request.getParameter("joinMemberId");
			
			if(joinMemberId.equals("") || joinMemberId.equals(null)) {
				return "redirect:/login?msg=5";
			}
			
			int num_band_code = Integer.parseInt(str_band_code);
			
			bandDao.bandJoinMember(num_band_code, joinMemberId);
			
			bandDao.myBand(num_band_code);
			bandDao.bandMembercount_plus(num_band_code) ;
			
			
			return "redirect:myBand?bandUrl="+str_band_code;
		}
		
		//밴드 설정하기 페이지
		@RequestMapping("/setMyBand")
		public String setMyBand(Model model, HttpServletRequest request) {
			String str_band_code = request.getParameter("bandUrl");
			
			int num_band_code = Integer.parseInt(str_band_code);
			
			model.addAttribute("updateMyBand", bandDao.myBand(num_band_code));
			
			model.addAttribute("bandList", bandDao.band_updateList(num_band_code));
			
			return "thymeleaf/band/band_update";
			
		}
		
		// 수정 완료 시
		@RequestMapping("/band_update")
		public String bandUpdate(Model model, HttpServletRequest request,
				@RequestParam("band_thumnail") MultipartFile file) {
			String str_band_code = request.getParameter("bandUrl");
			int num_band_code = Integer.parseInt(str_band_code);
			String band_thumnail = request.getParameter("band_thumnail");
			String origin_band_thumnail = request.getParameter("origin_band_thumnail");
			String band_name = request.getParameter("band_name");
			String band_content = request.getParameter("band_content");
			String band_area = request.getParameter("band_area");
			String band_theme = request.getParameter("band_theme");
			
			model.addAttribute("bandList",bandDao.bandList());
			
			System.out.println("band_thumnail" + band_thumnail);
			System.out.println("origin_band_thumnail" + origin_band_thumnail);

			String go = "redirect:myBand?bandUrl=" + str_band_code;
			
			try {

				if ( file.isEmpty() ) {
					bandDao.bandInfoUpdate(origin_band_thumnail, band_name, band_content, num_band_code, band_theme, band_area);
					
					return go;
				} else {
					
					UUID uuidOne = UUID.randomUUID();

					// A mutable sequence of characters
					StringBuilder fileNames = new StringBuilder();

					Path fileNameAndPath = Paths.get(UPLOAD_BAND_DIRECTORY, uuidOne + file.getOriginalFilename());
					// => Returns a {@code Path} by converting a path string => 이미지가 저장되는 경로
					fileNames.append(uuidOne + file.getOriginalFilename());
					byte[] fileSize = file.getBytes(); // 이미지에 대한 정보 값을 바이트 배열로 가져온다.
					Files.write(fileNameAndPath, fileSize);

					model.addAttribute("msg", fileNameAndPath);
					model.addAttribute("fileName", fileNames); // 밴드 대표 이미지 이름 저장.
					System.out.println("밴드 수정 - 파일 이름 : "+ fileNames.toString());
					bandDao.bandInfoUpdate(fileNames.toString(), band_name, band_content, num_band_code, band_theme, band_area);

				}
			} catch (Exception e) {
				e.printStackTrace();

			}

			return go;
		}
		
		//밴드 회원탈퇴 
		@RequestMapping("/withdrawalMyBand")
		public String withdrawalMyBand(Model model, HttpServletRequest request, HttpSession session) {
			String str_band_code = request.getParameter("bandUrl");
			int num_band_code = Integer.parseInt(str_band_code);
			String login_id = (String) session.getAttribute("Member_Id");
			
			bandDao.withdrawalJoinBand(num_band_code, login_id);
			bandDao.bandMembercount_minus(num_band_code);
			
			return "redirect:/band";
		}
		
		
		//밴드 채팅
		@RequestMapping("/bandChat")
		public String bandchat(Model model, HttpServletRequest request, HttpSession session) {
			String str_band_code = request.getParameter("bandUrl");
			int num_band_code = Integer.parseInt(str_band_code);
			String partnerId = request.getParameter("partnerId");
			
			String loginId = (String) session.getAttribute("Member_Id");
			
			String chatRoom = str_band_code + partnerId + loginId;
			String chatRoom_reverse = str_band_code + loginId + partnerId;
			
			
			System.out.println("bandChat페이지 : ");
			System.out.println("chatRoom_name : "+chatRoom);
			System.out.println("num_band_code : "+ num_band_code);
			System.out.println("partnerId : "+partnerId);
			System.out.println("loginId"+loginId);
			
			
			if(bandDao.bandChatRoomCheck(chatRoom_reverse).size() == 0 ) {
				model.addAttribute("checkMember",bandDao.checkJoinMember(num_band_code, loginId));
				model.addAttribute("partnerId", partnerId);
				model.addAttribute("chatRoom",chatRoom);
							
				model.addAttribute("myBandList",bandDao.myBand(num_band_code));
				
				model.addAttribute("bandChatList", bandDao.bandChatList(chatRoom));
				model.addAttribute("bandChatListSize", bandDao.bandChatList(chatRoom).size());
				System.out.println("bandChatListSize : " + bandDao.bandChatList(chatRoom).size());
				model.addAttribute("bandUrl", num_band_code);
				
				return "thymeleaf/band/bandChat";
			} else {
				model.addAttribute("checkMember",bandDao.checkJoinMember(num_band_code, loginId));
				model.addAttribute("partnerId", partnerId);
				model.addAttribute("chatRoom",chatRoom_reverse);
							
				model.addAttribute("myBandList",bandDao.myBand(num_band_code));
				
				model.addAttribute("bandChatList", bandDao.bandChatList(chatRoom_reverse));
				model.addAttribute("bandChatListSize", bandDao.bandChatList(chatRoom_reverse).size());
				System.out.println("bandChatListSize : " + bandDao.bandChatList(chatRoom_reverse).size());
				model.addAttribute("bandUrl", num_band_code);
				
				return "thymeleaf/band/bandChat";
				
			}

		
		}

		 // AJAX 요청에 대한 핸들러
	    @PostMapping("/sendBandChat")
	    public String sendBandChat(@RequestBody String message1) throws ParseException {
	        // 받은 채팅 메시지를 데이터베이스에 저장하거나 처리합니다.
	        // 여기서는 간단히 채팅 메시지를 콘솔에 출력합니다.
	        System.out.println("Received band chat message: " + message1);
	       JSONParser parser = new JSONParser();
            JSONObject jsonObjects = (JSONObject) parser.parse(message1);
            System.out.println("jsonObjects > " + jsonObjects);
            JSONObject jobj1 = new JSONObject(jsonObjects);
            System.out.println("message : "+jobj1.get("message"));
            System.out.println("band_code : "+jobj1.get("sendBandUrl"));
            System.out.println("chatRoom : "+jobj1.get("sendchatRoom"));
            System.out.println("loginId : "+jobj1.get("sendLoginId"));
	        
            String sendBandUrl_str = jobj1.get("sendBandUrl").toString();
            int num_band_code = Integer.parseInt(sendBandUrl_str);
            
            bandDao.userbandChatWrite( jobj1.get("sendchatRoom").toString() , num_band_code , jobj1.get("sendLoginId").toString(), jobj1.get("message").toString());
            
            
	        return "Message sent successfully"; // 클라이언트에게 응답
	    }
		
		//ajax 채팅 리스트 
		@RequestMapping("/getBandChatList")
		@ResponseBody
		public List<Band_chatDTO> getBandChatList( ModelMap model, HttpServletRequest request, HttpSession session){
			String chatRoom = request.getParameter("chatRoom");
			List<Band_chatDTO> bandChatList = bandDao.bandChatList(chatRoom);
			
			return bandChatList; 
		}
		
		@RequestMapping("/myband_feed_commentWrite")
		public String myband_feed_commentWrite(Model model, HttpServletRequest request, HttpSession session) {
			String loginId = (String)session.getAttribute("Member_Id");
			String str_band_feed_num = request.getParameter("commentBandUrl");
			String band_feedComment_content = request.getParameter("band_feedComment_content");
			String str_bandCode = request.getParameter("bandUrl");
			
			int num_band_feed_num = Integer.parseInt(str_band_feed_num);
			int num_bandCode =  Integer.parseInt(str_bandCode);
			
			
			bandDao.band_feed_commentWrite(num_band_feed_num, loginId, band_feedComment_content);
			
			return "redirect:myBand?bandUrl="+num_bandCode;
		}
		
		@RequestMapping("/myband_feedView_commentWrite")
		public String myband_feedView_commentWrite(Model model, HttpServletRequest request, HttpSession session) {
			String loginId = (String)session.getAttribute("Member_Id");
			String str_band_feed_num = request.getParameter("commentBandUrl");
			String band_feedComment_content = request.getParameter("band_feedComment_content");
			String str_bandCode = request.getParameter("bandUrl");
			
			int num_band_feed_num = Integer.parseInt(str_band_feed_num);
			int num_bandCode =  Integer.parseInt(str_bandCode);
			
			
			bandDao.band_feed_commentWrite(num_band_feed_num, loginId, band_feedComment_content);
			
			return "redirect:bandFeedView?bandUrl="+num_bandCode+"&feednum="+num_band_feed_num;
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



