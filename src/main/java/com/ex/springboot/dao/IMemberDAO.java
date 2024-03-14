package com.ex.springboot.dao;

import java.util.List;

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
	
	// 내정보 (마이페이지 내 정보 수정)
	public MemberDTO memberList(
			@Param("Member_Id") String Member_Id
	);
	
	// 회원수정
	public int editMember(
			@Param("Member_Name") String Member_Name, 
			@Param("Member_Age") int Member_Age, 
			@Param("Member_Email") String Member_Email,
			@Param("Member_Phone") String Member_Phone,
			@Param("Member_Pw") String Member_Pw,
			@Param("Member_Area") String Member_Area,
			@Param("Member_Thema") String Member_Thema,
			@Param("Member_profileimage") String Member_profileimage,
			@Param("Member_Id") String Member_Id
	);
	
	// 회원 삭제
	public int delMember(
			String Member_Id
	);
	public int delMember_Eventcomment(
			String Member_Id
	);
	public int delMember_Course(
			String Member_Id
	);
	
	
	// 로그인
	public MemberDTO login(
			@Param("Member_Id") String Member_Id,
			@Param("Member_Pw") String Member_Pw
	);
	
	// 아이디 찾기
	public MemberDTO Find_Id(
			@Param("Member_Name") String Member_Name,
			@Param("Member_Email") String Member_Email
	);
	
	// 패스워드 찾기
	public MemberDTO Find_Pw(
			@Param("Member_Id") String Member_Id,
			@Param("Member_Name") String Member_Name,
			@Param("Member_Email") String Member_Email
	);
	
	// 패스워드 수정
	public int FindPw_Result_Action(
			@Param("Member_Id") String Member_Id,
			@Param("Member_Pw") String Member_Pw
	);
	
	// 전체 회원 목록 보기
	public List<MemberDTO> AllMemberList();
	
}
