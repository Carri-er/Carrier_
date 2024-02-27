package com.ex.springboot.dao;


import java.util.List; 

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.springboot.dto.CourseDTO;
import com.ex.springboot.dto.EventCommentDTO;
import com.ex.springboot.dto.EventDTO;

@Mapper
public interface IAiDAO {
	public List<EventDTO> list(String area, String thema,String thema2,String thema3,String thema4,String thema5,String thema6,String thema7);
	public List<EventDTO> list2(String area, String thema,String thema2,String thema3,String thema4,String thema5,String thema6,String thema7,String area2);
	public List<EventDTO> listCafe(String area, String thema);
	public List<EventDTO> listFood(String area, String thema);
	public List<EventDTO> listCafeArea2(String area, String thema);
	public List<EventDTO> listFoodArea2(String area, String thema);
	public List<EventDTO> listAll(String area, String thema,String thema2);
	public List<EventDTO> listAll2(String area, String thema,String thema2);
	public List<EventDTO> listCourse(String num);
	public int save_course_insert(String id, String name, String thema,String area, String content, String distance,String num );
	public List<CourseDTO> Course_select(String num);
	public List<CourseDTO> Course_select4(String num);
	public List<CourseDTO> Course_view_list(String num);
	public int Course_update(String name, String content, String num);
	public int Course_delete(String num);
}
