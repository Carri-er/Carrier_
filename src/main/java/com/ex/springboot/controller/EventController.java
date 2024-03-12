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

import com.ex.springboot.dto.CourseDTO;
import com.ex.springboot.dto.EventCommentDTO;
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
   public String info(Model model, HttpServletRequest request) {
      String page = request.getParameter("page");
      if (page == null || page.isEmpty()) {
         page = "1";
      }
      int pages = Integer.parseInt(page);
      int pageSize = 7; // 페이지당 아이템 수
      int pageSizeR = pageSize * pages;
      int totalCount = eventDAO.getPostCount(); // 전체 아이템 수
      int mPage = pages - 1;
      int pPage = pages + 1;
      // 페이징 계산
      int totalPages = (int) Math.ceil((double) totalCount / pageSize);
      int offset = (pages - 1) * pageSize; // 시작 아이템 인덱스
      System.out.println("offset" + offset);
      System.out.println("pageSize" + pageSize);
      // DAO에서 페이징된 데이터 가져오기
      List<EventDTO> events = eventDAO.listWithPagination(offset, pageSizeR);
      model.addAttribute("list", events);
      model.addAttribute("currentPage", pages);
      model.addAttribute("mPage", mPage);
      model.addAttribute("pPage", pPage);
      model.addAttribute("totalPages", totalPages);

//      System.out.println(eventDAO.list() + "호출");
      // model.addAttribute("list", eventDAO.list());
//      System.out.println(eventDAO.getDistinctTags() + "호출");
      model.addAttribute("tags", eventDAO.getDistinctTags());
      model.addAttribute("area", eventDAO.areaTag());
      model.addAttribute("getCount", eventDAO.getPostCount());
      System.out.println(eventDAO.getPostCount() + "개");

      return "thymeleaf/info/info";
   }
   @GetMapping("/info_search")
   public String info_search(Model model, HttpServletRequest request) {
      String page = request.getParameter("page");
      String key = request.getParameter("key");
      String[] keyArray = key.split("\\s+");
      if (keyArray.length >= 2 && (keyArray[1] == null || keyArray[1].trim().isEmpty())) {
          keyArray[1] = " ";
      }
      String key1 = keyArray[0];
      String key2 = keyArray.length >= 2 ? keyArray[1] : "ㄲ";
      if (page == null || page.isEmpty()) {
         page = "1";
      }
      
      int pages = Integer.parseInt(page);
      int pageSize = 7; // 페이지당 아이템 수
      int pageSizeR = pageSize * pages;
      int totalCount = eventDAO.getPostCountSearch(key1,key2); // 전체 아이템 수
      int mPage = pages - 1;
      int pPage = pages + 1;
      // 페이징 계산
      int totalPages = (int) Math.ceil((double) totalCount / pageSize);
      int offset = (pages - 1) * pageSize; // 시작 아이템 인덱스
      List<EventDTO> events = eventDAO.listWithPaginationSearch(offset, pageSizeR,key1,key2);
      System.out.println("offset" + offset);
      System.out.println("pageSize" + pageSize);
      // DAO에서 페이징된 데이터 가져오기
      model.addAttribute("list", events);
      model.addAttribute("currentPage", pages);
      model.addAttribute("mPage", mPage);
      model.addAttribute("pPage", pPage);
      model.addAttribute("key", key);
      model.addAttribute("totalPages", totalPages);
      
//      System.out.println(eventDAO.list() + "호출");
      // model.addAttribute("list", eventDAO.list());
//      System.out.println(eventDAO.getDistinctTags() + "호출");
      model.addAttribute("tags", eventDAO.getDistinctTags());
      model.addAttribute("area", eventDAO.areaTag());
      model.addAttribute("getCount", eventDAO.getPostCountSearch(key1,key2));
      System.out.println(eventDAO.getPostCount() + "개");
      
      return "thymeleaf/info/info";
   }
   @GetMapping("/hitDESC")
   public String hitDesc(Model model, HttpServletRequest request) {
       // 페이지 정보 가져오기
       String page = request.getParameter("page");
       if (page == null || page.isEmpty()) {
           page = "1";
       }
       int pages = Integer.parseInt(page);
       int pageSize = 7; // 페이지당 아이템 수
       int pageSizeR = pageSize * pages;
       // 페이징 계산
       int mPage = pages - 1;
       int pPage = pages + 1;
       int offset = (pages - 1) * pageSize; // 시작 아이템 인덱스
       System.out.println("offset" + offset);
       System.out.println("pageSize" + pageSize);
       
       // 전체 아이템 수 가져오기
       int totalCount = eventDAO.getHitDescCount(); // 인기글 전체 수
       int totalPages = (int) Math.ceil((double) totalCount / pageSize);
       
       // 인기글 조회를 위한 SQL문 실행
       List<EventDTO> events = eventDAO.hitDESC(offset, pageSizeR);
       model.addAttribute("list", events);

       model.addAttribute("currentPage", pages);
       model.addAttribute("mPage", mPage);
       model.addAttribute("pPage", pPage);
       model.addAttribute("totalPages", totalPages);
       
       model.addAttribute("tags", eventDAO.getDistinctTags());
       model.addAttribute("area", eventDAO.areaTag());
       model.addAttribute("getCount", eventDAO.getPostCount());
       System.out.println(eventDAO.getPostCount() + "개");

       return "thymeleaf/info/infoHit";
   }
   // 코스 여행 정보
   @GetMapping("/infoCourse")
   public String infoCourse(Model model, HttpServletRequest request) {
      String page = request.getParameter("page");
      if (page == null || page.isEmpty()) {
         page = "1";
      }
      int pages = Integer.parseInt(page);
      int pageSize = 7; // 페이지당 아이템 수
      int pageSizeR = pageSize * pages;
      int totalCount = eventDAO.getPostCount2(); // 전체 아이템 수
      int mPage = pages - 1;
      int pPage = pages + 1;
      // 페이징 계산
      int totalPages = (int) Math.ceil((double) totalCount / pageSize);
      int offset = (pages - 1) * pageSize; // 시작 아이템 인덱스
      System.out.println("offset" + offset);
      System.out.println("pageSize" + pageSize);
      
      // DAO에서 페이징된 데이터 가져오기
      List<CourseDTO> course = eventDAO.listWithPagination2(offset, pageSizeR);
      model.addAttribute("list", course);
      model.addAttribute("currentPage", pages);
      model.addAttribute("mPage", mPage);
      model.addAttribute("pPage", pPage);
      model.addAttribute("totalPages", totalPages);
      
//      System.out.println(eventDAO.list() + "호출");
      // model.addAttribute("list", eventDAO.list());
//      System.out.println(eventDAO.getDistinctTags() + "호출");
   model.addAttribute("tags", eventDAO.getDistinctTags());
   model.addAttribute("area", eventDAO.areaTag());
      model.addAttribute("getCount", totalCount);
   System.out.println(eventDAO.getPostCount() + "개");
      
      return "thymeleaf/info/infoCourse";
   }

   // 여행 상세 정보
   @GetMapping("/Event_view")
   public String Event_view(HttpServletRequest request, Model model) {
      String eid = request.getParameter("id");
//      System.out.println(eventDAO.EventView(eid) + "호출");
      model.addAttribute("view", eventDAO.EventView(eid));
      System.out.println(eid + "잘오는지");
      int num = Integer.parseInt(eid);
      int hit = eventDAO.EventView(eid).getEvent_hit();
      System.out.println("현재 조회수"+hit);
      hit++;
      eventDAO.EventView(eid).setEvent_hit(hit);
      System.out.println("수정후 조회수"+hit);
      System.out.println(eventDAO.EventHiteUpdate(eid,hit)+"개 행 업데이트"); 
      model.addAttribute("commentlist", eventDAO.EventCommentList(num));
      model.addAttribute("commentCount", eventDAO.EventCommentList(num).size());
      
      return "thymeleaf/info/Event_view";
   }

   // 여행 지역 태그 눌렀을시 지역 태그별 정보 가져오기
   @GetMapping("/Event_area")
   public String Event_area(HttpServletRequest request, Model model) {
       String area = request.getParameter("area");
       System.out.println("area : "+area);
       String page = request.getParameter("page");
       if (page == null || page.isEmpty()) {
           page = "1";
       }
       int pages = Integer.parseInt(page);
       int pageSize = 7; // 페이지당 아이템 수
       int pageSizeR = pageSize * pages;
       int totalCount = eventDAO.EventArea(area).size(); // 전체 아이템 수
       int mPage = pages - 1;
       int pPage = pages + 1;
       // 페이징 계산
       int totalPages = (int) Math.ceil((double) totalCount / pageSize);
       int offset = (pages - 1) * pageSize; // 시작 아이템 인덱스
       // DAO에서 페이징된 데이터 가져오기
       List<EventDTO> events = eventDAO.listWithPaginationArea(offset, pageSizeR, area);
       model.addAttribute("currentPage", pages);
       model.addAttribute("mPage", mPage);
       model.addAttribute("pPage", pPage);
       model.addAttribute("totalPages", totalPages);
       model.addAttribute("list", events);
       model.addAttribute("getCount", eventDAO.EventArea(area).size());
       model.addAttribute("area", eventDAO.areaTagselect(area));
       System.out.println("eventDAO.areaTagselect(area) : "+eventDAO.areaTagselect(area));
       model.addAttribute("showArea", area);
       model.addAttribute("tags", eventDAO.EventTag(area));
       return "thymeleaf/info/info";
   }

   // 여행 지역 태그 눌렀을시 지역 태그별 정보 가져오기
   @GetMapping("/Event_tags")
   public String Event_tags(HttpServletRequest request, Model model) {
      String tag = request.getParameter("tagss");

      String page = request.getParameter("page");
      if (page == null || page.isEmpty()) {
         page = "1";
      }
      int pages = Integer.parseInt(page);
      int pageSize = 7; // 페이지당 아이템 수
      int pageSizeR = pageSize * pages;
      int totalCount = eventDAO.EventTags(tag).size(); // 전체 아이템 수
      int mPage = pages - 1;
      int pPage = pages + 1;
      // 페이징 계산
      int totalPages = (int) Math.ceil((double) totalCount / pageSize);
      int offset = (pages - 1) * pageSize; // 시작 아이템 인덱스
      System.out.println("offset" + offset);
      System.out.println("pageSize" + pageSize);
      // DAO에서 페이징된 데이터 가져오기
      List<EventDTO> events = eventDAO.listWithPaginationTag(offset, pageSizeR,tag);
      model.addAttribute("list", events);
      model.addAttribute("currentPage", pages);
      model.addAttribute("mPage", mPage);
      model.addAttribute("pPage", pPage);
      model.addAttribute("totalPages", totalPages);
      
      // System.out.println(eventDAO.EventTags(tag) + "호출");
      //model.addAttribute("list", eventDAO.EventTags(tag));
      model.addAttribute("showTag", tag);
      model.addAttribute("tags", eventDAO.EventTagTag(tag));
      model.addAttribute("area", eventDAO.areaTagselectarea(tag));
      System.out.println("eventDAO.areaTagselectarea(tag)"+eventDAO.areaTagselectarea(tag));
      model.addAttribute("getCount", eventDAO.EventTags(tag).size());
      return "thymeleaf/info/info";
   }

   // 여행 글쓰기로 이동
   @GetMapping("/Event_write_content")
   public String Event_write_content() {
      return "thymeleaf/info/Event_write";
   }

   // 여행 댓글 작성
   @PostMapping("/Event_comment")
   public String Event_comment(HttpServletRequest request, Model model) {
      EventCommentDTO dto = new EventCommentDTO();
      String num = request.getParameter("Event_num");
      int number = Integer.parseInt(num);
      dto.setEvent_num(number);
      dto.setEventcomment_content(request.getParameter("Eventcomment_content"));
      dto.setMember_Id(request.getParameter("memberId"));
      eventDAO.EventCommentWrite(dto);
      return "redirect:/Event_view?id=" + num;
   }

   // 여행 댓글 수정
   @PostMapping("/Event_comment_write_update")
   public String Event_comment_write_update(HttpServletRequest request, Model model) {
      EventCommentDTO dto = new EventCommentDTO();
      String num = request.getParameter("Event_num");
      String comment = request.getParameter("eventcomment_num");
      int commentNum = Integer.parseInt(comment);
      int number = Integer.parseInt(num);
      dto.setEventcomment_num(commentNum);
      dto.setEvent_num(number);
      dto.setEventcomment_content(request.getParameter("Eventcomment_content"));
      dto.setMember_Id(request.getParameter("memberId"));
      eventDAO.EventCommentUpdate(dto);
      return "redirect:/Event_view?id=" + num;
   }

   // 댓글 수정 버튼 눌렀을시 수정할곳으로 이동
   @GetMapping("/Event_comment_update")
   public String Event_comment_update(HttpServletRequest request, Model model) {
      EventCommentDTO dto = new EventCommentDTO();
      String id = request.getParameter("id");
      String num = request.getParameter("num");
      model.addAttribute("view", eventDAO.EventView(id));
      int num2 = Integer.parseInt(id);
      model.addAttribute("commentlist", eventDAO.EventCommentList(num2));
      model.addAttribute("list", eventDAO.EventCommentListUpdate(num));
      return "thymeleaf/info/Event_comment_update";
   }

   // 여행 글쓰기로 이동
   @RequestMapping(value = "/Event_write_update", method = RequestMethod.GET)
   public ModelAndView get(@ModelAttribute("formData") EventDTO eventDTO, ModelAndView mav,
         HttpServletRequest request) {
      String num = request.getParameter("num");
      mav.addObject("formData", eventDAO.EventView(num));
      mav.setViewName("thymeleaf/info/Event_write_update");
      return mav;
   }

   @RequestMapping(value = "/Event_write_update", method = RequestMethod.POST)
   public ModelAndView send(@ModelAttribute("formDate") EventDTO eventDTO, ModelAndView mav,
         HttpServletRequest request) {
      String num = request.getParameter("num");
      mav.addObject("formData", eventDAO.EventView(num));
      mav.setViewName("thymeleaf/info/Event_write_update");
      return mav;
   }

   // 여행 정보 삭제
   @GetMapping("/Event_write_delete")
   public String Event_write_delete(HttpServletRequest request, Model model) {
      String num = request.getParameter("num");
      eventDAO.EventCommentDelete(num);
      return "redirect:/Event_delete?num=" + num;
   }
   
   // 여행 정보 삭제
   @GetMapping("/Event_delete")
   public String Event_delete(HttpServletRequest request, Model model) {
      String num = request.getParameter("num");
      eventDAO.event_delete(num);
      model.addAttribute("delete", eventDAO.event_delete(num));
      return "redirect:/info";
   }

   // 여행 댓글 삭제
   @GetMapping("/EventCommentUpdateDelete")
   public String EventCommentUpdateDelete(HttpServletRequest request, Model model) {
      String num = request.getParameter("num");
      String id = request.getParameter("id");
      System.out.println(eventDAO.EventCommentUpdateDelete(num) + "댓글 삭제");
      return "redirect:/Event_view?id=" + id;
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
      return "redirect:/info?msg=1";
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
         String old = request.getParameter("Event_thumbnail_old");
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
            thumbnail = old;
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
         System.out.println("뭐가 들어있는지 보자" + dto.getEvent_num());
         System.out.println("--글수정 완료--");
      } catch (Exception e) {
         e.printStackTrace();
      }
      return "redirect:/Event_view?id="+num;
   }

}
