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
public class boardImg {
	
	
	private int imgNo; 
	private String imgPath; 
	private String imgOrignalName; 
	private String imgRename; 
	private int imgOrder; 
	private int boardNo; 
	
	// 게시글 이미지 삽입/ 수정 때 사용 
	private MultipartFile uploadFile; 
	

}
