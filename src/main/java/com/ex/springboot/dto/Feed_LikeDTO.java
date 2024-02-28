package com.ex.springboot.dto;

import lombok.Data;

@Data
public class Feed_LikeDTO {
	private int Feed_num; //피드 번호
	private String Member_Id; //작성한 멤버 id
	private int Feed_heart_on; //하트 on/off
}
