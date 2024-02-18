package com.ex.springboot.dao;

import java.util.List; 

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ex.springboot.dto.BandDTO;
import com.ex.springboot.dto.BandFeedDTO;
import com.ex.springboot.dto.Band_joinMemberDTO;
import com.ex.springboot.dto.MemberDTO;

@Mapper // 인터페이스를 xml로 구현하겠다.
public interface IBandDAO {
	
	//전체 밴드 리스트 출력 
	public List<BandDTO> bandList();
	
	//band_code를 가지고 myBand 페이지로 이동	
	public BandDTO myBand(String band_code);
	
	//밴드 생성 추상메소드		
	public int bandCreateDao(
			@Param("band_name") String band_name, 
			@Param("band_admin") String band_admin, 
			@Param("band_content") String band_content,
			@Param("band_thumnail") String band_thumnail
			);
	
	//밴드 피드 저장 추상 메소드
	public int bandFeedWrite(
			@Param("band_code") int band_code, 
			@Param("Member_id") String Member_id, 
			@Param("band_feed_content") String band_feed_content
			);
	
	//밴드별 feed 리스트 출력
	public List<BandFeedDTO> bandFeedList(String band_code); 
	
	public MemberDTO bandWriterImg(@Param("Member_id") String Member_id);
	
	public int bandFeedUpdate(
			@Param("band_feed_content") String band_feed_content,
			@Param("member_id") String member_id,
			@Param("band_code") int band_code,
			@Param("band_feed_num") int band_feed_num
			);
	
	public int bandFeedDelete(
				@Param("band_code") int band_code,
				@Param("band_feed_num") int band_feed_num
			);
	
	//가입한 밴드 리스트 출력
	public List<Band_joinMemberDTO> joinBandList(String member_id, String member_id2);
	
	//밴드 가입하기
	public int bandJoinMember(int band_code, String membet_id);
	
	//가입한 밴드인지 확인
	public Band_joinMemberDTO checkJoinMember(int band_code, String membet_id);
	
}
