package com.ex.springboot.dao;

import java.math.BigDecimal;
import java.util.List; 

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.springboot.dto.EventCommentDTO;
import com.ex.springboot.dto.EventDTO;

@Mapper
public interface IEventDAO {
	public List<EventDTO> list();
	public List<String> getDistinctTags();
	public EventDTO EventView(String id);
	public int event_write(EventDTO dto);
	public int event_write_update(EventDTO dto);
	public int event_delete(String id);
	public List<EventDTO> EventArea(String id);
	public List<EventDTO> EventTags(String id);
	public List<String> EventTag(String id);
	public List<String> EventTagTag(String id);
	public int getPostCount();
	public int getPostCountTag();
	public List<EventCommentDTO> EventCommentList(int evnetNum);
}
