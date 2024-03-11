package com.ex.springboot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.springboot.dto.PayDTO;

@Mapper
public interface IPayDAO {
	// 결제 
	public List<PayDTO> payOrder(
			@Param("orderId") String orderId
			);
	public List<PayDTO> payList(
			@Param("Member_Id") String Member_Id
			);
	public int payCreate(
			@Param("Member_Id") String Member_Id,
			@Param("Course_num") String Course_num,
			@Param("orderId") String orderId
			);
	
	public List<PayDTO> courseReseveList( @Param("Member_Id") String Member_Id );
	
}
