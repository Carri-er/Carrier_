package com.ex.springboot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BandFeedDTO {
	private int band_feed_num; // 밴드 글 번호
	private int band_code;  // 밴드 고유 번호 (fk)
	private String Member_id; // 작성한 멤버 id 
	private String band_feed_content; // 밴드 글 내용
	private String band_feed_uploadday; // 글 작성 시간
	private String band_feed_updateday; // 글 수정 시간
	private String Member_profileimage; // 멤버 프로필 사진
}
