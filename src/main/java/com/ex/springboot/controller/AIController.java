package com.ex.springboot.controller;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

import com.ex.springboot.dto.EventCommentDTO;
import com.ex.springboot.dto.EventDTO;

import jakarta.servlet.http.HttpServletRequest;

import com.ex.springboot.dao.IEventDAO;

@Controller
@RequestMapping
public class AIController {
	public static String UPLOAD_EVENT_DIRECTORY = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\static\\img\\info";

	@Autowired
	private com.ex.springboot.dao.IAiDAO AiDAO; // 다형성

	@GetMapping("/aicc")
	public String aicc() {
		return "thymeleaf/aicc/aicc";
	}
	
	@GetMapping("/aiccShow")
	public String aiccShow(HttpServletRequest request, Model model) {
		String area = request.getParameter("areaChk");
		String thema = request.getParameter("thema");
		String thema2 = request.getParameter("thema2");
		String thema3 = request.getParameter("thema3");
		String thema4 = request.getParameter("thema4");
		String thema5 = request.getParameter("thema5");
		String thema6 = request.getParameter("thema6");
		String thema7 = request.getParameter("thema7");
		String thema8 = request.getParameter("thema8");
		String thema9 = request.getParameter("thema9");
		
		thema = thema != null ? thema : " ";
		thema2 = thema2 != null ? thema2 : " ";
		thema3 = thema3 != null ? thema3 : " ";
		thema4 = thema4 != null ? thema4 : " ";
		thema5 = thema5 != null ? thema5 : " ";
		thema6 = thema6 != null ? thema6 : " ";
		thema7 = thema7 != null ? thema7 : " ";
		thema8 = thema8 != null ? thema8 : " ";
		thema9 = thema9 != null ? thema9 : " ";

		System.out.println("테마값이 뭐야?"+thema);
		System.out.println("테마값이 뭐야thema4?"+thema5);
		model.addAttribute("aicc",AiDAO.list(area,thema,thema2,thema3,thema4,thema5,thema6,thema7,thema8,thema9));
		return "thymeleaf/map/map";
	}






	// 여행 글 업로드
	@PostMapping("/Event_write222")

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
			//eventDAO.event_write(dto);

			System.out.println("--글작성 완료--");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/info?msg=1";
	}

	// 여행 글 수정 업로드
	@PostMapping("/Event_write_update_write222")

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

			//eventDAO.event_write_update(dto);
			System.out.println("뭐가 들어있는지 보자" + dto.getEvent_num());
			System.out.println("--글수정 완료--");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/info";
	}

}