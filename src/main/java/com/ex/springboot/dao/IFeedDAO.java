package com.ex.springboot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.springboot.dto.FeedDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Mapper // 인터페이스를 xml로 구현하겠다.
public interface IFeedDAO {
	//피드 글쓰기
	public int feedWrite(
			@Param("Member_Id") String Member_Id, 
			@Param("Member_profileimage") String Member_profileimage, 
			@Param("Feed_title") String Feed_title,
			@Param("Feed_content") String Feed_content,
			@Param("Feed_theme") String Feed_theme,
			@Param("Feed_area") String Feed_area,
			@Param("Feed_thumbnail") String Feed_thumbnail
	);

	//피드 전체 페이지
	public List<FeedDTO> feedList();
	
	//피드 세부 페이지
	public FeedDTO feedShow(
			@Param("Feed_num") int Feed_num
	);
	
	//피드 업데이트
	public int feedUpdate(
			@Param("Feed_title") String Feed_title,
			@Param("Feed_content") String Feed_content,
			@Param("Feed_theme") String Feed_theme,
			@Param("Feed_area") String Feed_area,
			@Param("Feed_thumbnail") String Feed_thumbnail,
			@Param("Feed_num") int Feed_num
	);
	
	 // 피드 삭제 
	public int feedDel(
		@Param("Feed_num") int Feed_num 
	 );
	
	
	// 피드 댓글 달기
	public int feedCommentCreate(
			@Param("Feed_num") int Feed_num,
			@Param("Member_Name") String Member_Name,
			@Param("Member_profileimage") String Member_profileimage,
			@Param("Feed_comment") String Feed_comment
	);
	
	// 패드 댓글 불러오기
	public List<FeedDTO> feedCommentList();
	
	
}
