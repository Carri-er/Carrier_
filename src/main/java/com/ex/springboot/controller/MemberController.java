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

import com.ex.springboot.dao.IFeedDAO;
import com.ex.springboot.dao.IMemberDAO;
import com.ex.springboot.dto.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping
public class MemberController {
	public static String UPLOAD_MEMBER_DIRECTORY = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\static\\img\\member_thumbnail";

	@Autowired
	IMemberDAO member_dao;

	@Autowired
	IFeedDAO feed_dao;
	
	@Autowired
	private com.ex.springboot.dao.IAiDAO AiDAO;
	// 회원가입 - Page
	@GetMapping("/addMember")
	public String addMember() {
		return "thymeleaf/Member/addMember";
	}

	// 회원가입 - Logic
	@PostMapping("/signup")
	public String signup(HttpServletRequest request, Model model,
			@RequestParam("Member_profileimage") MultipartFile file) {

		String Member_Email = request.getParameter("mail1") + "@" + request.getParameter("mail2");
		int Member_Age = Integer.parseInt(request.getParameter("Member_Age"));
		String Member_Phone = request.getParameter("phone1") + "-" + request.getParameter("phone2") + "-"
				+ request.getParameter("phone3");

		// 사용자가 파일을 안 넣었을 때
		if (file.isEmpty()) {
			String Member_profileimage = "user.png";

			member_dao.addMember(request.getParameter("Member_Name"), Member_Age, request.getParameter("Member_Id"),
					Member_Email, Member_Phone, request.getParameter("Member_Pw"), request.getParameter("Member_Area"),
					request.getParameter("Member_Thema"), Member_profileimage);
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

					member_dao.addMember(request.getParameter("Member_Name"), Member_Age,
							request.getParameter("Member_Id"), Member_Email, Member_Phone,
							request.getParameter("Member_Pw"), request.getParameter("Member_Area"),
							request.getParameter("Member_Thema"), fileNames.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println(" :: 회원가입 완 :: ");

		}
		return "redirect:/login?msg=1";
	}

	// 회원가입 - id 중복검사 Logic
	@RequestMapping("/idCheck")
	@ResponseBody // JSON 형태의 응답을 반환하기 위해 사용합니다.
	public String idCheck(HttpServletRequest request) {
		String id = request.getParameter("id");
		System.out.println("id: " + id);
		System.out.println("member_dao : " + member_dao.memberList(id));
		if (member_dao.memberList(id) == null) {
			return "success";
		} else {
			return "fail";
		}
	}

	// 회원 수정 - Page
	@RequestMapping("/editMember")
	public String editMember(@ModelAttribute("memberData") MemberDTO dto, HttpServletRequest request, Model model) {
		String Member_Id = request.getParameter("id");

		model.addAttribute("MemberList", member_dao.memberList(Member_Id));

		// 데이터 가공 [이메일/번호]
		dto = member_dao.memberList(Member_Id);

		String Member_Email = dto.getMember_Email();
		String Member_Phone = dto.getMember_Phone();

		String[] email = Member_Email.split("@");
		model.addAttribute("mail1", email[0]);
		model.addAttribute("mail2", email[1]);

		String[] phone = Member_Phone.split("-");
		model.addAttribute("phone1", phone[0]);
		model.addAttribute("phone2", phone[1]);
		model.addAttribute("phone3", phone[2]);

		return "thymeleaf/Member/editMember";
	}

	// 회원 수정 - Logic
	@PostMapping("/editAction")
	public String editAction(HttpServletRequest request, Model model,
			@RequestParam("Member_profileimage") MultipartFile file) {

		String Member_Email = request.getParameter("mail1") + "@" + request.getParameter("mail2");
		int Member_Age = Integer.parseInt(request.getParameter("Member_Age"));
		String Member_Phone = request.getParameter("phone1") + "-" + request.getParameter("phone2") + "-"
				+ request.getParameter("phone3");
		String Member_profile = null;

		try {
			StringBuilder fileNames = new StringBuilder();
			if (!file.isEmpty()) {
				Path fileNameAndPath = Paths.get(UPLOAD_MEMBER_DIRECTORY, file.getOriginalFilename());
				// 설정한 디렉토리에 파일 업로드
				fileNames.append(file.getOriginalFilename());
				byte[] fileSize = file.getBytes(); // 이미지에 대한 정보 값을 바이트 배열로 가져온다.
				Files.write(fileNameAndPath, fileSize);

				Member_profile = fileNames.toString();
			} else {
				Member_profile = request.getParameter("Member_profileimage_old");
			}

			member_dao.editMember(
					request.getParameter("Member_Name"), 
					Member_Age, 
					Member_Email, 
					Member_Phone,
					request.getParameter("Member_Pw"), 
					request.getParameter("Member_Area"),
					request.getParameter("Member_Thema"), 
					Member_profile, 
					request.getParameter("Member_Id")
			);

			// 2. 세션에 회원 정보(아이디/이름/프로필사진) 저장 & 세션 유지 시간 설정
			HttpSession session = request.getSession();
			session.setAttribute("Member_Name", request.getParameter("Member_Name"));
			session.setAttribute("Member_profileimage", Member_profile);
			session.setMaxInactiveInterval(60 * 30);

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("dao: " + member_dao);

		System.out.println(" :: 회원수정 완 :: ");

		return "redirect:/mypage?id=" + request.getParameter("Member_Id");
	}

	// 회원 탈퇴 및 삭제
	@GetMapping("/delMember")
	public String delMember(HttpServletRequest request, Model model, HttpSession session) {
		String Member_id;
		// 파라미터의 이름을 id 라고 들고온다면 admin 이 요청한 값 
		if(request.getParameterMap().containsKey("id")) {
			Member_id = request.getParameter("id");
			
			member_dao.delMember(Member_id);
			session.invalidate();
			
			System.out.println("회원삭제");
			
			try {
				String encodedMemberName = URLEncoder.encode(Member_id, "UTF-8");
				return "redirect:/MemberList?msg=1&name=" + encodedMemberName; // 로그인 성공 후 이동할 페이지
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		// 파라미터 이름을 memberId 로 들고오면 개인이 탈퇴 요청한 id 값
		Member_id = request.getParameter("memberId");

		member_dao.delMember(Member_id);
		session.invalidate();

		System.out.println("회원삭제");
		

		return "redirect:/login";
	}
	
	//마이페이지
	@GetMapping("/mypage")
	public String mypage(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		
		if(request.getParameter("id").equals("admin")) {
			return "redirect:/MemberList";
		}
		
		model.addAttribute("loginMember", member_dao.memberList(id));
		model.addAttribute("feed", feed_dao.feedList_login(id));
		model.addAttribute("course", AiDAO.Course_select4(id));
		System.out.println(AiDAO.Course_select4(id).size() + " --리스트 사이즈"); 
		return "thymeleaf/Member/mypage";
	}
	@GetMapping("/mypageCourseAll")
	public String mypageCourseAll(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		
		if(request.getParameter("id").equals("admin")) {
			return "redirect:/MemberList";
		}
		
		model.addAttribute("loginMember", member_dao.memberList(id));
		model.addAttribute("course", AiDAO.Course_select(id));
		model.addAttribute("feed", feed_dao.feedList_login(id));
		System.out.println(AiDAO.Course_select(id).size() + " --리스트 사이즈"); 
		return "thymeleaf/Member/mypage";
	}
	

	// 로그인 - Page
	@GetMapping("/login")
	public String login() {
		return "thymeleaf/Member/login";
	}

	// 로그인 - Logic
	@RequestMapping("/loginAction")
	public String loginAction(HttpServletRequest request, Model model, MemberDTO dto) {

		// 1. 회원 정보 조회
		String memberId = request.getParameter("Member_Id");
		String memberPw = request.getParameter("Member_Pw");

		dto = member_dao.login(memberId, memberPw);

		// 로그인 성공 시
		if (dto != null) {
			// 2. 세션에 회원 정보(아이디/이름/프로필사진) 저장 & 세션 유지 시간 설정
			HttpSession session = request.getSession();
			session.setAttribute("Member_Id", memberId);
			session.setAttribute("Member_Name", dto.getMember_Name());
			session.setAttribute("Member_profileimage", dto.getMember_profileimage());
			session.setMaxInactiveInterval(60 * 30);

			model.addAttribute("loginMember", dto);

			System.out.println("로그인 성공");
			try {
				String encodedMemberName = URLEncoder.encode(dto.getMember_Name(), "UTF-8");
				return "redirect:/home?msg=1&name=" + encodedMemberName; // 로그인 성공 후 이동할 페이지
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		System.out.println("로그인 실패");
		
		return "redirect:/login?msg=2"; // 로그인 실패 시 다시 로그인 페이지로 이동
	}

	// 로그아웃 - 로직
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		System.out.println("로그아웃");

		return "redirect:/login?msg=4"; // -> 로그인 페이지로 돌아가기
	}

	// 아이디 찾기 - 페이지
	@GetMapping("/FindId")
	public String FindId() {
		return "thymeleaf/Member/FindId";
	}
	
	// 아이디 찾기 - Logic
	@PostMapping("/FindIdAction")
	public String FindIdAction(MemberDTO dto, HttpServletRequest request, Model model) {
		String Member_Name = request.getParameter("Member_Name");
		String Member_Email = request.getParameter("Member_Email");
		
		dto = member_dao.Find_Id(Member_Name, Member_Email);
		
		if(dto != null) {
			model.addAttribute("MemberList", dto);
			
			System.out.println("아찾아디 : "+dto.getMember_Id());
			System.out.println("아찾이름 : "+dto.getMember_Name());
			return "thymeleaf/Member/FindId_Result";
		}
		
		return "redirect:/FindId?msg=1";
	}
	
	// 아이디 찾기 - 찾은 아이디 출력 페이지
	@GetMapping("/FindId_Result")
	public String FindId_Result() {
		return "thymeleaf/Member/FindId_Result";
	}
	
	// 비밀번호 찾기 - 페이지
	@GetMapping("/FindPw")
	public String FindPw() {
		return "thymeleaf/Member/FindPw";
	}
	
	// 비밀번호 찾기 - Logic
	@RequestMapping("/FindPwAction")
	public String Find_Pw(MemberDTO dto, HttpServletRequest request, Model model) {
		String Member_Id = request.getParameter("Member_Id");
		String Member_Name = request.getParameter("Member_Name");
		String Member_Email = request.getParameter("Member_Email");

		dto = member_dao.Find_Pw(Member_Id, Member_Name, Member_Email);
		
		if (dto != null) {
			model.addAttribute("MemberList", dto);
			
			try {
				String encodedMemberName = URLEncoder.encode(Member_Name, "UTF-8");
				return "redirect:/FindPw_Result?Member_Id=" + Member_Id + "&Member_Name=" + encodedMemberName;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return "redirect:/FindPw?msg=1";
	}
	
	// 비밀번호 찾기 - 비밀번호 재설정 페이지
	@GetMapping("/FindPw_Result")
	public String FindPw_Result() {
		return "thymeleaf/Member/FindPw_Result";
	}
	
	// 비밀번호 찾기 - 비밀번호 재설정 Logic
	@PostMapping("/FindPw_Result_Action")
	public String FindPw_Result_Action(MemberDTO dto, HttpServletRequest request, Model model) {
		String Member_Id = request.getParameter("Member_Id");
		String Member_Pw = request.getParameter("Member_Pw");
		
		member_dao.FindPw_Result_Action(Member_Id, Member_Pw);
		
		System.out.println("~~~~~~" + Member_Id + Member_Pw);

		return "redirect:/login?msg=3";
	}
	
	// [관리자] -- 회원 목록
	@RequestMapping("/MemberList")
	public String AllMemberList(MemberDTO dto, HttpServletRequest request, Model model) {

		model.addAttribute("MemberList", member_dao.AllMemberList());

		return "thymeleaf/Member/MemberList";
	}
}
