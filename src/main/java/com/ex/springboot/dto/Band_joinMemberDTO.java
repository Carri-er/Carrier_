package com.ex.springboot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Band_joinMemberDTO {
	private int band_joinMember_num;
	private int band_code;
	private String Member_Id;
	private String band_name; 
    private int band_memberCount;
    private String band_thumnail;
    private String Member_name;
    private String Member_profileimage;
}
