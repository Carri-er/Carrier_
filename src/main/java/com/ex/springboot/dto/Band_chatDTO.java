package com.ex.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Band_chatDTO {
	private String band_chat_room;  // 채팅방 이름 (밴드 코드와 채팅을하는 두명의 id를 조합하여 데이터 기입)
	private String band_code; 		// 밴드 코드 : 어느 밴드에서 채팅을 하였는지.
	private String chat_Id;		// 채팅을한 사람의 ID
	private String chat_message;	// 채팅 메세지
	private String chat_time;		// 채팅을 친 시간
	private String Member_profileimage; // 채팅 친 사람의 프로필 이미지

}
