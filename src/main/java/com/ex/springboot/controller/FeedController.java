package com.ex.springboot.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ex.springboot.dao.IFeedDAO;
import com.ex.springboot.dto.FeedDTO;
import com.ex.springboot.dto.Feed_commentDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping
public class FeedController {
	public static String UPLOAD_MEMBER_DIRECTORY = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\static\\img\\feed_thumbnail";
	 
	@Autowired
	IFeedDAO feed_dao;

	// 피드 리스트
	@GetMapping("/feed")
	public String feed(Model model, HttpServletRequest request, FeedDTO feedDTO, HttpSession session, Time time) {
		String Feed_theme = "#" + request.getParameter("Feed_theme");
		String Feed_area = "#" + request.getParameter("Feed_area");
		String Member_Id = (String) session.getAttribute("Member_Id");

		System.out.println("search: " + Feed_theme + "/" + "search2: " + Feed_area);

		List<FeedDTO> feedList = new ArrayList<>();

		if (request.getParameter("Feed_theme") == null && request.getParameter("Feed_area") == null) {
			// 비로그인
			if (Member_Id == null) {
				feedList = feed_dao.feedList();
			} else {
				// 로그인 시
				feedList = feed_dao.feedList_login(Member_Id);
			}

		} else if (request.getParameter("Feed_theme") != null && !request.getParameter("Feed_theme").equals("")) {
			if (Member_Id == null) {
				feedList = feed_dao.feedList_theme(Feed_theme);
			} else {
				feedList = feed_dao.feedList_theme_login(Member_Id, Feed_theme);
			}

		} else if (request.getParameter("Feed_area") != null && !request.getParameter("Feed_area").equals("")) {
			if (Member_Id == null) {
				feedList = feed_dao.feedList_area(Feed_area);
			} else {
				feedList = feed_dao.feedList_area_login(Member_Id, Feed_area);
			}
		}
		model.addAttribute("feedList", feedList);
		
		
		// n시간 전 값 불러오기
		model.addAttribute("time", time);
	

		return "thymeleaf/feed/feed2";
	}

	// 피드 세부 - 페이지 & 댓글 목록 가져오기
	@GetMapping("/feed_show")
	public String feedShow(Model model, HttpServletRequest request, HttpSession session) {
		int Feed_num = Integer.parseInt(request.getParameter("num"));
		String Member_Id = (String) session.getAttribute("Member_Id");

		// 로그인 시 좋아요 여부 확인
		if (Member_Id == null) {
			model.addAttribute("feedList", feed_dao.feedShow(Feed_num));
		} else {
			model.addAttribute("feedList", feed_dao.feedShow_login(Member_Id, Feed_num));
		}
		// 피드 리스트
		// 피드 코멘트 리스트
		model.addAttribute("Feed_commentList", feed_dao.feedCommentList(Feed_num));
		model.addAttribute("Feed_commentDTO", new Feed_commentDTO());
		model.addAttribute("feedList_heart", feed_dao.feedCommentList(Feed_num));

		return "thymeleaf/feed/feed_show";
	}

	// 댓글 업데이트 - 페이지 & 댓글 목록 가져오기
	@GetMapping("/feed_comment_update")
	public String feedCommentUpdate(Model model, HttpServletRequest request) {
		int Feed_num = Integer.parseInt(request.getParameter("num"));
		int Feed_comment_num = Integer.parseInt(request.getParameter("comment_num"));

		System.out.println("Feed_comment_num" + Feed_comment_num);

		model.addAttribute("feedList", feed_dao.feedShow(Feed_num));
		model.addAttribute("Feed_commentList", feed_dao.feedCommentList(Feed_num));
		model.addAttribute("Feed_commentDTO", new Feed_commentDTO());
		model.addAttribute("Feed_comment_num", Feed_comment_num); // input 활성화 시킬 부분

		return "thymeleaf/feed/feed_comment_update";
	}

	// 피드 글쓰기 - 페이지
	@GetMapping("/feed_write")
	public String feed_write() {
		return "thymeleaf/feed/feed_write";
	}

