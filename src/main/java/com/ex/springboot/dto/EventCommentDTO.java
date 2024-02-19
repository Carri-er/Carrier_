package com.ex.springboot.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class EventCommentDTO {
	private String Member_Id;
	private int eventcomment_num;
	private String eventcomment_content;
	private String eventcomment_upload;
	private String eventcomment_update;
	private int Event_num;
	private String Member_profileimage;
}
