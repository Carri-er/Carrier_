package com.ex.springboot.controller;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.springboot.dao.IAiDAO;
import com.ex.springboot.dto.CourseDTO;
import com.ex.springboot.dto.EventDTO;
import com.ex.springboot.dto.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class WidgetController {
	@Autowired
	private com.ex.springboot.dao.IAiDAO AiDAO;
	@Autowired
	private com.ex.springboot.dao.IMemberDAO member_dao;
	@Autowired
	private com.ex.springboot.dao.IPayDAO pay_dao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// 결제 하러 갈 때 정보 받기
	@RequestMapping("/checkout")
	public String checkout(HttpServletRequest request, Model model, MemberDTO dto) {
		/* 결제에 필요한 항목 */
		// 주문번호 = 아이디+시간
		// 이름
		// 이메일
		// 코스 제목
		// 핸드폰 번호
		// 결제 금액

		// 결제 파라미터
		Integer courseNum = AiDAO.getCourseNum();
		String Course_num = String.valueOf(courseNum);
		model.addAttribute("Course_num", Course_num);
		List<CourseDTO> DAO= AiDAO.Course_select_useCk(Course_num);
		String Member_Id = request.getParameter("Member_Id");
		String title = request.getParameter("Course_name");
		// int amount = Integer.parseInt(request.getParameter("discount"));
		System.out.println("widget Course_num : "+Course_num);
		 int amounttt= DAO.get(0).getAmount();
		String amount = String.valueOf(amounttt);
		// 난수 지정을 위한 날짜 가져오기 / 데이터 가공 - 하이픈 제거
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String getTime = currentDateTime.format(formatter);
		String orderId = Member_Id + getTime; // 난수 지정
		String phone;

		// 멤버 정보 불러오기
		dto = member_dao.memberList(Member_Id);
		phone = dto.getMember_Phone().replace("-", "");

		model.addAttribute("orderId", orderId);
		model.addAttribute("title", title);
		model.addAttribute("amount", amount);
		model.addAttribute("member", dto);
		model.addAttribute("customerMobilePhone", phone);

		// 전달
		model.addAttribute("Course_num", Course_num);

		System.out.println("주문번호:" + orderId + " 코스제목:" + title + " 결제금액:" + amount);

		return "thymeleaf/member/checkout";
	}

	@RequestMapping(value = "/confirm")
	public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody, HttpSession session, HttpServletRequest request) throws Exception {
		
		String Member_Id = (String) session.getAttribute("Member_Id");
		String Course_num = request.getParameter("courseNum");
		
		JSONParser parser = new JSONParser();
		String orderId;
		String amount;
		String paymentKey;
		String courseNum;
		
		
		try {
			// 클라이언트에서 받은 JSON 요청 바디입니다.
			JSONObject requestData = (JSONObject) parser.parse(jsonBody);
			paymentKey = (String) requestData.get("paymentKey");
			orderId = (String) requestData.get("orderId");
			amount = (String) requestData.get("amount");
			courseNum = (String) requestData.get("courseNum");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		;
		JSONObject obj = new JSONObject();
		obj.put("orderId", orderId);
		obj.put("amount", amount);
		obj.put("paymentKey", paymentKey);
		obj.put("courseNum", courseNum);

		System.out.println(courseNum);

		// 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
		// 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
		String widgetSecretKey = "test_sk_eqRGgYO1r5AZxBYALlPWrQnN2Eya";
		Base64.Encoder encoder = Base64.getEncoder();
		byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes("UTF-8"));
		String authorizations = "Basic " + new String(encodedBytes, 0, encodedBytes.length);

		// 결제를 승인하면 결제수단에서 금액이 차감돼요.
		URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Authorization", authorizations);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);

		OutputStream outputStream = connection.getOutputStream();
		outputStream.write(obj.toString().getBytes("UTF-8"));

		int code = connection.getResponseCode();
		boolean isSuccess = code == 200 ? true : false;

		InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

		if (isSuccess) {
			pay_dao.payCreate(Member_Id, Course_num, orderId);
		}

		// 결제 성공 및 실패 비즈니스 로직을 구현하세요.
		Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
		JSONObject jsonObject = (JSONObject) parser.parse(reader);
		responseStream.close();

		return ResponseEntity.status(code).body(jsonObject);
	}

	@GetMapping("/fail")
	public String fail() {
		return "thymeleaf/member/fail";
	}

	@GetMapping("/success")
	public String success(HttpServletRequest request, Model model, HttpSession session) {

		String Member_Id = (String) session.getAttribute("Member_Id");
		String orderId = request.getParameter("orderId");
		String Course_num = request.getParameter("num");
		System.out.println("================================");
		System.out.println("Member_Id:" + Member_Id + " 코스 번호:" + Course_num + " 주문 번호:" + orderId);
		System.out.println("================================");
		pay_dao.payCreate(Member_Id, Course_num, orderId);

		return "thymeleaf/member/success";
	}
}