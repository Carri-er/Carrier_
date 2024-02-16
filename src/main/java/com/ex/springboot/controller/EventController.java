package com.ex.springboot.controller;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ex.springboot.dto.EventDTO;

import jakarta.servlet.http.HttpServletRequest;

import com.ex.springboot.dao.IEventDAO;

@Controller
@RequestMapping
public class EventController {
	public static String UPLOAD_EVENT_DIRECTORY = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\static\\img\\info";
	@Autowired
	private com.ex.springboot.dao.IEventDAO eventDAO; // 다형성

	// 여행 정보
	@GetMapping("/info")
	public String info(Model model) {
//		System.out.println(eventDAO.list() + "호출");
		model.addAttribute("list", eventDAO.list());
//		System.out.println(eventDAO.getDistinctTags() + "호출");
		model.addAttribute("tags", eventDAO.getDistinctTags());
		model.addAttribute("getCount", eventDAO.getPostCount());
		System.out.println( eventDAO.getPostCount()+"개");
		
		return "thymeleaf/info/info";
	}

	// 여행 상세 정보
	@GetMapping("/Event_view")
	public String Event_view(HttpServletRequest request, Model model) {
		String eid = request.getParameter("id");
		String evnetNum = request.getParameter("Event_num");
//		System.out.println(eventDAO.EventView(eid) + "호출");
		model.addAttribute("view", eventDAO.EventView(eid));
		System.out.println(eid+"잘오는지");
		int num = Integer.parseInt(eid);
		System.out.println(num+"sssssss");

		model.addAttribute("commentlist", eventDAO.EventCommentList(num));
		System.out.println(eventDAO.EventCommentList(num).size());
		return "thymeleaf/info/Event_view";
	}
	// 여행 지역 태그 눌렀을시 지역 태그별 정보 가져오기
	@GetMapping("/Event_area")
	public String Event_area(HttpServletRequest request, Model model) {
		String area = request.getParameter("area");
		//System.out.println(eventDAO.EventArea(area) + "호출");
		model.addAttribute("list", eventDAO.EventArea(area));
		model.addAttribute("getCount",eventDAO.EventArea(area).size());
		model.addAttribute("tags", eventDAO.EventTag(area));
		
		return "thymeleaf/info/info";
	}
	// 여행 지역 태그 눌렀을시 지역 태그별 정보 가져오기
	@GetMapping("/Event_tags")
	public String Event_tags(HttpServletRequest request, Model model) {
		
		String tag = request.getParameter("tagss");
		//System.out.println(eventDAO.EventTags(tag) + "호출");
		model.addAttribute("list", eventDAO.EventTags(tag));
		model.addAttribute("tags", eventDAO.EventTagTag(tag));
		model.addAttribute("getCount", eventDAO.EventTags(tag).size());
		return "thymeleaf/info/info";
	}

	// 여행 글쓰기로 이동
	@GetMapping("/Event_write_content")
	public String Event_write_content() {
		return "thymeleaf/info/Event_write";
	}
	// 여행 글쓰기로 이동
	@PostMapping("/Event_comment")
	public String Event_comment(HttpServletRequest request, Model model) {
		String evnetNum = request.getParameter("Event_num");
		model.addAttribute("commentlist", eventDAO.EventCommentList(Integer.parseInt(evnetNum)));
		System.out.println("출력이 되는지");
		return "thymeleaf/info/Event_view";
	}

	// 여행 글쓰기로 이동
	@RequestMapping(value="/Event_write_update", method = RequestMethod.GET)
	public ModelAndView get(@ModelAttribute("formData") EventDTO eventDTO, ModelAndView mav,HttpServletRequest request) {
		String num = request.getParameter("num");
		mav.addObject("formData", eventDAO.EventView(num));
		mav.setViewName("thymeleaf/info/Event_write_update");
		return mav;
	}
	@RequestMapping(value="/Event_write_update", method = RequestMethod.POST)
	public ModelAndView send(@ModelAttribute("formDate") EventDTO eventDTO, ModelAndView mav,HttpServletRequest request) {
		String num = request.getParameter("num");
		mav.addObject("formData", eventDAO.EventView(num));
		mav.setViewName("thymeleaf/info/Event_write_update");
		return mav;
	}
	
	// 여행 정보 삭제
	@GetMapping("/Event_write_delete")
	public String Event_write_delete(HttpServletRequest request, Model model) {
		String num = request.getParameter("num");
	System.out.println(eventDAO.event_delete(num) + "호출");
		model.addAttribute("delete", eventDAO.event_delete(num));
		return "redirect:/info";
	}
	// 여행 글 업로드
	@PostMapping("/Event_write")

