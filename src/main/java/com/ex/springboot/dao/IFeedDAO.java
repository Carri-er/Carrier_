package com.ex.springboot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.springboot.dto.FeedDTO;
import com.ex.springboot.dto.Feed_LikeDTO;
import com.ex.springboot.dto.Feed_commentDTO;

@Mapper // 인터페이스를 xml로 구현하겠다.
public interface IFeedDAO {
	//피드 글쓰기
	public int feedWrite(
			@Param("Member_Id") String Member_Id, 
			@Param("Feed_title") String Feed_title,
			@Param("Feed_content") String Feed_content,
			@Param("Feed_theme") String Feed_theme,
			@Param("Feed_area") String Feed_area,
			@Param("Feed_thumbnail") String Feed_thumbnail
	);

	//피드 전체 페이지
	public List<FeedDTO> feedList();

	//피드 전체 페이지 - login 했을 때
	public List<FeedDTO> feedList_login(
			@Param("Member_Id") String Member_Id
	);
	
	//피드 전체 페이지 - 홈에서 보일 피드
	public List<FeedDTO> feedList_random();

	
	//피드 전체 페이지 - filter
	public List<FeedDTO> feedList_theme(
			@Param("Feed_theme") String Feed_theme
	);
	
	//피드 전체 페이지 - filter - login 시 좋아요 개수 가져오기
	public List<FeedDTO> feedList_theme_login(
			@Param("Member_Id") String Member_Id,
			@Param("Feed_theme") String Feed_theme
	);
	
	//피드 전체 페이지 - filter
	public List<FeedDTO> feedList_area(
			@Param("Feed_area") String Feed_area
	);
	
	//피드 전체 페이지 - filter - login 시 좋아요 개수 가져오기
	public List<FeedDTO> feedList_area_login(
			@Param("Member_Id") String Member_Id,
			@Param("Feed_area") String Feed_area
	);
	
	//피드 세부 페이지
	public FeedDTO feedShow(
			@Param("Feed_num") int Feed_num
	);
	
	//피드 세부 페이지 - login 시 좋아요 개수 가져오기
	public FeedDTO feedShow_login(
			@Param("Member_Id") String Member_Id,
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
	
	
	public List<FeedDTO> feedList_mypage(
			@Param("Member_Id") String Member_Id
			);
	
	
	/////////////////////// 좋아요 /////////////////////// 
	
	// 피드 좋아요
	public int feedLike(
			@Param("Feed_num") int Feed_num,
			@Param("Member_Id") String Member_Id
	);
	
	// 피드 좋아요 취소
	public int feedHate(
			@Param("Feed_num") int Feed_num,
			@Param("Member_Id") String Member_Id
			);
	
	
	// 좋아요 count 개수 ++
	public int feedLikeCount(
			@Param("Feed_num") int Feed_num,
			@Param("count") int count
	);
	
	//////////////////////////// 피드 댓글 /////////////////////////////

	// 피드 댓글 달기
	public int feedCommentCreate(
			@Param("Feed_num") int Feed_num,
			@Param("Member_Id") String Member_Id,
			@Param("Feed_comment") String Feed_comment
	);
	
	// 피드 댓글 불러오기
	public List<Feed_commentDTO> feedCommentList(
			@Param("Feed_num") int Feed_num
	);
	
	// 피드 댓글 수정
	public int feedCommentUpdate(
			@Param("Feed_comment") String Feed_comment,
			@Param("Feed_num") int Feed_num,
			@Param("Feed_comment_num") int Feed_comment_num
	);
	
	// 댓글 삭제 
	public int feedCommentDel(
			@Param("Feed_num") int Feed_num,
			@Param("Feed_comment_num") int Feed_comment_num
	);

	// 피드 댓글 좋아요
	public int feedCommentLike(
			@Param("Feed_comment_like") int Feed_comment_like,
			@Param("Feed_num") int Feed_num,
			@Param("Feed_comment_num") int Feed_comment_num
	);

}



