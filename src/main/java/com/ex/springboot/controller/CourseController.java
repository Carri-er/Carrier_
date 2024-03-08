package com.ex.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping
public class CourseController {
	
	@Autowired
	private com.ex.springboot.dao.IAiDAO AiDAO;
	
	@GetMapping("/aiccShow")
	public String aiccShow(HttpServletRequest request, Model model) {
		String area = request.getParameter("areaChk");
		String day = request.getParameter("day");
		day = day !=null? day:"0";
		String hotel ="숙박";
		String cafe = "카페";
		String food = "맛집";
		String thema = request.getParameter("thema");// 산
		String thema1 = request.getParameter("thema1");// 실내
		String thema2 = request.getParameter("thema2");// 엑티비티
		String thema3 = request.getParameter("thema3");// 테마파크
		String thema4 = request.getParameter("thema4");// 카페
		String thema5 = request.getParameter("thema5");// 바다
		String thema6 = request.getParameter("thema6");// 축제
		String thema7 = request.getParameter("thema7");// 맛집
		String thema8 = request.getParameter("thema8");// 전통시장
		
		model.addAttribute("day", day);
		thema = thema != null ? thema : " ";
		thema2 = thema2 != null ? thema2 : " ";
		thema3 = thema3 != null ? thema3 : " ";
		thema4 = thema4 != null ? thema4 : " ";
		thema5 = thema5 != null ? thema5 : " ";
		thema6 = thema6 != null ? thema6 : " ";
		thema7 = thema7 != null ? thema7 : " ";
		thema8 = thema8 != null ? thema8 : " ";
		thema1 = thema1 != null ? thema1 : " ";

		model.addAttribute("thema", thema);
		model.addAttribute("thema1", thema1);
		model.addAttribute("thema2", thema2);
		model.addAttribute("thema3", thema3);
		model.addAttribute("thema4", thema4);
		model.addAttribute("thema5", thema5);
		model.addAttribute("thema6", thema6);
		model.addAttribute("thema7", thema7);
		model.addAttribute("thema8", thema8);

		String area2 = "";
		AiDAO.firstlist(area);
		area2 = AiDAO.firstlist(area).get(0).getEvent_area2();
		
		
		System.out.println("====================");
		System.out.println("이번에 사용할 area2 = "+area2);
		
		if (AiDAO.list(area2, thema, thema2, thema3, thema5, thema6, thema8, thema1,food,hotel).isEmpty()) {
			System.out.println("====================");
			System.out.println("aicc isEmpty 에서 출력");
			System.out.println("area2 isEmpty에서 출력"+area2);
			model.addAttribute("aicc", AiDAO.listAll(area, cafe, food,hotel));
			model.addAttribute("aicc2", AiDAO.listAll(area, cafe, food,hotel));
		
		} else {
			System.out.println("====================");
			System.out.println("list의 area2 사용 -"+area2);
			model.addAttribute("aicc", AiDAO.list(area2, thema, thema1, thema2, thema3, thema5, thema6, thema8,food,hotel));
			model.addAttribute("aicc2", AiDAO.listAll(area, cafe, food,hotel));
		
		}

		if (AiDAO.listCafeArea2(area,area2, cafe).isEmpty()) {
			System.out.println("====================");
			System.out.println("aiccCafe2 isEmpty 에서 출력 :"+area2);
			
			model.addAttribute("aiccCafe2", AiDAO.listCafe(area, cafe));
		} else {
			model.addAttribute("aiccCafe", AiDAO.listCafeArea2(area,area2, cafe));
			
			System.out.println("====================");
			System.out.println("Cafe2에 사용되는 area2 : "+area2);
		}
		if (AiDAO.listFoodArea2(area,area2, food).isEmpty()) {
			System.out.println("====================");
			System.out.println("aiccFood isEmpty 에서 출력 : "+area2);
			model.addAttribute("aiccFood", AiDAO.listFood(area, food));
			model.addAttribute("aiccFood2", AiDAO.listFood(area, food));
			
			
		} else {
			model.addAttribute("aiccFood", AiDAO.listFoodArea2(area,area2, food));
			model.addAttribute("aiccFood2", AiDAO.listFoodArea2(area,area2, food));
			System.out.println("====================");
			System.out.println("Food에 사용되는 area2 : "+area2);
		}
		if(day.equals("0")) {
			System.out.println("비워둠");
		}
		///////////////////////////////////////////////////////////////////////////
		////// 1박 2일 선택시 ////////////
		if(day.equals("1")) {
			if (AiDAO.list(area2, thema, thema2, thema3, thema5, thema6, thema8, thema1,food,hotel).isEmpty()) {
				System.out.println("====================");
				System.out.println("aicc isEmpty 에서 출력");
				System.out.println("area2 isEmpty에서 출력"+area2);
				model.addAttribute("aicc", AiDAO.listAll(area, cafe, food,hotel));
				model.addAttribute("aicc2", AiDAO.listAll(area, cafe, food,hotel));
				model.addAttribute("aicc3", AiDAO.listAll(area, cafe, food,hotel));
				model.addAttribute("aicc4", AiDAO.listAll(area, cafe, food,hotel));
			} else {
				System.out.println("====================");
				System.out.println("list의 area2 사용 -"+area2);
				model.addAttribute("aicc", AiDAO.list(area2, thema, thema1, thema2, thema3, thema5, thema6, thema8,food,hotel));
				model.addAttribute("aicc2", AiDAO.listAll(area, cafe, food,hotel));
				//model.addAttribute("aicc3", AiDAO.list(area2, thema, thema1, thema2, thema3, thema5, thema6, thema8,food,hotel));
				//model.addAttribute("aicc4", AiDAO.list(area2, thema, thema1, thema2, thema3, thema5, thema6, thema8,food,hotel));
				model.addAttribute("aicc3", AiDAO.listAll(area, cafe, food,hotel));
				model.addAttribute("aicc4", AiDAO.listAll(area, cafe, food,hotel));
			}

			if (AiDAO.listCafeArea2(area,area2, cafe).isEmpty()) {
				System.out.println("====================");
				System.out.println("aiccCafe2 isEmpty 에서 출력 :"+area2);
				model.addAttribute("aiccCafe2", AiDAO.listCafe(area, cafe));
			} else {
				model.addAttribute("aiccCafe2", AiDAO.listCafeArea2(area,area2, cafe));
				System.out.println("====================");
				System.out.println("Cafe2에 사용되는 area2 : "+area2);
			}
			if (AiDAO.listFoodArea2(area,area2, food).isEmpty()) {
				System.out.println("====================");
				System.out.println("aiccFood3,4 isEmpty 에서 출력 : "+area2);
				model.addAttribute("aiccFood3", AiDAO.listFood(area, food));
				model.addAttribute("aiccFood4", AiDAO.listFood(area, food));
				
			} else {
				model.addAttribute("aiccFood3", AiDAO.listFoodArea2(area,area2, food));
				model.addAttribute("aiccFood4", AiDAO.listFoodArea2(area,area2, food));
				System.out.println("====================");
				System.out.println("Food3,4에 사용되는 area2 : "+area2);
			}
			
			
			model.addAttribute("hotel",AiDAO.listHotel(area, hotel));
			System.out.println("**********************************************");
			System.out.println("**********************************************");
			System.out.println("*****************검색 종료 day2***********************");
			System.out.println("**********************************************");
			System.out.println("**********************************************");
			
			return "thymeleaf/map/mapDay2";
		}
		if(day.equals("2")) {
			if (AiDAO.list(area2, thema, thema2, thema3, thema5, thema6, thema8, thema1,food,hotel).isEmpty()) {
				System.out.println("====================");
				System.out.println("aicc5,6 isEmpty 에서 출력");
				System.out.println("area5,6 isEmpty에서 출력"+area2);
				
				model.addAttribute("aicc5", AiDAO.listAll(area, cafe, food,hotel));
				model.addAttribute("aicc6", AiDAO.listAll(area, cafe, food,hotel));
				model.addAttribute("aicc3", AiDAO.listAll(area, cafe, food,hotel));
				model.addAttribute("aicc4", AiDAO.listAll(area, cafe, food,hotel));
			} else {
				System.out.println("====================");
				System.out.println("aicc5,6 의 area2 사용 -"+area2);
			
				model.addAttribute("aicc3", AiDAO.list(area2, thema, thema1, thema2, thema3, thema5, thema6, thema8,food,hotel));
				model.addAttribute("aicc4", AiDAO.list(area2, thema, thema1, thema2, thema3, thema5, thema6, thema8,food,hotel));
				model.addAttribute("aicc5", AiDAO.list(area2, thema, thema1, thema2, thema3, thema5, thema6, thema8,food,hotel));
				model.addAttribute("aicc6", AiDAO.list(area2, thema, thema1, thema2, thema3, thema5, thema6, thema8,food,hotel));
			}

			if (AiDAO.listCafeArea2(area,area2, cafe).isEmpty()) {
				System.out.println("====================");
				System.out.println("aiccCafe3 isEmpty 에서 출력 :"+area2);
				
				model.addAttribute("aiccCafe2", AiDAO.listCafe(area, cafe));
				model.addAttribute("aiccCafe3", AiDAO.listCafe(area, cafe));
			} else {
			
				model.addAttribute("aiccCafe2", AiDAO.listCafeArea2(area,area2, cafe));
				model.addAttribute("aiccCafe3", AiDAO.listCafeArea2(area,area2, cafe));
				System.out.println("====================");
				System.out.println("Cafe3에 사용되는 area2 : "+area2);
			}
			if (AiDAO.listFoodArea2(area,area2, food).isEmpty()) {
				System.out.println("====================");
				System.out.println("aiccFood5,6 isEmpty 에서 출력 : "+area2);
			
				model.addAttribute("aiccFood3", AiDAO.listFood(area, food));
				model.addAttribute("aiccFood4", AiDAO.listFood(area, food));
				model.addAttribute("aiccFood5", AiDAO.listFood(area, food));
				model.addAttribute("aiccFood6", AiDAO.listFood(area, food));
				
			} else {
				
				model.addAttribute("aiccFood3", AiDAO.listFoodArea2(area,area2, food));
				model.addAttribute("aiccFood4", AiDAO.listFoodArea2(area,area2, food));
				model.addAttribute("aiccFood5", AiDAO.listFoodArea2(area,area2, food));
				model.addAttribute("aiccFood6", AiDAO.listFoodArea2(area,area2, food));
				System.out.println("====================");
				System.out.println("Food5,6 에 사용되는 area2 : "+area2);
			}
			
			model.addAttribute("hotel",AiDAO.listHotel(area, hotel));
			model.addAttribute("hotel2",AiDAO.listHotel(area, hotel));
			System.out.println("**********************************************");
			System.out.println("**********************************************");
			System.out.println("*****************검색 종료 day3***********************");
			System.out.println("**********************************************");
			System.out.println("**********************************************");
			
			return "thymeleaf/map/mapDay3";
		}
		model.addAttribute("hotel",AiDAO.listHotel(area, hotel));
		System.out.println("**********************************************");
		System.out.println("**********************************************");
		System.out.println("*****************검색 종료 day1***********************");
		System.out.println("**********************************************");
		System.out.println("**********************************************");
		return "thymeleaf/map/map";
	}
	
