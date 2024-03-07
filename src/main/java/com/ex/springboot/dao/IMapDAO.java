package com.ex.springboot.dao;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.springboot.dto.EventDTO;

@Mapper
public interface IMapDAO {
	
	//event 테이블에 저장된 전체 데이터에서 위도, 경도, 이름, 카테고리, num 열들을 출력하는 리스트
	public List<EventDTO> mapMarkerAllList();
	
	//event 테이블의 Event_category 가 관광지인 리스트
	public List<EventDTO> mapMarkerCategoryList(String category);
	
	//event 테이블의 Event_category 가 산, 바다 리스트
		public List<EventDTO> mapMarkerSanBadaList(String San, String Bada);
	
	// 페이지당 출력될 게시글 리스트
	public List<EventDTO> pageItemList(
				@Param("startItemNum") int startItemNum, 
				@Param("endItemNum") int endItemNum
			);
	
	
}
