package com.ex.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class CarrierController {

	@GetMapping("/")
	public String main() {
		return "thymeleaf/home/home";
	}

	@GetMapping("/feed")
	public String feed() {
		return "thymeleaf/feed/feed2";
	}
	@GetMapping("/feedshow")
	public String feedshow() {
		return "thymeleaf/feed/feed_show";
	}


	@GetMapping("/home")
	public String home() {
		return "thymeleaf/home/home";
	}


	@GetMapping("/map")
	public String map() {
		return "thymeleaf/map/map";
	}



}