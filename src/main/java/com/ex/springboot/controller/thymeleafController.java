package com.ex.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class thymeleafController {
	@GetMapping("/layout")
	public String main() {
		return "layout/layout";
	}
	@GetMapping("/board")
	public String board() {
		return "thymeleaf/board";
	}
	@GetMapping("/feed")
	public String feed() {
		return "feed/feed";
	}
	@GetMapping("/inner")
	public String inner() {
		return "inner/inner";
	}
}