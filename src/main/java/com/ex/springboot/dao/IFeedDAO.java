package com.ex.springboot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ex.springboot.dto.FeedDTO;

@Mapper // 인터페이스를 xml로 구현하겠다.
public interface IFeedDAO {
	
	//전체 피드 리스트 출력 
	public List<FeedDTO> feedList();
	/*
	 * //Feed_num를 가지고 Feed 상세 페이지로 이동 public BandDTO myBand(int Feed_num);
	 * 
	 * //작성자 feed 리스트 출력 public List<FeedDTO> bandFeedList(String Member_Id);
	 * 
	 * public MemberDTO bandWriterImg(@Param("Member_id") String Member_id);
	 * 
	 * // 피드 업데이트 public int FeedUpdate(
	 * 
	 * @Param("Feed_content") String Feed_content,
	 * 
	 * @Param("member_id") String member_id,
	 * 
	 * @Param("Feed_num") int Feed_num );
	 * 
	 * // 피드 삭제 public int FeedDelete(
	 * 
	 * @Param("Feed_num") int Feed_num );
	 * 
	 * //좋아하는 멤버 리스트 출력 public List<Band_joinMemberDTO> joinBandList(String
	 * member_id);
	 * 
	 * //좋아요 public int feedHeartCount(int Feed_heart);
	 */
	
}
