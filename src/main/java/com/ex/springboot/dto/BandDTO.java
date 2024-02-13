package com.ex.springboot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BandDTO {
	private int band_code; //밴드 고유 코드
	private String band_name; //밴드 이름
	private String band_admin; //밴드관리자
	private int band_memberCount; //밴드 멤버수
	private String band_content; //밴드 상세설명
	private String band_thumnail;//밴드 썸네일
	private String band_day; // 밴드 개설일
}
