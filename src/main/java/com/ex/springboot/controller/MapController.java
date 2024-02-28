package com.ex.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MapController {
	
	@Autowired
	private com.ex.springboot.dao.IMapDAO mapDao; 
	
	@GetMapping("/map")
	public String map(HttpServletRequest request, Model model) {
		// 페이징 
		String str_page = request.getParameter("page");
		 
		if(str_page == null || str_page.trim().equals(null) || str_page.isEmpty() ) {
			str_page = "1"; //페이지가 null 일 때 1로 설정
		}
		
		int num_page = Integer.parseInt(str_page);
		int pageItemCount = 15; // 한 페이지당 출력 될 게시글 수
		int startItemNum = (num_page-1) * pageItemCount; // 페이지의 시작 게시글 넘버
		int endItemNum = num_page * pageItemCount; // 페이지의 마지막 게시글 넘버
		int totalItemCount = mapDao.mapMarkerList().size(); // 총 건수 
		int prePage = num_page - 1; // 이전 페이지  
		int nextPage = num_page + 1;  // 다음 페이지 
		int endPage_num = (int) Math.ceil( (double)totalItemCount / pageItemCount ); // 총 게시글 수에서 표시되는 아이템 수를 나눠 마지막 페이지 번호 확인
		
		//페이지 마다 보일 Itemlist 
		model.addAttribute("paginItemList", mapDao.pageItemList(startItemNum, endItemNum));
		
		//페이징에 필요한 model
		model.addAttribute("currentPage", num_page);
		model.addAttribute("mPage", prePage);
	    model.addAttribute("pPage", nextPage);
	    model.addAttribute("totalPages", totalItemCount);
		
		//마커를 찍기위한 리스트 
		model.addAttribute("mapMarkerList", mapDao.mapMarkerList());

		return "thymeleaf/map/map_0221";
	}
}
