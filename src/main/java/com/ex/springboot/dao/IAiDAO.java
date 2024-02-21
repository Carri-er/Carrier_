package com.ex.springboot.dao;


import java.util.List; 

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.springboot.dto.EventCommentDTO;
import com.ex.springboot.dto.EventDTO;

@Mapper
public interface IAiDAO {
	public List<EventDTO> list(String area, String thema,String thema2,String thema3,String thema4,String thema5,String thema6,String thema7,String thema8,String thema9);
}
