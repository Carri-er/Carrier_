package com.ex.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping
public class AIController {
	public static String UPLOAD_EVENT_DIRECTORY = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\static\\img\\info";

	@Autowired
	private com.ex.springboot.dao.IAiDAO AiDAO; // 다형성

	@GetMapping("/aicc")
	public String aicc() {
		return "thymeleaf/aicc/aicc";
	}

	

	@PostMapping("/cours_Save")
	public String cours_Save(HttpServletRequest request, Model model) {
		//day1 파라미터 값
		String day1aicc = request.getParameter("day1aicc");
		String day1aiccFood = request.getParameter("day1aiccFood");
		String day1aicc2 = request.getParameter("day1aicc2");
		String day1aiccCafe = request.getParameter("day1aiccCafe");
		String day1aiccFood2 = request.getParameter("day1aiccFood2");
		String day1aiccTD = request.getParameter("day1aiccTDV");
		String day1hotel = request.getParameter("day1hotel");
		//day2 파라미터 값
		String day2aicc = request.getParameter("day2aicc");
		String day2aiccFood = request.getParameter("day2aiccFood");
		String day2aicc2 = request.getParameter("day2aicc2");
		String day2aiccCafe = request.getParameter("day2aiccCafe");
		String day2aiccFood2 = request.getParameter("day2aiccFood2");
		String day2aiccTD = request.getParameter("day2aiccTDV");
		String day2hotel = request.getParameter("day2hotel");
		//day3 파라미터 값
		String day3aicc = request.getParameter("day3aicc");
		String day3aiccFood = request.getParameter("day3aiccFood");
		String day3aicc2 = request.getParameter("day3aicc2");
		String day3aiccCafe = request.getParameter("day3aiccCafe");
		String day3aiccFood2 = request.getParameter("day3aiccFood2");
		String day3aiccTD = request.getParameter("day3aiccTDV");
		
	
		String memberId = request.getParameter("memberId");
		String day = request.getParameter("day");
		String amount = request.getParameter("amount");
		String discount = request.getParameter("discount");
		String realAmount = request.getParameter("realAmount");
		model.addAttribute("amount", amount);
		model.addAttribute("realAmount", realAmount);
		model.addAttribute("discount", discount);
		
		Integer courseNum = AiDAO.getCourseNum();
		model.addAttribute("courseNum", courseNum+1);
		
		model.addAttribute("aicc", AiDAO.listCourse(day1aicc));
		model.addAttribute("aiccFood", AiDAO.listCourse(day1aiccFood));
		model.addAttribute("aicc2", AiDAO.listCourse(day1aicc2));
		model.addAttribute("aiccFood2", AiDAO.listCourse(day1aiccCafe));
		model.addAttribute("aiccCafe", AiDAO.listCourse(day1aiccFood2));
		if(day.equals("1")) {
			double d1 = Double.parseDouble(day1aiccTD);
			double d2 = Double.parseDouble(day2aiccTD);
			
			double total = d1 + d2;
			
			String td = String.format("%.2f", total);
			model.addAttribute("totalDistance", td);
			//1일차 보내기
			model.addAttribute("aicc", AiDAO.listCourse(day1aicc));
			model.addAttribute("aiccFood", AiDAO.listCourse(day1aiccFood));
			model.addAttribute("aicc2", AiDAO.listCourse(day1aicc2));
			model.addAttribute("aiccFood2", AiDAO.listCourse(day1aiccCafe));
			model.addAttribute("aiccCafe", AiDAO.listCourse(day1aiccFood2));
			model.addAttribute("hotel", AiDAO.listCourse(day1hotel));
			// 2일차 보내기
			model.addAttribute("aicc3", AiDAO.listCourse(day2aicc));
			model.addAttribute("aiccFood3", AiDAO.listCourse(day2aiccFood));
			model.addAttribute("aicc4", AiDAO.listCourse(day2aicc2));
			model.addAttribute("aiccFood4", AiDAO.listCourse(day2aiccCafe));
			model.addAttribute("aiccCafe2", AiDAO.listCourse(day2aiccFood2));
			model.addAttribute("memberId", memberId);
			model.addAttribute("day", day);
			model.addAttribute("amount", amount);
			model.addAttribute("realAmount", realAmount);
			model.addAttribute("discount", discount);
			model.addAttribute("courseNum", courseNum+1);
			
			return "thymeleaf/aicc/saveCourseday2";
		}
		if(day.equals("2")) {
			double d1 = Double.parseDouble(day1aiccTD);
			double d2 = Double.parseDouble(day2aiccTD);
			double d3 = Double.parseDouble(day3aiccTD);
			double total = d1 + d2 + d3;
			
			String td = String.format("%.2f", total);
			model.addAttribute("totalDistance", td);
			//1일차 보내기
			model.addAttribute("aicc", AiDAO.listCourse(day1aicc));
			model.addAttribute("aiccFood", AiDAO.listCourse(day1aiccFood));
			model.addAttribute("aicc2", AiDAO.listCourse(day1aicc2));
			model.addAttribute("aiccFood2", AiDAO.listCourse(day1aiccCafe));
			model.addAttribute("aiccCafe", AiDAO.listCourse(day1aiccFood2));
			model.addAttribute("hotel", AiDAO.listCourse(day1hotel));
			// 2일차 보내기
			model.addAttribute("aicc3", AiDAO.listCourse(day2aicc));
			model.addAttribute("aiccFood3", AiDAO.listCourse(day2aiccFood));
			model.addAttribute("aicc4", AiDAO.listCourse(day2aicc2));
			model.addAttribute("aiccFood4", AiDAO.listCourse(day2aiccCafe));
			model.addAttribute("aiccCafe2", AiDAO.listCourse(day2aiccFood2));
			model.addAttribute("hotel2", AiDAO.listCourse(day2hotel));
			// 3일차 보내기
			model.addAttribute("aicc5", AiDAO.listCourse(day3aicc));
			model.addAttribute("aiccFood5", AiDAO.listCourse(day3aiccFood));
			model.addAttribute("aicc6", AiDAO.listCourse(day3aicc2));
			model.addAttribute("aiccFood6", AiDAO.listCourse(day3aiccCafe));
			model.addAttribute("aiccCafe3", AiDAO.listCourse(day3aiccFood2));
			model.addAttribute("memberId", memberId);
			model.addAttribute("day", day);
			model.addAttribute("amount", amount);
			model.addAttribute("realAmount", realAmount);
			model.addAttribute("discount", discount);
			model.addAttribute("courseNum", courseNum+1);
			
			return "thymeleaf/aicc/saveCourseday3";
		}
	
		
		model.addAttribute("totalDistance", day1aiccTD);
		model.addAttribute("memberId", memberId);
		model.addAttribute("day", day);
		return "thymeleaf/aicc/saveCourse";
	}
	@GetMapping("/Course_view")
	public String Course_view(HttpServletRequest request, Model model) {
		
		String num = request.getParameter("num");
		model.addAttribute("list",AiDAO.Course_view_list(num));
		String number = AiDAO.Course_view_list(num).get(0).getCourse_number();
		System.out.println("number"+number);
		String day =  AiDAO.Course_view_list(num).get(0).getCourse_Interest();
		String[] values = number.split(",");
		model.addAttribute("aicc", AiDAO.listCourse(values[0]));
		model.addAttribute("aiccFood", AiDAO.listCourse(values[1]));
		model.addAttribute("aicc2", AiDAO.listCourse(values[2]));
		model.addAttribute("aiccFood2", AiDAO.listCourse(values[3]));
		model.addAttribute("aiccCafe", AiDAO.listCourse(values[4]));
		
		if(day.equals("1박2일")) {
			model.addAttribute("aicc", AiDAO.listCourse(values[0]));
			model.addAttribute("aiccFood", AiDAO.listCourse(values[1]));
			model.addAttribute("aicc2", AiDAO.listCourse(values[2]));
			model.addAttribute("aiccFood2", AiDAO.listCourse(values[3]));
			model.addAttribute("aiccCafe", AiDAO.listCourse(values[4]));
			model.addAttribute("hotel", AiDAO.listCourse(values[5]));
			
			model.addAttribute("aicc3", AiDAO.listCourse(values[6]));
			model.addAttribute("aiccFood3", AiDAO.listCourse(values[7]));
			model.addAttribute("aicc4", AiDAO.listCourse(values[8]));
			model.addAttribute("aiccFood4", AiDAO.listCourse(values[9]));
			model.addAttribute("aiccCafe2", AiDAO.listCourse(values[10]));
			
			
			
			model.addAttribute("totalDistance", AiDAO.Course_view_list(num).get(0).getCourse_distance());
			model.addAttribute("memberId", AiDAO.Course_view_list(num).get(0).getMember_Id());
			
			return "thymeleaf/aicc/Course_viewDay2";
		}
		if(day.equals("2박3일")) {
			model.addAttribute("aicc", AiDAO.listCourse(values[0]));
			model.addAttribute("aiccFood", AiDAO.listCourse(values[1]));
			model.addAttribute("aicc2", AiDAO.listCourse(values[2]));
			model.addAttribute("aiccFood2", AiDAO.listCourse(values[3]));
			model.addAttribute("aiccCafe", AiDAO.listCourse(values[4]));
			model.addAttribute("hotel", AiDAO.listCourse(values[5]));
			
			model.addAttribute("aicc3", AiDAO.listCourse(values[6]));
			model.addAttribute("aiccFood3", AiDAO.listCourse(values[7]));
			model.addAttribute("aicc4", AiDAO.listCourse(values[8]));
			model.addAttribute("aiccFood4", AiDAO.listCourse(values[9]));
			model.addAttribute("aiccCafe2", AiDAO.listCourse(values[10]));
			model.addAttribute("hotel2", AiDAO.listCourse(values[11]));
			// 3일차 보내기
			model.addAttribute("aicc5", AiDAO.listCourse(values[12]));
			model.addAttribute("aiccFood5", AiDAO.listCourse(values[13]));
			model.addAttribute("aicc6", AiDAO.listCourse(values[14]));
			model.addAttribute("aiccFood6", AiDAO.listCourse(values[15]));
			model.addAttribute("aiccCafe3", AiDAO.listCourse(values[16]));
			
			
			model.addAttribute("totalDistance", AiDAO.Course_view_list(num).get(0).getCourse_distance());
			model.addAttribute("memberId", AiDAO.Course_view_list(num).get(0).getMember_Id());
			
			return "thymeleaf/aicc/Course_viewDay3";
		}
		model.addAttribute("totalDistance", AiDAO.Course_view_list(num).get(0).getCourse_distance());
		model.addAttribute("memberId", AiDAO.Course_view_list(num).get(0).getMember_Id());
		
		return "thymeleaf/aicc/Course_view";
	}
	@GetMapping("/Course_delete")
	public String Course_delete(HttpServletRequest request, Model model) {
		
		String num = request.getParameter("num");
		String id = request.getParameter("id");
		model.addAttribute("list",AiDAO.Course_delete(num));
		
		String msg = "1";
	    if (msg != null && msg.equals("1")) {
	        model.addAttribute("confirmMessage", "코스 삭제가 완료되었습니다.");
	    }
		
		return "redirect:/mypage?id="+id;
	}
	@PostMapping("/cours_Save_insert")
	public String cours_Save_insert(HttpServletRequest request, Model model) {
		String day = request.getParameter("day");
		String amount = request.getParameter("discount");

		
		// day1 파라미터 값
		String day1aicc = request.getParameter("day1aicc");
		String day1aiccFood = request.getParameter("day1aiccFood");
		String day1aicc2 = request.getParameter("day1aicc2");
		String day1aiccCafe = request.getParameter("day1aiccCafe");
		String day1aiccFood2 = request.getParameter("day1aiccFood2");
		String day1hotel = request.getParameter("day1hotel");
		
		// day2 파라미터 값
		String day2aicc = request.getParameter("day2aicc");
		String day2aiccFood = request.getParameter("day2aiccFood");
		String day2aicc2 = request.getParameter("day2aicc2");
		String day2aiccCafe = request.getParameter("day2aiccCafe");
		String day2aiccFood2 = request.getParameter("day2aiccFood2");
		String day2hotel = request.getParameter("day2hotel");
		
		// day3 파라미터 값
		String day3aicc = request.getParameter("day3aicc");
		String day3aiccFood = request.getParameter("day3aiccFood");
		String day3aicc2 = request.getParameter("day3aicc2");
		String day3aiccCafe = request.getParameter("day3aiccCafe");
		String day3aiccFood2 = request.getParameter("day3aiccFood2");
		String number="";
		
		String memberId = request.getParameter("memberId");
		String img = request.getParameter("img");
		if (day==null || day.equals("") || day.equals("0")) {
			day="당일 치기";
		}else if(day.equals("1")) {
			day="1박2일";
		}else if(day.equals("2")) {
			day="2박3일";
		}
		System.out.println("day : 가 바로"+day);
		String Course_name = request.getParameter("Course_name");
		String Course_thema = request.getParameter("Course_thema");
		String Course_Area = request.getParameter("Course_Area");
		String Course_content = request.getParameter("Course_content");
		String Course_distance = request.getParameter("Course_distance");
		

		if(day.equals("2박3일")) {
		    number = day1aicc+","+day1aiccFood+","+day1aicc2+","+day1aiccCafe+","+day1aiccFood2+","+day1hotel+","+
		             day2aicc+","+day2aiccFood+","+day2aicc2+","+day2aiccCafe+","+day2aiccFood2+","+day2hotel+","+
		             day3aicc+","+day3aiccFood+","+day3aicc2+","+day3aiccCafe+","+day3aiccFood2;
		   AiDAO.save_course_insert(memberId,Course_name,Course_thema,Course_Area,Course_content,Course_distance,day,number,img,amount);
		} else if(day.equals("1박2일")) {
		    number = day1aicc+","+day1aiccFood+","+day1aicc2+","+day1aiccCafe+","+day1aiccFood2+","+day1hotel+","+
		             day2aicc+","+day2aiccFood+","+day2aicc2+","+day2aiccCafe+","+day2aiccFood2;
		    AiDAO.save_course_insert(memberId,Course_name,Course_thema,Course_Area,Course_content,Course_distance,day,number,img,amount);
		} else if(day.equals("당일 치기")) {
		    number = day1aicc+","+day1aiccFood+","+day1aicc2+","+day1aiccCafe+","+day1aiccFood2;
		    AiDAO.save_course_insert(memberId,Course_name,Course_thema,Course_Area,Course_content,Course_distance,day,number,img,amount);
		    System.out.println("실행 순서 확인1");
		}

		
		// 결제를 위한 코스 번호 불러오기
		Integer courseNum = AiDAO.getCourseNum();
		model.addAttribute("courseNum", courseNum);
		 System.out.println("실행 순서 확인2 courseNum: "+courseNum);

		// 결제를 위한 금액 불러오기
		model.addAttribute("amount", amount);
		model.addAttribute("memberId", memberId);
		
		String msg = "1";
	    if (msg != null && msg.equals("1")) {
	        model.addAttribute("confirmMessage", "코스를 확인하러 마이페이지로 이동하시겠습니까?");
	    }
		
		return "thymeleaf/aicc/aicc";
	}
	
	
	
