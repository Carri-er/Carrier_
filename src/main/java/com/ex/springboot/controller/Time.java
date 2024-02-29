package com.ex.springboot.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

public class Time {
	private static class TIME_MAXIMUM {
		public static final int SEC = 60;
		public static final int MIN = 60;
		public static final int HOUR = 24;
		public static final int DAY = 30;
		public static final int MONTH = 12;
	}
	public static String calculateTime(Date date) {
		long curTime = System.currentTimeMillis();
		long regTime = date.getTime();
		long diffTime = (curTime - regTime) / 1000;
		String msg = null;
		if (diffTime < TIME_MAXIMUM.SEC) {
			// sec
			msg = diffTime + "방금 전";
		} else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
			// min
			msg = diffTime + "분 전";
		} else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
			// hour
			msg = (diffTime) + "시간 전";
		} else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
			// day
			msg = (diffTime) + "일 전";
		} else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
			// day
			msg = (diffTime) + "달 전";
		} else {
			msg = (diffTime) + "년 전";
		}
		return msg;
	}
	
	@RequestMapping(value = "/memberPush", method = RequestMethod.POST)
    @ResponseBody
    public HashMap <String, Object> memberPush(HttpSession session, Model model) throws Exception {
        logger.info("memberPush");
 
        HashMap<String, Object> result = new HashMap <String,Object>();
 
        TogetherMemberVO vo = (TogetherMemberVO) session.getAttribute("member");
 
        String memberEmail = vo.getM_email();
 
        List<MemberNoticeVO> notice = memberNoticeService.notiList(memberEmail);
        
        for(MemberNoticeVO notiVO : notice) {    // 날짜 포맷 변경
            notiVO.setNoti_time(formatTimeString(notiVO.getComparetime()));
        }
        
        if (notice.size() > 0) {
            String size = "1";
 
            result.put("size", size);
            result.put("notice", notice);
        }
        
        return result;
    }



}