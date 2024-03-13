package com.ex.springboot.dao;

import java.util.List;   

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ex.springboot.dto.BandDTO;
import com.ex.springboot.dto.BandFeedDTO;
import com.ex.springboot.dto.Band_chatDTO;
import com.ex.springboot.dto.Band_commentDTO;
import com.ex.springboot.dto.Band_joinMemberDTO;
import com.ex.springboot.dto.MemberDTO;

@Mapper // 인터페이스를 xml로 구현하겠다.
public interface IBandDAO {
	
	//로그인한 멤버 정보 
	public MemberDTO bandLoginMemberDto(String member_id); 
	
	//전체 밴드 리스트 출력 
	public List<BandDTO> bandList();
	
	//밴드 피드 세부 보기
	public List<BandFeedDTO> bandFeedViewItem(int band_feed_num);
	
	//생성된 밴드 랜덤으로 6개 출력 
	public List<BandDTO> randomBandList();
	
	//생성된 밴드 랜덤으로 3개 출력
	public List<BandDTO> randomBandList_home();	
	
	//band_code를 가지고 myBand 페이지로 이동	
	public BandDTO myBand(int band_code);
	
	//밴드 명을 가지고 밴드 리스트 출력
	public BandDTO bandName_search(String band_name);
	
	//밴드 생성 추상메소드		
	public int bandCreateDao(
			@Param("band_name") String band_name, 
			@Param("band_admin") String band_admin, 
			@Param("band_content") String band_content,
			@Param("band_thumnail") String band_thumnail,
			@Param("band_theme") String band_theme,
			@Param("band_area") String band_area
			);
	
	//밴드 삭제하기
	public int bandInfoDelete(
			@Param("band_code") int band_code
			);
	
	//삭제한 밴드 joinmember 다 삭제하기 
	public int bandInfoDelete_member(
			@Param("band_code") int band_code
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
	public List<Band_joinMemberDTO> joinBandList(String member_id);
	
	
	//가입한 밴드 리스트 출력
	public List<Band_joinMemberDTO> mypage_joinBandList(String member_id);
		
	
	//밴드 가입하기
	public int bandJoinMember(int band_code, String membet_id);
	
	//가입한 밴드인지 확인
	public Band_joinMemberDTO checkJoinMember(int band_code, String membet_id);
	

		
	
	//밴드에 가입한 멤버 출력
	public List<Band_joinMemberDTO> joinMemberList(int band_code);
	
	//밴드 별 멤버 검색
	public List<Band_joinMemberDTO> searchBandjoinMemberList(int band_code, String member_id);
	
	//밴드 정보 수정
	public int bandInfoUpdate(
			@Param("band_thumnail") String band_thumnail,
			@Param("band_name") String band_name, 
			@Param("band_content") String band_content,
			@Param("band_code") int band_code, 
			@Param("band_theme") String band_theme,
			@Param("band_area") String band_area
			
			);
	
	//밴드 가입 시 멤버 수 증가 
	public int bandMembercount_plus(int band_code);
	
	//가입한 밴드 탈퇴하기 
	public int withdrawalJoinBand(
			@Param("band_code") int band_code,
			@Param("member_id") String member_id
			);
	
	//밴드 탈퇴 시 멤버 수 감소
	public int bandMembercount_minus(int band_code);
	
	//밴드 채팅 데이터 생성
	public int bandChatCreate(
			@Param("band_chat_room") String band_chat_room,
			@Param("band_code") int band_code,
			@Param("chat_myId") String chat_myId,
			@Param("chat_partnerId") String chat_partnerId,
			@Param("chat_message") String chat_message 
			);
	
	//밴드 채팅 리스트 출력
	public List<Band_chatDTO> bandChatList(
			@Param("band_chat_room") String band_chat_room
			);
	
	//밴드 채팅 
	public List<Band_chatDTO> bandChatRoomCheck(
				@Param("band_chat_room") String band_chat_room
			);
	
	//밴드 채팅 작성 
	public int userbandChatWrite(
			@Param("band_chat_room") String band_chat_room,
			@Param("band_code") int band_code,
			@Param("chat_Id") String chat_Id,
			@Param("chat_message") String chat_message
			
			);
	
	//밴드 피드별 댓글 수
	public int bandFeedCommentCount(int band_feed_num);
	
	//밴드 댓글 전체 리스트
	public List<Band_commentDTO> bandFeedCommentAll();
	
	//밴드 피드 별 댓글 리스트
	public List<Band_commentDTO> bandFeedCommentList(int band_feed_num);
	
	//밴드 피드 별 최신 댓글
	public List<Band_commentDTO> bandFeedCommentTop(int band_feed_num);
	
	public int band_feed_commentWrite(
				@Param("band_feed_num") int band_feed_num,
				@Param("Member_Id") String Member_Id,
				@Param("band_feedComment_content") String band_feedComment_content
			);
	
	//밴드 피드 댓글 삭제
	public int band_feedComment_del(int band_feedComment_num);
	
	//밴드 피드 댓글 수정
	public int band_fedComment_update(String band_feedComment_content, int band_feedComment_num);
	
	//본인 밴드를 뺀 밴드 리스트 출력 
	public List<BandDTO> band_updateList(int band_code);
	

}
