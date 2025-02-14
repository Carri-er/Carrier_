
package com.ex.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.springboot.dao.IFeedDAO;
import com.ex.springboot.dto.CourseDTO;

@Controller
@RequestMapping
public class CarrierController {

   @Autowired
   private com.ex.springboot.dao.IBandDAO bandDao; 
   @Autowired
   IFeedDAO feed_dao;
   @Autowired
   private com.ex.springboot.dao.IAiDAO AiDAO; 
   @Autowired
   private com.ex.springboot.dao.IEventDAO eventDAO;

   @GetMapping("/home2")   
   public String main2(Model model) {
	   return "thymeleaf/home/home2";
   
   }
   @GetMapping("/home3")   
   public String home3(Model model) {
	   return "thymeleaf/home/home3";
   
   }
   @GetMapping("/home4")   
   public String home4(Model model) {
	   return "thymeleaf/home/home4";
   }

   @GetMapping("/")   
   public String main(Model model) {

      return "thymeleaf/home/home2";
   }
   
   @GetMapping("/home")
   public String home(Model model) {
	 //홈에 밴드 불러오는 코드
	     model.addAttribute("randomBandList_home", bandDao.randomBandList());
	   //홈에 피드 불러오는 코드
	     model.addAttribute("feedList", feed_dao.feedList_random());
	   //홈에 여행 정보 불러오는 코드
	     model.addAttribute("infoHome2", eventDAO.infoHome2());
	   //홈에 코스정보 불러오는 코드
	     List<CourseDTO> courseList = AiDAO.CourseListHome();
	     List<CourseDTO> courseList2 = AiDAO.CourseListHome2();
	     model.addAttribute("CourseListHome", courseList);
	     model.addAttribute("infoHome", courseList2);
	     int number = courseList2.get(0).getCourse_num();
	     String numm = String.valueOf(number);
	     int number2 = courseList2.get(1).getCourse_num();
	     String numm2 = String.valueOf(number2);
	     int number3 = courseList2.get(2).getCourse_num();
	     String numm3 = String.valueOf(number3);
	     int number4 = courseList2.get(3).getCourse_num();
	     String numm4 = String.valueOf(number4);
	     
	     System.out.println("number값 확인 : "+number);
	     System.out.println("number2값 확인 : "+number2);
	     System.out.println("number3값 확인 : "+number3);
	     String num = AiDAO.Course_view_list(numm).get(0).getCourse_number();
	     String[] values = num.split(",");
	     model.addAttribute("text1", AiDAO.listCourse(values[0]));
		 model.addAttribute("text2", AiDAO.listCourse(values[1]));
		 model.addAttribute("text3", AiDAO.listCourse(values[2]));
		 model.addAttribute("text4", AiDAO.listCourse(values[3]));
		 model.addAttribute("text5", AiDAO.listCourse(values[4]));
		 
		 String num2 = AiDAO.Course_view_list(numm2).get(0).getCourse_number();
	     String[] values2 = num2.split(",");
	     model.addAttribute("text11", AiDAO.listCourse(values2[0]));
		 model.addAttribute("text12", AiDAO.listCourse(values2[1]));
		 model.addAttribute("text13", AiDAO.listCourse(values2[2]));
		 model.addAttribute("text14", AiDAO.listCourse(values2[3]));
		 model.addAttribute("text15", AiDAO.listCourse(values2[4]));
		 
		 String num3 = AiDAO.Course_view_list(numm3).get(0).getCourse_number();
		 System.out.println("num3의 정체는 "+num3);
	     String[] values3 = num3.split(",");
	     model.addAttribute("text21", AiDAO.listCourse(values3[0]));
		 model.addAttribute("text22", AiDAO.listCourse(values3[1]));
		 model.addAttribute("text23", AiDAO.listCourse(values3[2]));
		 model.addAttribute("text24", AiDAO.listCourse(values3[3]));
		 model.addAttribute("text25", AiDAO.listCourse(values3[4]));
		 
		 String num4 = AiDAO.Course_view_list(numm4).get(0).getCourse_number();
		 System.out.println("num4의 정체는 "+num3);
	     String[] values4 = num4.split(",");
	     model.addAttribute("text31", AiDAO.listCourse(values4[0]));
		 model.addAttribute("text32", AiDAO.listCourse(values4[1]));
		 model.addAttribute("text33", AiDAO.listCourse(values4[2]));
		 model.addAttribute("text34", AiDAO.listCourse(values4[3]));
		 model.addAttribute("text35", AiDAO.listCourse(values4[4]));
      
      return "thymeleaf/home/home";
   }


   
   

}