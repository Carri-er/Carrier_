package com.ex.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.springboot.dao.IFeedDAO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping
public class CarrierController {
	
	@Autowired
	IFeedDAO feed_dao;

   @GetMapping("/")
   public String main() {
      return "thymeleaf/home/home";
   }


   @GetMapping("/home")
   public String home(Model model, HttpServletRequest request) {
	   model.addAttribute("feedList", feed_dao.feedList());
      return "thymeleaf/home/home";
   }


   @GetMapping("/map")
   public String map() {
      return "thymeleaf/map/map_0221";
   }
   


}