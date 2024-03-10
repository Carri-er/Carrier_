package com.ex.springboot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.springboot.dto.PayDTO;

@Mapper
public interface IPayDAO {
	
	// 결제 
	public int payCreate(
			@Param("Member_Id") String Member_Id,
			@Param("Course_num") String Course_num,
			@Param("orderId") String orderId
			);
	
	// 결제정보
	public PayDTO payList(
			@Param("Member_Id") String Member_Id
	);
	
	// 내 전체 결제정보 - 관리자
	public List<PayDTO> myPayList();
	
	// 전체 결제정보 - 관리자
	public List<PayDTO> allPayList();
}
