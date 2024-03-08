
package com.ex.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.springboot.dao.IFeedDAO;
import com.ex.springboot.dto.CourseDTO;

import jakarta.servlet.http.HttpServletRequest;

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
   @GetMapping("/")   
   public String main(Model model) {
	   
	    List<CourseDTO> courseList = AiDAO.CourseListHome();
	    List<CourseDTO> courseList2 = AiDAO.CourseListHome2();
	      
	     model.addAttribute("randomBandList_home", bandDao.randomBandList_home());
	     model.addAttribute("feedList", feed_dao.feedList_random());
	     
	     model.addAttribute("CourseListHome", courseList);
	     model.addAttribute("infoHome", courseList2);
	     model.addAttribute("infoHome2", eventDAO.infoHome2());
      return "thymeleaf/home/home";
   }
   
	@RequestMapping("/checkout")
	public String checkout(HttpServletRequest request, Model model) {
		/* String MemberId = request.getParameter(); */

		return "thymeleaf/member/checkout";
	}
	
	@GetMapping("/fail")
	public String fail() {
		return "thymeleaf/member/fail";
	}
	
	@GetMapping("/success")
	public String success() {
		return "thymeleaf/member/success";
	}
   
   @GetMapping("/home")
   public String home(Model model) {
      List<CourseDTO> courseList = AiDAO.CourseListHome();
      
     model.addAttribute("randomBandList_home", bandDao.randomBandList_home());
     model.addAttribute("feedList", feed_dao.feedList_random());
     
     model.addAttribute("CourseListHome", courseList);
     model.addAttribute("infoHome", courseList);
     model.addAttribute("infoHome2", eventDAO.infoHome2());
      
      return "thymeleaf/home/home";
   }


   
   

}