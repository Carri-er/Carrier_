package com.ex.springboot.dto;

import lombok.Data;

@Data
public class FeedDTO {
	private int Feed_num; //피드 번호
	private String Member_Id; //작성한 멤버 id
	private String Feed_title; //피드제목
	private String Feed_content; //피드내용
	private String Feed_category; //피드분류
	private String Feed_uploadday; //올린날짜
	private String Feed_updateday; //수정날짜
	private String Feed_heart; //하트 수
	private String Feed_thumbnail; //피드 썸네일 이미지 파일 명
}
