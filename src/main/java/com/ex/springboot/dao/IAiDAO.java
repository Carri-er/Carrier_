package com.ex.springboot.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.springboot.dto.CourseDTO;
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
	public int save_course_insert(@Param("memberId") String id, @Param("Course_name") String name, @Param("Course_thema") String thema, @Param("Course_Area") String area, @Param("Course_content") String content, @Param("Course_distance") String distance, @Param("day") String day, @Param("number") String num, @Param("img") String img, @Param("amount") String amount);
	public List<CourseDTO> Course_select(String num);
	public CourseDTO Course_select_useCk(String num);
	public List<CourseDTO> Course_select4(String num);
	public List<CourseDTO> Course_view_list(String num);
	public int Course_update(String name, String content, String num);
	public int Course_delete(String num);
	public List<CourseDTO> CourseListHome();
	public List<CourseDTO> CourseListHome2();
	
	public int getCourseNum();
}