	// 피드 글쓰기 - action
	@PostMapping("/feed_write_action")
	public String feed_write_action(Model model, HttpServletRequest request,
			@RequestPart(value = "Feed_thumbnail1", required = false) MultipartFile file1,
			@RequestPart(value = "Feed_thumbnail2", required = false) MultipartFile file2,
			@RequestPart(value = "Feed_thumbnail3", required = false) MultipartFile file3) {

		String Member_Id = request.getParameter("Member_Id");
		String Feed_title = request.getParameter("Feed_title");
		String Feed_content = request.getParameter("Feed_content");
		String Feed_theme = request.getParameter("Feed_theme");
		String Feed_area = request.getParameter("Feed_area");

		System.out.println("나는야 write 구문 : " + "Feed_title:" + Feed_title + "/ Feed_content: " + Feed_content
				+ "/ Feed_theme: " + Feed_theme + "/ Feed_area: " + Feed_area);

		try {
			// multiple 사용을 위함
			List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("Feed_thumbnail1");
			List<MultipartFile> files2 = ((MultipartHttpServletRequest) request).getFiles("Feed_thumbnail2");
			List<MultipartFile> files3 = ((MultipartHttpServletRequest) request).getFiles("Feed_thumbnail3");

			StringBuilder fileNames = new StringBuilder();

			int count = 0;

			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					String fileName = saveFile(file);
					if (fileNames.length() > 0) {
						fileNames.append(",");
						count++;
					}
					fileNames.append(fileName);
				}
			}

			if (count <= 3) {
				for (MultipartFile file : files2) {
					if (!file.isEmpty()) {
						String fileName = saveFile(file);
						if (fileNames.length() > 0) {
							fileNames.append(",");
							count++;
						}
						fileNames.append(fileName);
					}
				}
			}

			if (count <= 3) {
				for (MultipartFile file : files3) {
					if (!file.isEmpty()) {
						String fileName = saveFile(file);
						if (fileNames.length() > 0) {
							fileNames.append(",");
							count++;
						}
						fileNames.append(fileName);
					}
				}
			}

			// 개별 input 파일일 때
			if (file2 != null && !file2.isEmpty()) {
				String fileName2 = saveFile(file2);
				if (fileNames.length() > 0) {
					fileNames.append(",");
				}
				System.out.println("fileName2:  " + fileName2);
				fileNames.append(fileName2);
			}

			if (file3 != null && !file3.isEmpty()) {
				String fileName3 = saveFile(file3);
				if (fileNames.length() > 0) {
					fileNames.append(",");
				}
				System.out.println("fileName3:  " + fileName3);
				fileNames.append(fileName3);
			}

			// DB에 게시물 정보 저장
			feed_dao.feedWrite(Member_Id, Feed_title, Feed_content, Feed_theme, Feed_area, fileNames.toString());

			System.out.println("fileNames.toString():" + fileNames.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/feed";
	}

	// 파일 저장
	private String saveFile(MultipartFile file) throws Exception {
		String fileName = file.getOriginalFilename();
		Path fileNameAndPath = Paths.get(UPLOAD_MEMBER_DIRECTORY, fileName);
		Files.write(fileNameAndPath, file.getBytes());
		return fileName;
	}

	// 피드 업데이트 - 페이지
	@GetMapping("/feed_update")
	public String feed_update(@ModelAttribute FeedDTO dto, Model model, HttpServletRequest request) {
		int Feed_num = Integer.parseInt(request.getParameter("num"));

		model.addAttribute("feedList", feed_dao.feedShow(Feed_num));

		return "thymeleaf/feed/feed_update";
	}

