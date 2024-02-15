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
import com.ex.springboot.dto.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping
public class MemberController {
	public static String UPLOAD_MEMBER_DIRECTORY = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\img\\member_thumbnail";
	
	@Autowired
	IMemberDAO member_dao;
	
	// 회원가입 - Page
	@GetMapping("/addMember")
	public String addMember() {
		return "thymeleaf/Member/addMember";
	}
	
	// 회원가입 - Logic
	@PostMapping("/signup")
	public String signup(HttpServletRequest request, Model model, @RequestParam("Member_profileimage") MultipartFile file) {

		String Member_Email = request.getParameter("mail1")+"@"+request.getParameter("mail2");
		int Member_Age = Integer.parseInt(request.getParameter("Member_Age"));
		String Member_Phone = request.getParameter("phone1")+"-"+request.getParameter("phone2")+"-"+request.getParameter("phone3");
		
		
		// 사용자가 파일을 안 넣었을 때
		if(file.isEmpty()) {
			String Member_profileimage = "user.png";
			
			member_dao.addMember(
					request.getParameter("Member_Name"), 
					Member_Age, 
					request.getParameter("Member_Id"),
					Member_Email,
					Member_Phone,
					request.getParameter("Member_Pw"),
					request.getParameter("Member_Area"),
					request.getParameter("Member_Thema"),
					Member_profileimage
					);
		} else {
			// 사용자가 파일을 넣었을 때
			try {
				StringBuilder fileNames = new StringBuilder();
				
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
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			System.out.println(" :: 회원가입 완 :: ");
			
		}
		
		return "thymeleaf/Member/login";
	}
	
	// 로그인 - Page
	@GetMapping("/login")
	public String login() {
		return "thymeleaf/Member/login";
	}
	
	
	//TODO 0215 로그인 action 확인할 것
	// 로그인 - Logic
	/*
	@RequestMapping("/loginAction")
	public String loginAction(HttpServletRequest request, Model model) {
		// 정보 조회
		member_dao.login(
				request.getParameter("Member_Id"),
				request.getParameter("Member_Pw")
		);
		
		MemberResponse member = memberService.login(loginId, password);
		
		System.out.println("id:"+request.getParameter("Member_Id"));
		System.out.println("pw:"+request.getParameter("Member_Pw"));
		
		// 성공 시	
		if(member_dao.login != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", login);
            session.setMaxInactiveInterval(60 * 30);

            return "redirect:login";
		}
		
		System.out.println("로긴 성공");
		
		// 실패 시 -> Home 으로 돌아가기
		return "thymeleaf/home/home";
	}
	*/
	// 로그인 - Logic
	/*
	@RequestMapping("/loginAction")
	public String loginAction(HttpServletRequest request, Model model, MemberDTO dto) {
	    // 회원 정보 조회
	    String memberId = request.getParameter("Member_Id");
	    String memberPw = request.getParameter("Member_Pw");
	    System.out.println(dto);
	    System.out.println("id:" + memberId);
	    System.out.println("pw:" + memberPw);
	    
	    // 로그인 성공 시
	    if (dto != null) {
	        HttpSession session = request.getSession();
	        session.setAttribute("loginMember", dto);
	        session.setMaxInactiveInterval(60 * 30);

	        return "redirect:/home"; // 로그인 성공 후 이동할 페이지
	    } else {
	        System.out.println("로그인 실패");

	        return "redirect:/login";  // 로그인 실패 시 다시 로그인 페이지로 이동
	    }
	}
	*/
	@RequestMapping("/loginAction")
	public String loginAction(HttpServletRequest request, Model model, MemberDTO dto) {
		
		// 회원 정보 조회
		String memberId = request.getParameter("Member_Id");
		String memberPw = request.getParameter("Member_Pw");

		System.out.println("DAO 전 --------");
		System.out.println("id:" + memberId);
		System.out.println("pw:" + memberPw);

		MemberDTO memberdto = new MemberDTO();
		
		// 로그인 실패 시
		if (member_dao.login(memberId, memberPw) == null) {
			System.out.println("로그인 실패");
			
			return "redirect:/login?msg=1";  // 로그인 실패 시 다시 로그인 페이지로 이동
		} else {
			memberdto = member_dao.login(memberId, memberPw);
			
			// session 저장
			HttpSession session = request.getSession();
			session.setAttribute("loginMember_id", memberId);
			session.setMaxInactiveInterval(60 * 30);
			
			
			model.addAttribute("loginMember", memberdto);
			
			return "redirect:/home"; // 로그인 성공 후 이동할 페이지
		}
	}

    // 로그아웃
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/login"; // -> 로그인 페이지로 돌아가기
    }

}
