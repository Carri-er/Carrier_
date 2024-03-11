package com.ex.springboot.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ex.springboot.dto.EventDTO;
import com.ex.springboot.dto.MapDistanceDTO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MapController {
	
	public static String UPLOAD_EVENT_DIRECTORY = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\static\\img\\info";

	@Autowired
	private com.ex.springboot.dao.IAiDAO AiDAO; // 다형성
	
	@Autowired
	private com.ex.springboot.dao.IMapDAO mapDao;

	@RequestMapping("/map")
	public String map(HttpServletRequest request, Model model) {
		// 페이징
		String str_page = request.getParameter("page");

		if (str_page == null || str_page.trim().equals(null) || str_page.isEmpty()) {
			str_page = "1"; // 페이지가 null 일 때 1로 설정
		}

		int num_page = Integer.parseInt(str_page);
		int pageItemCount = 15; // 한 페이지당 출력 될 게시글 수
		int startItemNum = (num_page - 1) * pageItemCount; // 페이지의 시작 게시글 넘버
		int endItemNum = num_page * pageItemCount; // 페이지의 마지막 게시글 넘버
		int totalItemCount = mapDao.mapMarkerAllList().size(); // 총 건수
		int prePage = num_page - 1; // 이전 페이지
		int nextPage = num_page + 1; // 다음 페이지
		int endPage_num = (int) Math.ceil((double) totalItemCount / pageItemCount); // 총 게시글 수에서 표시되는 아이템 수를 나눠 마지막 페이지
																					// 번호 확인

		// 페이지 마다 보일 Itemlist
		model.addAttribute("paginItemList", mapDao.pageItemList(startItemNum, endItemNum));

		// 페이징에 필요한 model
		model.addAttribute("currentPage", num_page);
		model.addAttribute("mPage", prePage);
		model.addAttribute("pPage", nextPage);
		model.addAttribute("totalPages", totalItemCount);
		
		
		String category = request.getParameter("category");
		String selectCategory = "";
		
		
		if( category == null ) {
			// 마커를 찍기위한 리스트
			model.addAttribute("mapMarkerList", mapDao.mapMarkerAllList());
			selectCategory = "all";
			model.addAttribute("selectCategory", selectCategory);
		} else if( category.equals("액티비티")) {
			model.addAttribute("mapMarkerList", mapDao.mapMarkerCategoryList("레포츠"));
			selectCategory = "activity";
			model.addAttribute("selectCategory", selectCategory);
		} else if( category.equals("산,바다")) {
			model.addAttribute("mapMarkerList", mapDao.mapMarkerSanBadaList("산", "바다"));
			selectCategory = "sanBada";
			model.addAttribute("selectCategory", selectCategory);
		} else if(category.equals("여행코스")){
			selectCategory = "tripCourse";
			model.addAttribute("selectCategory", selectCategory);
			model.addAttribute("mapMarkerList", mapDao.mapMarkerAllList());
			model.addAttribute("mapCourseList", mapDao.mapCourseList());
			System.out.println(mapDao.mapCourseList());
			return "thymeleaf/map/map_course";
		}else {
			model.addAttribute("mapMarkerList", mapDao.mapMarkerCategoryList(category));
			
			if(category.equals("관광지")) { selectCategory = "tour"; } 
			else if(category.equals("맛집")) { selectCategory = "food"; }
			else if(category.equals("카페")) { selectCategory = "cafe"; }
			else if(category.equals("숙박")) { selectCategory = "hotel"; }
			else if(category.equals("실내")) { selectCategory = "inside"; }
			else if(category.equals("테마파크")) { selectCategory = "themepark"; }
			else if(category.equals("축제")) { selectCategory = "event"; }
			
			
			model.addAttribute("selectCategory", selectCategory);
		}

		
		return "thymeleaf/map/map_0221";
	}
	
	//HttpServletRequest request, @RequestParam(value = "page", required = false, defaultValue = "1") String str_page, 
	@RequestMapping(value = "/mapDistance")
	@ResponseBody
	public List<MapDistanceDTO> getDistance(@RequestBody MapDistanceDTO[] data, Model model ) {
		// 클라이언트로부터 받은 데이터를 처리하여 리스트에 저장합니다.
		List<MapDistanceDTO> distanceList = new ArrayList<>();
		
		for (MapDistanceDTO mapDistanceDTO : data) {
			EventDTO map_eventDto = mapDistanceDTO.getEvent();
			if (map_eventDto != null) {
				String distance = mapDistanceDTO.getDistance();
				distanceList.add(mapDistanceDTO);
			}
		}

		// 거리를 기준으로 정렬합니다.
		Collections.sort(distanceList);
		int num_page = distanceList.get(0).getCurrentPage();
		// 페이징
		//String str_page = request.getParameter("page");
		//System.out.println("page = " + num_page);
		/*
		 * if (str_page == null || str_page.trim().equals(null) || str_page.isEmpty()) {
		 * str_page = "1"; // 페이지가 null 일 때 1로 설정 }
		 */

		//int num_page = Integer.parseInt(str_page);
		int pageItemCount = 15; // 한 페이지당 출력 될 게시글 수
		int startItemNum = (num_page - 1) * pageItemCount; // 페이지의 시작 게시글 넘버
		int endItemNum = num_page * pageItemCount; // 페이지의 마지막 게시글 넘버
		int totalItemCount = distanceList.size(); // 총 건수
		int prePage = num_page - 1; // 이전 페이지
		int nextPage = num_page + 1; // 다음 페이지
		int endPage_num = (int) Math.ceil((double) totalItemCount / pageItemCount); // 총 게시글 수에서 표시되는 아이템 수를 나눠 마지막 페이지
																					// 번호 확인
		
		/*
		 * List<MapDistanceDTO> distanceList_paging = new ArrayList<>();
		 * System.err.println("startItemNum = "+startItemNum);
		 * System.err.println("endItemNum = "+endItemNum); //페이지 마다 가져갈 배열의 수 for(int i
		 * = startItemNum ; i < endItemNum+1 ; i++) {
		 * distanceList_paging.add(distanceList.get(i)); }
		 */
		// 페이지 마다 보일 Itemlist
		//model.addAttribute("paginItemList", distanceList_paging);

		// 페이징에 필요한 model
		//model.addAttribute("currentPage", num_page);
		//model.addAttribute("mPage", prePage);
		//model.addAttribute("pPage", nextPage);
		//model.addAttribute("totalPages", totalItemCount);
		
		
		return distanceList;
	}

	
	
	
}
