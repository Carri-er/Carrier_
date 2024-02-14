package com.ex.springboot.dao;

import java.util.List; 

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.springboot.dto.EventDTO;

@Mapper
public interface IEventDAO {
	public List<EventDTO> list();
	public List<String> getDistinctTags();
	public EventDTO EventView(String id);
	public int event_write(EventDTO dto);
}
