package com.ex.springboot.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberDAO {
	
	// 회원가입
	public int addMember(
			String Member_Name, 
			int Member_Age, 
			String Member_Id,
			String Member_Email,
			String Member_Phone,
			String Member_Pw,
			String Member_Area,
			String Member_Thema,
			String Member_profileimage );
}
