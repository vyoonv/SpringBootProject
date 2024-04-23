package edu.kh.project.board.model.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class Comment {

	private int commentNo; 
	private String commentContent; 
	private String commentWriteDate; 
	private String commentDelFl; 
	private int boardNo; 
	private int memberNo; 
	private int parentCommentNo; 
	
	// 댓글 조회시 회원 프로필, 닉네임 
	private String memberNickname; 
	private String profileImg; 
	
	
}
