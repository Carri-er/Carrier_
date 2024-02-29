
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
   @GetMapping("/")   
   public String main() {
      return "thymeleaf/home/home";
   }
   
   @GetMapping("/checkout")   
   public String checkout() {
	   return "thymeleaf/member/checkout";
   }


   @GetMapping("/home")
   public String home(Model model) {
      List<CourseDTO> courseList = AiDAO.CourseListHome();
      
     model.addAttribute("randomBandList_home", bandDao.randomBandList_home());
     model.addAttribute("feedList", feed_dao.feedList_random());
     
     model.addAttribute("CourseListHome", courseList);
     model.addAttribute("infoHome", eventDAO.infoHome());
      
      return "thymeleaf/home/home";
   }


   
   

}