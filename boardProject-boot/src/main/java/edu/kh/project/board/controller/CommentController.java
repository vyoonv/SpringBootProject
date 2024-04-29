package edu.kh.project.board.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.kh.project.board.model.dto.Comment;
import edu.kh.project.board.model.service.CommentService;
import lombok.RequiredArgsConstructor;

/* @Controller : 컨트롤러임을 명시하면서 Bean으로 등록 
 *
 * 받을 요청이 fetch를 통한 비동기 요청 
 * comment 요청이 오면 해당 컨트롤러에서 잡아 처리 
 * 
 * -> @ResponseBody를 매번 메서드에 추가했었음 (응답 본문으로 데이터 자체를 반환) 
 * 
 * @RestController : REST API 구축을 위해 사용하는 컨트롤러 
 * 
 * ==> @Controller + @Responsebody -> 모든 응답을 응답 본문(ajax)으로 반환하는 컨트롤러 
 * 
 * */

@RequiredArgsConstructor
@RequestMapping("comment")
@RestController
public class CommentController {

	private final CommentService service; 
	
	
	@GetMapping("")  // 쿼리스트링은 빼고 
	public List<Comment> select(@RequestParam("boardNo") int boardNo) {
		
		// HttpMessageConverter가 
		// List -> JSON (문자열)로 변환해서 응답 
		
		return service.select(boardNo); 
		
		
	}
	
	
	@PostMapping("")
	public int insert(@RequestBody Comment comment) {
		
		return service.insert(comment); 
		
	}
	
	
}
