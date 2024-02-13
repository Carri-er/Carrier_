package com.ex.springboot.dto;

import lombok.Data;

@Data
public class EventDTO {
	private int Event_num;
	private String Event_title;
	private String Event_content;
	private String Event_category;
	private int hit;
	private String Event_uploadday;
	private String Event_update;
	private String Event_phone;
	private String Event_address;
	private String Event_time;
	private String Event_starttime;
	private String Event_endtime;
	private String Event_host;
	private String Event_thumbnail;
	private String Event_img;
	private String Event_mapX;
	private String Event_mapY;
}
