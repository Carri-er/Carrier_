package com.ex.springboot.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Feed_commentDTO {
	private int Feed_num; //피드 번호
	private int Feed_comment_num; //피드 댓글의 번호
	private String Member_Id; // 댓글단 id
	private String Member_profileimage; // 프로필이미지
	
	@NotNull(message = "빈칸이나 공백으로 이루어진 댓글은 작성되지 않습니다")
	@Size(max = 65, message="65자 이내로 작성해주세요")
	private String Feed_comment; // 댓글
	
	private int Feed_comment_like; // 댓글 좋아요
	private String Feed_comment_updateday; // 댓글
}