	public String Event_write(HttpServletRequest request, Model model,
			@RequestParam("Event_thumbnail") MultipartFile file) {
		EventDTO dto = new EventDTO();
		try {

			StringBuilder fileNames = new StringBuilder();

			String fileEmpty = "file_empty";
			String thumbnail = "user_rabbit.jpg";

			// 사용자가 파일을 넣었을 때
			if (!file.isEmpty()) {

				Path fileNameAndPath = Paths.get(UPLOAD_EVENT_DIRECTORY, file.getOriginalFilename());
				// 설정한 디렉토리에 파일 업로드
				fileNames.append(file.getOriginalFilename());
				byte[] fileSize = file.getBytes(); // 이미지에 대한 정보 값을 바이트 배열로 가져온다.
				Files.write(fileNameAndPath, fileSize);

				thumbnail = fileNames.toString();

			} else {
				model.addAttribute("Member_profileimage", fileEmpty); // 이미지 이름 저장
			}

			dto.setEvent_address(request.getParameter("Event_address"));
			dto.setEvent_area(request.getParameter("Event_area"));
			dto.setEvent_area2(request.getParameter("Event_area2"));
			dto.setEvent_category(request.getParameter("Event_category"));
			dto.setEvent_content(request.getParameter("Event_content"));
			dto.setEvent_endtime(request.getParameter("Event_endtime"));
			dto.setEvent_host(request.getParameter("Event_host"));
			dto.setEvent_mapX(request.getParameter("Event_mapX"));
			dto.setEvent_mapY(request.getParameter("Event_mapY"));
			dto.setEvent_name(request.getParameter("Event_name"));
			dto.setEvent_parking(request.getParameter("Event_parking"));
			dto.setEvent_rest(request.getParameter("Event_rest"));
			dto.setEvent_phone(request.getParameter("Event_phone"));
			dto.setEvent_starttime(request.getParameter("Event_starttime"));
			dto.setEvent_tag(request.getParameter("Event_tag"));
			dto.setEvent_tag2(request.getParameter("Event_tag2"));
			dto.setEvent_tag3(request.getParameter("Event_tag3"));
			dto.setEvent_tag4(request.getParameter("Event_tag4"));
			dto.setEvent_tag5(request.getParameter("Event_tag5"));
			dto.setEvent_thumbnail(thumbnail);
			dto.setEvent_time(request.getParameter("Event_time"));
			dto.setEvent_title(request.getParameter("Event_title"));
			eventDAO.event_write(dto);
			
			System.out.println("--글작성 완료--");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/info";
	}
	// 여행 글 수정 업로드
		@PostMapping("/Event_write_update_write")

		public String Event_write_update_write(HttpServletRequest request, Model model,
				@RequestParam("Event_thumbnail") MultipartFile file) {
			EventDTO dto = new EventDTO();
			int num = Integer.parseInt(request.getParameter("num"));
			System.out.println(num);
			try {

				StringBuilder fileNames = new StringBuilder();

				String fileEmpty = "file_empty";
				String thumbnail = "user_rabbit.jpg";

				// 사용자가 파일을 넣었을 때
				if (!file.isEmpty()) {

					Path fileNameAndPath = Paths.get(UPLOAD_EVENT_DIRECTORY, file.getOriginalFilename());
					// 설정한 디렉토리에 파일 업로드
					fileNames.append(file.getOriginalFilename());
					byte[] fileSize = file.getBytes(); // 이미지에 대한 정보 값을 바이트 배열로 가져온다.
					Files.write(fileNameAndPath, fileSize);

					thumbnail = fileNames.toString();

				} else {
					model.addAttribute("Member_profileimage", fileEmpty); // 이미지 이름 저장
				}
				dto.setEvent_num(num);
				dto.setEvent_address(request.getParameter("Event_address"));
				dto.setEvent_area(request.getParameter("Event_area"));
				dto.setEvent_area2(request.getParameter("Event_area2"));
				dto.setEvent_category(request.getParameter("Event_category"));
				dto.setEvent_content(request.getParameter("Event_content"));
				dto.setEvent_endtime(request.getParameter("Event_endtime"));
				dto.setEvent_host(request.getParameter("Event_host"));
				dto.setEvent_mapX(request.getParameter("Event_mapX"));
				dto.setEvent_mapY(request.getParameter("Event_mapY"));
				dto.setEvent_name(request.getParameter("Event_name"));
				dto.setEvent_parking(request.getParameter("Event_parking"));
				dto.setEvent_rest(request.getParameter("Event_rest"));
				dto.setEvent_phone(request.getParameter("Event_phone"));
				dto.setEvent_starttime(request.getParameter("Event_starttime"));
				dto.setEvent_tag(request.getParameter("Event_tag"));
				dto.setEvent_tag2(request.getParameter("Event_tag2"));
				dto.setEvent_tag3(request.getParameter("Event_tag3"));
				dto.setEvent_tag4(request.getParameter("Event_tag4"));
				dto.setEvent_tag5(request.getParameter("Event_tag5"));
				dto.setEvent_thumbnail(thumbnail);
				dto.setEvent_time(request.getParameter("Event_time"));
				dto.setEvent_title(request.getParameter("Event_title"));

				eventDAO.event_write_update(dto);
				System.out.println("뭐가 들어있는지 보자"+dto.getEvent_num());
				System.out.println("--글수정 완료--");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:/info";
		}

}