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
	public List<String> areaTag();
	public List<String> areaTagselect(String id);
	public List<String> areaTagselectarea(String id);
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
	// 페이징된 데이터 가져오기
	public List<EventDTO> listWithPagination(@Param("offset") int offset, @Param("pageSize") int pageSize);
	public List<EventDTO> listWithPaginationArea(@Param("offset") int offset, @Param("pageSize") int pageSize,@Param("area")String area);
	public List<EventDTO> listWithPaginationTag(@Param("offset") int offset, @Param("pageSize") int pageSize,@Param("tag")String tag);
	public List<EventCommentDTO> EventCommentList(int evnetNum);
	public List<EventCommentDTO> EventCommentListUpdate(String evnetNum);
	public List<EventCommentDTO> EventCommentListUpdateList(String id);
	public int EventCommentWrite(EventCommentDTO dto);
	public int EventCommentUpdate(EventCommentDTO dto);
	public int EventCommentUpdateDelete(String id);
	public int EventCommentDelete(String id);
}
