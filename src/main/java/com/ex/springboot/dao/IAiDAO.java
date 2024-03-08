package com.ex.springboot.dao;


import java.util.List; 

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.springboot.dto.CourseDTO;
import com.ex.springboot.dto.EventCommentDTO;
import com.ex.springboot.dto.EventDTO;

@Mapper
public interface IAiDAO {
	public List<EventDTO> firstlist(String area);
	public List<EventDTO> list(String area, String thema,String thema2,String thema3,String thema4,String thema5,String thema6,String thema7,String food,String hotel);
	public List<EventDTO> listThema(String area, String thema);
	public List<EventDTO> listCafe(String area, String thema);
	public List<EventDTO> listHotel(String area, String thema);
	public List<EventDTO> listFood(String area, String thema);
	public List<EventDTO> listCafeArea2(String area, String thema,String area2);
	public List<EventDTO> listFoodArea2(String area, String thema,String area2);
	public List<EventDTO> listAll(String area, String thema,String thema2,String hotel);
	public List<EventDTO> listAll2(String area, String thema,String thema2);
	public List<EventDTO> listCourse(String num);
	public int save_course_insert(String id, String name, String thema,String area, String content, String distance,String day,String num,String img );
	public List<CourseDTO> Course_select(String num);
	public List<CourseDTO> Course_select4(String num);
	public List<CourseDTO> Course_view_list(String num);
	public int Course_update(String name, String content, String num);
	public int Course_delete(String num);
	public List<CourseDTO> CourseListHome();
	public List<CourseDTO> CourseListHome2();
}