	// 피드 Update - action
	@PostMapping("/feed_update_action")
	public String feed_update_action(Model model, HttpServletRequest request,
			@RequestPart(value = "Feed_thumbnail1", required = false) MultipartFile file1,
			@RequestPart(value = "Feed_thumbnail2", required = false) MultipartFile file2,
			@RequestPart(value = "Feed_thumbnail3", required = false) MultipartFile file3) {
		int Feed_num = Integer.parseInt(request.getParameter("num"));

		String Feed_title = request.getParameter("Feed_title");
		String Feed_content = request.getParameter("Feed_content");
		String Feed_theme = request.getParameter("Feed_theme");
		String Feed_area = request.getParameter("Feed_area");
		String Feed_thumbnail_old = request.getParameter("Feed_thumbnail_old");

		System.out.println(
				"나는야 업데이트 구문 : " + "Feed_title:" + Feed_title + "/ Feed_content: " + Feed_content + "/ Feed_theme: "
						+ Feed_theme + "/ Feed_area: " + Feed_area + "/ Feed_thumbnail_old: " + Feed_thumbnail_old);

		try {
			List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("Feed_thumbnail1");
			List<MultipartFile> files2 = ((MultipartHttpServletRequest) request).getFiles("Feed_thumbnail2");
			List<MultipartFile> files3 = ((MultipartHttpServletRequest) request).getFiles("Feed_thumbnail3");

			if ((file1 != null && !file1.isEmpty()) || (file2 != null && !file2.isEmpty())
					|| (file3 != null && !file3.isEmpty()) || !files.isEmpty() || !files2.isEmpty()
					|| !files3.isEmpty()) {

				StringBuilder fileNames = new StringBuilder();

				int count = 0;

				for (MultipartFile file : files) {
					if (!file.isEmpty()) {
						String fileName = saveFile(file);
						if (fileNames.length() > 0) {
							fileNames.append(",");
							count++;
						}
						fileNames.append(fileName);
					} else {

					}
				}

				if (count <= 3) {
					for (MultipartFile file : files2) {
						if (!file.isEmpty()) {
							String fileName = saveFile(file);
							if (fileNames.length() > 0) {
								fileNames.append(",");
								count++;
							}
							fileNames.append(fileName);
						}
					}
				}

				if (count <= 3) {
					for (MultipartFile file : files3) {
						if (!file.isEmpty()) {
							String fileName = saveFile(file);
							if (fileNames.length() > 0) {
								fileNames.append(",");
								count++;
							}
							fileNames.append(fileName);
						}
					}
				}

				/*
				 * if (file1 != null && !file1.isEmpty()) { String fileName1 = saveFile(file1);
				 * fileNames.append(fileName1);
				 * 
				 * System.out.println("fileName1 O : "+fileName1); }
				 * 
				 * if (file2 != null && !file2.isEmpty()) { String fileName2 = saveFile(file2);
				 * if (fileNames.length() > 0) { fileNames.append(","); }
				 * fileNames.append(fileName2); System.out.println("fileName2 O : "+fileName2);
				 * }
				 * 
				 * if (file3 != null && !file3.isEmpty()) { String fileName3 = saveFile(file3);
				 * if (fileNames.length() > 0) { fileNames.append(","); }
				 * fileNames.append(fileName3); System.out.println("fileName3 O : "+fileName3);
				 * }
				 */

				// DB에 게시물 정보 저장
				System.out.println("fileNames.toString():" + fileNames.toString());

				feed_dao.feedUpdate(Feed_title, Feed_content, Feed_theme, Feed_area, fileNames.toString(), Feed_num);

			} else {
				feed_dao.feedUpdate(Feed_title, Feed_content, Feed_theme, Feed_area, Feed_thumbnail_old, Feed_num);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/feed";
	}

	// 피드 Delete - action
	@GetMapping("/delFeed")
	public String delFeed(HttpServletRequest request, Model model, HttpSession session) {
		int Feed_num = Integer.parseInt(request.getParameter("num"));

		feed_dao.feedDel(Feed_num);

		System.out.println("~피드 삭제~");

		return "redirect:/feed";
	}

	/* 피드 댓글 - Logic */
	@PostMapping("/feed_comment_upload")
	public String feed_comment(@Valid @ModelAttribute("Feed_commentDTO") Feed_commentDTO commentDTO,
			BindingResult result, @RequestParam("Feed_comment") String Feed_comment, HttpServletRequest request,
			Model model) {
		int Feed_num = Integer.parseInt(request.getParameter("Feed_num"));
		String Member_Id = request.getParameter("Member_Id");
		String msg = "";
		System.out.println("FeedComment: " + Feed_num + "/" + Member_Id + "/" + Feed_comment);
		// 입력 데이터 검증
		if (Feed_comment == null || Feed_comment.trim().isEmpty()) {
			result.rejectValue("Feed_comment", "NotEmpty", "댓글은 비어있을 수 없습니다");
			msg = "1";
		} else if (Feed_comment.length() > 65) {
			result.rejectValue("Feed_comment", "Size", "댓글은 65자 이내여야 합니다");
			msg = "2";
		}

		if (result.hasErrors()) {
			// 유효성 검사 실패 시 처리
			return "redirect:/feed_show?num=" + Feed_num + "&msg=" + msg;
		} else {
			feed_dao.feedCommentCreate(Feed_num, Member_Id, Feed_comment);
			System.out.println("~댓글 달기~");
			return "redirect:/feed_show?num=" + Feed_num;
		}

	}

	/* 피드 댓글 수정 - Logic */
	@PostMapping("/feed_comment_update_action")
	public String feed_comment_update_action(@RequestParam("Feed_comment") String Feed_comment,
			HttpServletRequest request, Model model) {
		int Feed_num = Integer.parseInt(request.getParameter("Feed_num"));
		int Feed_comment_num = Integer.parseInt(request.getParameter("Feed_comment_num"));

		String Member_Id = request.getParameter("Member_Id");

		System.out.println("FeedComment: " + Feed_num + "/" + Member_Id + "/" + Feed_comment);

		feed_dao.feedCommentUpdate(Feed_comment, Feed_num, Feed_comment_num);

		System.out.println("~댓글 달기~");

		return "redirect:/feed_show?num=" + Feed_num;
	}

	// 피드 댓글 Delete - action
	@GetMapping("/feed_comment_del")
	public String feedCommentDel(HttpServletRequest request, Model model, HttpSession session) {
		int Feed_num = Integer.parseInt(request.getParameter("num"));
		int Feed_comment_num = Integer.parseInt(request.getParameter("comment_num"));

		feed_dao.feedCommentDel(Feed_num, Feed_comment_num);

		System.out.println("~댓삭~");

		return "redirect:/feed_show?num=" + Feed_num;
	}

	// 피드 댓글 좋아요 - action
	@GetMapping("/feed_comment_like")
	public String feed_comment_like(HttpServletRequest request, Model model) {
		int Feed_num = Integer.parseInt(request.getParameter("num"));
		int Feed_comment_num = Integer.parseInt(request.getParameter("comment_num"));
		int Feed_comment_like = Integer.parseInt(request.getParameter("on"));

		feed_dao.feedCommentLike(Feed_comment_like, Feed_num, Feed_comment_num);

		System.out.println("~댓글 조아요~");

		return "redirect:/feed_show?num=" + Feed_num;
	}

	// 피드 좋아요 - action
	@GetMapping("/feed_like")
	public String feed_like(HttpServletRequest request, Model model) {
		int Feed_num = Integer.parseInt(request.getParameter("num"));
		String Member_Id = request.getParameter("id");
		int Feed_heart = Integer.parseInt(request.getParameter("on"));
		int count = 0;

		System.out.println(Feed_heart);

		if (Feed_heart == 1) {
			feed_dao.feedLike(Feed_num, Member_Id);
			count = 1;
			feed_dao.feedLikeCount(Feed_num, count);

		} else if (Feed_heart == 0) {
			feed_dao.feedHate(Feed_num, Member_Id);
			count = -1;
			feed_dao.feedLikeCount(Feed_num, count);
		}

		System.out.println("~댓글 조아요~");

		return "redirect:/feed";
	}

	// 피드 좋아요 - action
	@GetMapping("/feed_show_like")
	public String feed_show_like(HttpServletRequest request, Model model) {
		int Feed_num = Integer.parseInt(request.getParameter("num"));
		String Member_Id = request.getParameter("id");
		int Feed_heart = Integer.parseInt(request.getParameter("on"));
		int count = 0;

		System.out.println(Feed_heart);

		if (Feed_heart == 1) {
			feed_dao.feedLike(Feed_num, Member_Id);
			count = 1;
			feed_dao.feedLikeCount(Feed_num, count);

		} else if (Feed_heart == 0) {
			feed_dao.feedHate(Feed_num, Member_Id);
			count = -1;
			feed_dao.feedLikeCount(Feed_num, count);
		}

		System.out.println("~댓글 조아요~");

		return "redirect:/feed_show?num=" + Feed_num;
	}

}
