package com.ex.springboot.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.springboot.dto.CourseDTO;
import com.ex.springboot.dto.EventDTO;

@Mapper
public interface IError {
	String handleError(int errorCode);
}
