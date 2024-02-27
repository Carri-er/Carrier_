package com.ex.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class CarrierController {
	@Autowired
	private com.ex.springboot.dao.IBandDAO bandDao; // 다형성
	
   @GetMapping("/")	
   public String main() {
      return "thymeleaf/home/home";
   }


   @GetMapping("/home")
   public String home(Model model) {
	   
	  model.addAttribute("randomBandList_home", bandDao.randomBandList_home());
	   
      return "thymeleaf/home/home2";
   }


   @GetMapping("/map")
   public String map() {
      return "thymeleaf/map/map_0221";
   }
   


}