package com.ex.springboot.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.springboot.dto.MemberDTO;

@Mapper
public interface IMemberDAO {
	
	// 회원가입
	public int addMember(
		@Param("Member_Name") String Member_Name, 
		@Param("Member_Age") int Member_Age, 
		@Param("Member_Id") String Member_Id,
		@Param("Member_Email") String Member_Email,
		@Param("Member_Phone") String Member_Phone,
		@Param("Member_Pw") String Member_Pw,
		@Param("Member_Area") String Member_Area,
		@Param("Member_Thema") String Member_Thema,
		@Param("Member_profileimage") String Member_profileimage
	);
	
	// 로그인
		//TODO 자료형 확인할 것 0215	
	public MemberDTO login(
		@Param("Member_Id") String Member_Id,
		@Param("Member_Pw") String Member_Pw
	);
}
