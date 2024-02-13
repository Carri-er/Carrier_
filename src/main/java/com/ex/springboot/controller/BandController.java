package com.ex.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class BandController {
	
	@GetMapping("/band")
	public String band() {
		return "thymeleaf/band/band";
	}
}