	@GetMapping("/aiccShow2")
	public String aiccShow2(HttpServletRequest request, Model model) {
		String area = request.getParameter("areaChk");
		String day = request.getParameter("day");
		day = day !=null? day:"0";
		String hotel ="숙박";
		String cafe = "카페";
		String food = "맛집";
		String thema = request.getParameter("thema");// 산
		String thema1 = request.getParameter("thema1");// 실내
		String thema2 = request.getParameter("thema2");// 엑티비티
		String thema3 = request.getParameter("thema3");// 테마파크
		String thema4 = request.getParameter("thema4");// 카페
		String thema5 = request.getParameter("thema5");// 바다
		String thema6 = request.getParameter("thema6");// 축제
		String thema7 = request.getParameter("thema7");// 맛집
		String thema8 = request.getParameter("thema8");// 전통시장
		
		model.addAttribute("day", day);
		thema = thema != null ? thema : " ";
		thema2 = thema2 != null ? thema2 : " ";
		thema3 = thema3 != null ? thema3 : " ";
		thema4 = thema4 != null ? thema4 : " ";
		thema5 = thema5 != null ? thema5 : " ";
		thema6 = thema6 != null ? thema6 : " ";
		thema7 = thema7 != null ? thema7 : " ";
		thema8 = thema8 != null ? thema8 : " ";
		thema1 = thema1 != null ? thema1 : " ";

		model.addAttribute("thema", thema);
		model.addAttribute("thema1", thema1);
		model.addAttribute("thema2", thema2);
		model.addAttribute("thema3", thema3);
		model.addAttribute("thema4", thema4);
		model.addAttribute("thema5", thema5);
		model.addAttribute("thema6", thema6);
		model.addAttribute("thema7", thema7);
		model.addAttribute("thema8", thema8);

		String area2 = "";
		AiDAO.firstlist(area);
		area2 = AiDAO.firstlist(area).get(0).getEvent_area2();
		
		
		System.out.println("====================");
		System.out.println("이번에 사용할 area2 = "+area2);
		
		if (AiDAO.list(area2, thema, thema2, thema3, thema5, thema6, thema8, thema1,food,hotel).isEmpty()) {
			System.out.println("====================");
			System.out.println("aicc isEmpty 에서 출력");
			System.out.println("area2 isEmpty에서 출력"+area2);
			model.addAttribute("aicc", AiDAO.listAll(area, cafe, food,hotel));
			model.addAttribute("aicc2", AiDAO.listAll(area, cafe, food,hotel));
			
		} else {
			System.out.println("====================");
			System.out.println("list의 area2 사용 -"+area2);
			model.addAttribute("aicc", AiDAO.list(area2, thema, thema1, thema2, thema3, thema5, thema6, thema8,food,hotel));
			model.addAttribute("aicc2", AiDAO.listAll(area, cafe, food,hotel));
			
		}

		if (AiDAO.listCafeArea2(area,area2, cafe).isEmpty()) {
			System.out.println("====================");
			System.out.println("aiccCafe2 isEmpty 에서 출력 :"+area2);
			
			model.addAttribute("aiccCafe2", AiDAO.listCafe(area, cafe));
		} else {
			model.addAttribute("aiccCafe", AiDAO.listCafeArea2(area,area2, cafe));
			
			System.out.println("====================");
			System.out.println("Cafe2에 사용되는 area2 : "+area2);
		}
		if (AiDAO.listFoodArea2(area,area2, food).isEmpty()) {
			System.out.println("====================");
			System.out.println("aiccFood isEmpty 에서 출력 : "+area2);
			model.addAttribute("aiccFood", AiDAO.listFood(area, food));
			model.addAttribute("aiccFood2", AiDAO.listFood(area, food));
			
			
		} else {
			model.addAttribute("aiccFood", AiDAO.listFoodArea2(area,area2, food));
			model.addAttribute("aiccFood2", AiDAO.listFoodArea2(area,area2, food));
			System.out.println("====================");
			System.out.println("Food에 사용되는 area2 : "+area2);
		}
		if(day.equals("0")) {
			System.out.println("비워둠");
		}
		///////////////////////////////////////////////////////////////////////////
		////// 1박 2일 선택시 ////////////
		if(day.equals("1")) {
			if (AiDAO.list(area2, thema, thema2, thema3, thema5, thema6, thema8, thema1,food,hotel).isEmpty()) {
				System.out.println("====================");
				System.out.println("aicc isEmpty 에서 출력");
				System.out.println("area2 isEmpty에서 출력"+area2);
				model.addAttribute("aicc", AiDAO.listAll(area, cafe, food,hotel));
				model.addAttribute("aicc2", AiDAO.listAll(area, cafe, food,hotel));
				model.addAttribute("aicc3", AiDAO.listAll(area, cafe, food,hotel));
				model.addAttribute("aicc4", AiDAO.listAll(area, cafe, food,hotel));
			} else {
				System.out.println("====================");
				System.out.println("list의 area2 사용 -"+area2);
				model.addAttribute("aicc", AiDAO.list(area2, thema, thema1, thema2, thema3, thema5, thema6, thema8,food,hotel));
				model.addAttribute("aicc2", AiDAO.listAll(area, cafe, food,hotel));
				//model.addAttribute("aicc3", AiDAO.list(area2, thema, thema1, thema2, thema3, thema5, thema6, thema8,food,hotel));
				//model.addAttribute("aicc4", AiDAO.list(area2, thema, thema1, thema2, thema3, thema5, thema6, thema8,food,hotel));
				model.addAttribute("aicc3", AiDAO.listAll(area, cafe, food,hotel));
				model.addAttribute("aicc4", AiDAO.listAll(area, cafe, food,hotel));
			}

			if (AiDAO.listCafeArea2(area,area2, cafe).isEmpty()) {
				System.out.println("====================");
				System.out.println("aiccCafe2 isEmpty 에서 출력 :"+area2);
				model.addAttribute("aiccCafe2", AiDAO.listCafe(area, cafe));
			} else {
				model.addAttribute("aiccCafe2", AiDAO.listCafeArea2(area,area2, cafe));
				System.out.println("====================");
				System.out.println("Cafe2에 사용되는 area2 : "+area2);
			}
			if (AiDAO.listFoodArea2(area,area2, food).isEmpty()) {
				System.out.println("====================");
				System.out.println("aiccFood3,4 isEmpty 에서 출력 : "+area2);
				model.addAttribute("aiccFood3", AiDAO.listFood(area, food));
				model.addAttribute("aiccFood4", AiDAO.listFood(area, food));
				
			} else {
				model.addAttribute("aiccFood3", AiDAO.listFoodArea2(area,area2, food));
				model.addAttribute("aiccFood4", AiDAO.listFoodArea2(area,area2, food));
				System.out.println("====================");
				System.out.println("Food3,4에 사용되는 area2 : "+area2);
			}
			
			
			model.addAttribute("hotel",AiDAO.listHotel(area, hotel));
			System.out.println("**********************************************");
			System.out.println("**********************************************");
			System.out.println("*****************검색 종료 day2***********************");
			System.out.println("**********************************************");
			System.out.println("**********************************************");
			
			return "thymeleaf/map/mapDay2";
		}
		if(day.equals("2")) {
			if (AiDAO.list(area2, thema, thema2, thema3, thema5, thema6, thema8, thema1,food,hotel).isEmpty()) {
				System.out.println("====================");
				System.out.println("aicc5,6 isEmpty 에서 출력");
				System.out.println("area5,6 isEmpty에서 출력"+area2);
				
				model.addAttribute("aicc5", AiDAO.listAll(area, cafe, food,hotel));
				model.addAttribute("aicc6", AiDAO.listAll(area, cafe, food,hotel));
				model.addAttribute("aicc3", AiDAO.listAll(area, cafe, food,hotel));
				model.addAttribute("aicc4", AiDAO.listAll(area, cafe, food,hotel));
			} else {
				System.out.println("====================");
				System.out.println("aicc5,6 의 area2 사용 -"+area2);
			
				model.addAttribute("aicc3", AiDAO.list(area2, thema, thema1, thema2, thema3, thema5, thema6, thema8,food,hotel));
				model.addAttribute("aicc4", AiDAO.list(area2, thema, thema1, thema2, thema3, thema5, thema6, thema8,food,hotel));
				model.addAttribute("aicc5", AiDAO.list(area2, thema, thema1, thema2, thema3, thema5, thema6, thema8,food,hotel));
				model.addAttribute("aicc6", AiDAO.list(area2, thema, thema1, thema2, thema3, thema5, thema6, thema8,food,hotel));
			}

			if (AiDAO.listCafeArea2(area,area2, cafe).isEmpty()) {
				System.out.println("====================");
				System.out.println("aiccCafe3 isEmpty 에서 출력 :"+area2);
				
				model.addAttribute("aiccCafe2", AiDAO.listCafe(area, cafe));
				model.addAttribute("aiccCafe3", AiDAO.listCafe(area, cafe));
			} else {
			
				model.addAttribute("aiccCafe2", AiDAO.listCafeArea2(area,area2, cafe));
				model.addAttribute("aiccCafe3", AiDAO.listCafeArea2(area,area2, cafe));
				System.out.println("====================");
				System.out.println("Cafe3에 사용되는 area2 : "+area2);
			}
			if (AiDAO.listFoodArea2(area,area2, food).isEmpty()) {
				System.out.println("====================");
				System.out.println("aiccFood5,6 isEmpty 에서 출력 : "+area2);
			
				model.addAttribute("aiccFood3", AiDAO.listFood(area, food));
				model.addAttribute("aiccFood4", AiDAO.listFood(area, food));
				model.addAttribute("aiccFood5", AiDAO.listFood(area, food));
				model.addAttribute("aiccFood6", AiDAO.listFood(area, food));
				
			} else {
				
				model.addAttribute("aiccFood3", AiDAO.listFoodArea2(area,area2, food));
				model.addAttribute("aiccFood4", AiDAO.listFoodArea2(area,area2, food));
				model.addAttribute("aiccFood5", AiDAO.listFoodArea2(area,area2, food));
				model.addAttribute("aiccFood6", AiDAO.listFoodArea2(area,area2, food));
				System.out.println("====================");
				System.out.println("Food5,6 에 사용되는 area2 : "+area2);
			}
			
			model.addAttribute("hotel",AiDAO.listHotel(area, hotel));
			model.addAttribute("hotel2",AiDAO.listHotel(area, hotel));
			System.out.println("**********************************************");
			System.out.println("**********************************************");
			System.out.println("*****************검색 종료 day3***********************");
			System.out.println("**********************************************");
			System.out.println("**********************************************");
			
			return "thymeleaf/map/mapDay3";
		}
		model.addAttribute("hotel",AiDAO.listHotel(area, hotel));
		System.out.println("**********************************************");
		System.out.println("**********************************************");
		System.out.println("*****************검색 종료 day1***********************");
		System.out.println("**********************************************");
		System.out.println("**********************************************");
		return "thymeleaf/map/map_modal";
	}
}
