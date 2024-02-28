package com.ex.springboot.dto;

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
}
