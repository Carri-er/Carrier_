package com.ex.springboot.dto;

import java.util.Date;

import com.ex.springboot.controller.Time;

import lombok.Data;

@Data
public class FeedDTO {
	private int Feed_num; //피드 번호
	private String Member_Id; //작성한 멤버 id
	private String Member_profileimage; //작성한 멤버 프로필 이미지
	private String Feed_title; //피드제목
	private String Feed_content; //피드내용
	private String Feed_theme; //테마
	private String Feed_area; //지역
	private String Feed_uploadday; //올린날짜
	private String Feed_updateday; //수정날짜
	private int Feed_heart; //하트 수
	private int Feed_heart_on; //하트 on/off
	private String Feed_thumbnail; //피드 썸네일 이미지 파일 명
	
	// n시간 전 사용을 위함
	public void setFeed_updateday(Date date) {
		this.Feed_updateday = Time.calculateTime(date); // 기존의 getter, setter에서 변경된 부분
	}	
}