	@PostMapping("/cours_Save_update")
	public String cours_Save_update(HttpServletRequest request, Model model) {
		
		String num = request.getParameter("num");
		model.addAttribute("list",AiDAO.Course_view_list(num));
		String number = AiDAO.Course_view_list(num).get(0).getCourse_number();
		String day = AiDAO.Course_view_list(num).get(0).getCourse_Interest();
		System.out.println("number"+number);
		String[] values = number.split(",");
		if(day.equals("2박3일")) {
			model.addAttribute("aicc", AiDAO.listCourse(values[0]));
			model.addAttribute("aiccFood", AiDAO.listCourse(values[1]));
			model.addAttribute("aicc2", AiDAO.listCourse(values[2]));
			model.addAttribute("aiccFood2", AiDAO.listCourse(values[3]));
			model.addAttribute("aiccCafe", AiDAO.listCourse(values[4]));
	model.addAttribute("hotel", AiDAO.listCourse(values[5]));
			
			model.addAttribute("aicc3", AiDAO.listCourse(values[6]));
			model.addAttribute("aiccFood3", AiDAO.listCourse(values[7]));
			model.addAttribute("aicc4", AiDAO.listCourse(values[8]));
			model.addAttribute("aiccFood4", AiDAO.listCourse(values[9]));
			model.addAttribute("aiccCafe2", AiDAO.listCourse(values[10]));
			model.addAttribute("hotel2", AiDAO.listCourse(values[11]));
			// 3일차 보내기
			model.addAttribute("aicc5", AiDAO.listCourse(values[12]));
			model.addAttribute("aiccFood5", AiDAO.listCourse(values[13]));
			model.addAttribute("aicc6", AiDAO.listCourse(values[14]));
			model.addAttribute("aiccFood6", AiDAO.listCourse(values[15]));
			model.addAttribute("aiccCafe3", AiDAO.listCourse(values[16]));
			
			model.addAttribute("totalDistance", AiDAO.Course_view_list(num).get(0).getCourse_distance());
			model.addAttribute("memberId", AiDAO.Course_view_list(num).get(0).getMember_Id());
			return "thymeleaf/aicc/Course_updateDay3";
		}
		if(day.equals("1박2일")) {
			model.addAttribute("aicc", AiDAO.listCourse(values[0]));
			model.addAttribute("aiccFood", AiDAO.listCourse(values[1]));
			model.addAttribute("aicc2", AiDAO.listCourse(values[2]));
			model.addAttribute("aiccFood2", AiDAO.listCourse(values[3]));
			model.addAttribute("aiccCafe", AiDAO.listCourse(values[4]));
			model.addAttribute("hotel", AiDAO.listCourse(values[5]));
			
			model.addAttribute("aicc3", AiDAO.listCourse(values[6]));
			model.addAttribute("aiccFood3", AiDAO.listCourse(values[7]));
			model.addAttribute("aicc4", AiDAO.listCourse(values[8]));
			model.addAttribute("aiccFood4", AiDAO.listCourse(values[9]));
			model.addAttribute("aiccCafe2", AiDAO.listCourse(values[10]));
			
			
			model.addAttribute("totalDistance", AiDAO.Course_view_list(num).get(0).getCourse_distance());
			model.addAttribute("memberId", AiDAO.Course_view_list(num).get(0).getMember_Id());
			return "thymeleaf/aicc/Course_updateDay2";
		}
		model.addAttribute("aicc", AiDAO.listCourse(values[0]));
		model.addAttribute("aiccFood", AiDAO.listCourse(values[1]));
		model.addAttribute("aicc2", AiDAO.listCourse(values[2]));
		model.addAttribute("aiccFood2", AiDAO.listCourse(values[3]));
		model.addAttribute("aiccCafe", AiDAO.listCourse(values[4]));
		model.addAttribute("totalDistance", AiDAO.Course_view_list(num).get(0).getCourse_distance());
		model.addAttribute("memberId", AiDAO.Course_view_list(num).get(0).getMember_Id());
		
		return "thymeleaf/aicc/Course_update";
	}
	@PostMapping("/cours_Save_update_result")
	public String cours_Save_update_result(HttpServletRequest request, Model model) {
		
		String num = request.getParameter("num");
		String Course_name = request.getParameter("Course_name");
		String Course_content = request.getParameter("Course_content");
		
		model.addAttribute("update",AiDAO.Course_update(Course_name,Course_content,num));
		model.addAttribute("list",AiDAO.Course_view_list(num));
		
		String number = AiDAO.Course_view_list(num).get(0).getCourse_number();
		String day = AiDAO.Course_view_list(num).get(0).getCourse_Interest();
		
		System.out.println("number"+number);
		String[] values = number.split(",");
		if(day.equals("2박3일")) {
			model.addAttribute("aicc", AiDAO.listCourse(values[0]));
			model.addAttribute("aiccFood", AiDAO.listCourse(values[1]));
			model.addAttribute("aicc2", AiDAO.listCourse(values[2]));
			model.addAttribute("aiccFood2", AiDAO.listCourse(values[3]));
			model.addAttribute("aiccCafe", AiDAO.listCourse(values[4]));
			model.addAttribute("hotel", AiDAO.listCourse(values[5]));
			
			model.addAttribute("aicc3", AiDAO.listCourse(values[6]));
			model.addAttribute("aiccFood3", AiDAO.listCourse(values[7]));
			model.addAttribute("aicc4", AiDAO.listCourse(values[8]));
			model.addAttribute("aiccFood4", AiDAO.listCourse(values[9]));
			model.addAttribute("aiccCafe2", AiDAO.listCourse(values[10]));
			model.addAttribute("hotel2", AiDAO.listCourse(values[11]));
			// 3일차 보내기
			model.addAttribute("aicc5", AiDAO.listCourse(values[12]));
			model.addAttribute("aiccFood5", AiDAO.listCourse(values[13]));
			model.addAttribute("aicc6", AiDAO.listCourse(values[14]));
			model.addAttribute("aiccFood6", AiDAO.listCourse(values[15]));
			model.addAttribute("aiccCafe3", AiDAO.listCourse(values[16]));
			model.addAttribute("totalDistance", AiDAO.Course_view_list(num).get(0).getCourse_distance());
			model.addAttribute("memberId", AiDAO.Course_view_list(num).get(0).getMember_Id());
			
			return "redirect:/Course_view?num="+num;
		}
		if(day.equals("1박2일")) {
			model.addAttribute("aicc", AiDAO.listCourse(values[0]));
			model.addAttribute("aiccFood", AiDAO.listCourse(values[1]));
			model.addAttribute("aicc2", AiDAO.listCourse(values[2]));
			model.addAttribute("aiccFood2", AiDAO.listCourse(values[3]));
			model.addAttribute("aiccCafe", AiDAO.listCourse(values[4]));
			model.addAttribute("hotel", AiDAO.listCourse(values[5]));
			
			model.addAttribute("aicc3", AiDAO.listCourse(values[6]));
			model.addAttribute("aiccFood3", AiDAO.listCourse(values[7]));
			model.addAttribute("aicc4", AiDAO.listCourse(values[8]));
			model.addAttribute("aiccFood4", AiDAO.listCourse(values[9]));
			model.addAttribute("aiccCafe2", AiDAO.listCourse(values[10]));
			
			model.addAttribute("totalDistance", AiDAO.Course_view_list(num).get(0).getCourse_distance());
			model.addAttribute("memberId", AiDAO.Course_view_list(num).get(0).getMember_Id());
			
			return "redirect:/Course_view?num="+num;
		}
		model.addAttribute("aicc", AiDAO.listCourse(values[0]));
		model.addAttribute("aiccFood", AiDAO.listCourse(values[1]));
		model.addAttribute("aicc2", AiDAO.listCourse(values[2]));
		model.addAttribute("aiccFood2", AiDAO.listCourse(values[3]));
		model.addAttribute("aiccCafe", AiDAO.listCourse(values[4]));
		model.addAttribute("totalDistance", AiDAO.Course_view_list(num).get(0).getCourse_distance());
		model.addAttribute("memberId", AiDAO.Course_view_list(num).get(0).getMember_Id());
		
		return "redirect:/Course_view?num="+num;
	}
	

}