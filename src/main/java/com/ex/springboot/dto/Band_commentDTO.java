package com.ex.springboot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Band_commentDTO {
	private int band_feedComment_num; // 댓글 번호
	private int band_feed_num;   // 피드 번호
	private String Member_Id;  // 댓글 작성자 아이디
	private String  band_feedComment_content;  // 댓글 내용 
	private String bandFeed_comment_writeTime;  // 댓글 작성 시간
	private String Member_profileimage;   // 댓글 작성자 이미지 
}
