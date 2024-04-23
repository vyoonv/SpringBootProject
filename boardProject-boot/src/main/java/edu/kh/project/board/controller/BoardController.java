package edu.kh.project.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.board.model.dto.boardImg;
import edu.kh.project.board.model.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("board")
@Controller
public class BoardController {
	
	private final BoardService service; 
	
	
	
	/** 게시글 목록 조회 
	 * @param boardCode : 게시판 종류 구분 번호 
	 * @return cp :
	 * 
	 * - /board/xxx 
	 * /board 이하 1레벨 자리에 숫자로 된 요청 주소가 작성되어있을 때만 동작 -> 정규 표현식 이용 
	 * 
	 * [0-9] : 한 칸에 0~9 사이 숫자 입력 가능 
	 * + : 하나 이상 
	 * [0-9]+ : 모든 숫자 
	 * 
	 */
	@GetMapping("{boardCode:[0-9]+}") 
	public String selectBoardList(@PathVariable("boardCode") int boardCode, 
								  @RequestParam(value="cp", required = false, defaultValue="1") int cp, 
								  Model model  ) {
		
		log.debug("boardCode : " + boardCode);
		
		// 조회 서비스 호출 후 결과 반환 
		Map<String, Object> map = service.selectBoardList(boardCode, cp); 
		
		model.addAttribute("pagination", map.get("pagination")); 
		model.addAttribute("boardList", map.get("boardList")); 
		
		return "board/boardList"; // boardList.html로 forward
		
	}
	
	
	
	// 상세 요청 주소 : board/1/1998?cp = 1
	
	
	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}")
	public String boardDetail(@PathVariable("boardCode") int boardCode, 
			 				  @PathVariable("boardNo") int boardNo, 
			 				  Model model, 
			 				  RedirectAttributes ra ) {
		
		// 게시글 상세 조회 서비스 호출 
		
		// 1) Map으로 전달할 파라미터 묶기 
		Map<String, Integer> map = new HashMap<>(); 
		map.put("boardCode", boardCode); 
		map.put("boardNo", boardNo); 
		
		// 2) 서비스 호출 
		Board  board = service.selectOne(map);
		
		String path = null; 
		
		// 조회 결과가 없는 경우 
		if(board == null) {
			path = "redirect:/board/" + boardCode;   //목록 재요청
			ra.addFlashAttribute("message", "게시글이 존재하지 않습니다"); 
		} else {
			// 조회 결과가 있는 경우 
			path = "board/boardDetail";  // boardDetaul.html로 forward
			
			// board - 게시글 일반 내용 + imageList + commentList 
			model.addAttribute("board", board); 
			
			// 조회된 이미지 목록(imageList)이 있을 경우
			if( !board.getImageList().isEmpty() ) {
				
				boardImg thumbnail = null; 
				
				// imageList의 0번 인덱스 == 가장 빠른 순서 (imgOrder)
				
				// 이미지 목록의 첫번째 행이 순서 0 == 썸네일인 경우 
				if(board.getImageList().get(0).getImgOrder() == 0) {
					
					thumbnail = board.getImageList().get(0); 
				}
				
				model.addAttribute("thumbnail", thumbnail); 
				model.addAttribute("start", thumbnail != null ? 1 : 0); 
				
					
			}
		}
		
		
		return path; 
		
	}
	
	
	
	
	
	
	
	
	
	

}
