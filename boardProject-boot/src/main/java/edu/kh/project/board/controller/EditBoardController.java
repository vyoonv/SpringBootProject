package edu.kh.project.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.board.model.exception.ImageUpdateException;
import edu.kh.project.board.model.service.BoardService;
import edu.kh.project.board.model.service.EditBoardService;
import edu.kh.project.member.model.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequestMapping("editBoard")
@RequiredArgsConstructor
@Controller
public class EditBoardController {

	private final EditBoardService service; 
	private final BoardService boardService; 
	
	
	
	/** 게시글 작성 화면으로 전환 
	 * @param BoardCode
	 * @return board/boardWrite
	 */
	@GetMapping("{boardCode:[0-9]+}/insert")
	public String boardInsert( @PathVariable("boardCode") int BoardCode  ) {
			
		return "board/boardWrite";    // templates/board/boardWrite.html 로 forward 
	}
	
	/** 게시글 작성 
	 * @param BoardCode : 어떤 게시판에 작성할 글인지 구분 
	 * @param inputBoard : 입력된 값(제목, 내용) 세팅되어있음 (커맨드 객체) 
	 * @param loginMember : 로그인한 회원 번호를 얻어오는 용도
	 * @param images : 제출된 file 타입 input 태그 데이터들 (이미지 파일...)
	 * @param ra : 리다이렉트시 request scope 로 데이터 전달  
	 * @return 
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@PostMapping("{boardCode:[0-9]+}/insert")
	public String boardInsert( @PathVariable("boardCode") int boardCode, 
							   @ModelAttribute Board inputBoard, 
							   @SessionAttribute("loginMember") Member loginMember, 
							   @RequestParam("images") List<MultipartFile> images, 
							   RedirectAttributes ra ) throws IllegalStateException, IOException {
		
		/* List<MultipartFile> images 
		 *  - 5개 모두 업로드 => 0~4번 인덱스에 파일 저장 
		 *  - 5개 모두 업로드 X => 0~4번 인덱스에 파일 저장 X
		 *  - 2번 인덱스만 업로드 => 2번 인덱스만 파일 저장, 0/1/3/4번 인덱스 저장 X
		 *  
		 *  [문제점]
		 *  - 파일이 선택되지 않은 input 태그도 제출되고 있음 
		 *  (제출은 되어있는데 데이터는 없음 )
		 *  -> 파일 선택이 안된 input 태그 값을 서버에 저장하려고 하면 오류 발생할 것 
		 *  
		 *  [해결방법]
		 *  - 서버에 저장하면 안되고 
		 *  - 제출된 파일이 있는지 확인하는 로직 추가 구성 
		 * 
		 *  + List 요소의 index 번호 == IMG_ORDER 와 같음
		 * */ 
		
		// 1. boardCode, 로그인한 회원 번호를 inputBoard에 세팅 
		inputBoard.setBoardCode(boardCode);
		inputBoard.setMemberNo(loginMember.getMemberNo());
		
		// 2. 서비스 메서드 호출 후 결과 반환 받기 
		// -> 성공 시 [상세 조회]를 요청할 수 있도록 
		// 삽입된 게시글 번호를 반환 받기 
		
		int boardNo = service.boardInsert(inputBoard, images); 
		
		// 3. 서비스 수행 결과에 따라 message, 리다이렉트 경로 저장 
		String path = null; 
		String message = null;
		
		if(boardNo > 0) {
			
			path = "/board/" + boardCode + "/" + boardNo; // 상세 조회 
			message = "게시글이 작성되었습니다"; 
			
		} else {
			
			path = "insert"; 
			message = "게시글 작성 실패"; 
			
		}
		
		ra.addFlashAttribute("message", message); 
		
		return "redirect:" + path; 
	

	}
	
	
	/** 게시글 수정 화면 전환 
	 * @param boardCode : 게시판 종류 
	 * @param boardNo : 게시글 번호 
	 * @param loginMember : 로그인한 회원이 작성한 글이 맞는지 검사하는 용도 
	 * @param model : 포워드시 request scope 로 값 전달하는 용도 
	 * @param ra : 리다이렉트시 request scope 로 값 전달하는 용도 
	 * @return
	 */
	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}/update")  // 쿼리스트링 앞 주소까지만 매핑 
	public String boardUpdate(@PathVariable("boardCode") int boardCode, 
							  @PathVariable("boardNo") int boardNo, 
							  @SessionAttribute("loginMember") Member loginMember, 
							  Model model, RedirectAttributes ra  
																	) {
		
		// 수정 화면에 출력할 기존의 제목/내용/이미 조회 
		// -> 게시글 상세 조회 
		
		Map<String, Integer> map = new HashMap<>(); 
		map.put("boardCode", boardCode); 
		map.put("boardNo", boardNo); 
		
		// BoardService.selectOne(map)호출 
		Board board = boardService.selectOne(map); 
		
		String message = null; 
		String path = null; 
		
		if(board == null) {
			message = "해당 게시글이 존재하지 않습니다"; 
			path = "redirect:";   // 메인페이지
			  
			ra.addFlashAttribute("message", message); 
			
		} else if(board.getMemberNo() != loginMember.getMemberNo()) {
			
			message = "자신이 작성한 글만 수정할 수 있습니다"; 
			
			// 해당 글 상세조회 리다이렉트 "redirect:/board/1/2001"; 
			path = String.format("redirect:/board/%d/%d", boardCode, boardNo); 
			
			ra.addFlashAttribute("message", message); 
		
		} else {
			
			path = "board/boardUpdate";   // templates/board/boardUpdate.html로 forward 
			model.addAttribute("board", board); 
		}
	
		return path; 
	}
	
	
	// 게시글 수정 
	/** 게시글 수정 
	 * @param boardCode : 게시판 종류 
	 * @param boardNo : 수정할 게시글 번호 
	 * @param inputBoard : 커맨드 객체 (제목, 내용) 
	 * @param loginMember : 로그인한 회원 번호 이용 (로그인 == 작성자1)
	 * @param images : 제출된 input type = "file" 모든 요소 
	 * @param ra : redirect시 request scope 값 전달 
	 * @param deleteOrder : 삭제된 이미지 순서가 기록된 문자열 (1,2,3) 
	 * @param queryString : 수정 성공시 이전 파라미터 유지 (cp)
	 * @return 
	 * @throws ImageUpdateException 
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@PostMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}/update")
	public String boardUpdate(	@PathVariable("boardCode") int boardCode, 
							@PathVariable("boardNo") int boardNo, 
							@ModelAttribute Board inputBoard, 
							@SessionAttribute("loginMember") Member loginMember, 
							@RequestParam("images") List<MultipartFile> images,
							RedirectAttributes ra, 
							@RequestParam(value="deleteOrder", required = false) String deleteOrder, 
							@RequestParam(value="queryString", required=false, defaultValue = "") String queryString) throws IllegalStateException, IOException, ImageUpdateException {
		
		// 1. 커맨드 객체 (inputBoard)에 boardCode, boardNo, memberNo 세팅 
		inputBoard.setBoardCode(boardCode);
		inputBoard.setBoardNo(boardNo);
		inputBoard.setMemberNo(loginMember.getMemberNo());
		// ->inputBoard (제목, 내용, boardCode, boardNo, memberNo) 
		
		// 2. 게시글 수정 서비스 호출 후 결과 반환 받기
		
		int result = service.boardUpdate(inputBoard, images, deleteOrder); 
		
		String message = null; 
		String path = null; 
		
		if(result > 0) {
			
			 message = "게시글이 수정되었습니다"; 
			 path = String.format("/board/%d/%d%s", boardCode, boardNo, queryString);  // /board/1/2000?cp=3 
			
		} else {
			
			message = "수정 실패"; 
			path = "update";  // 수정 화면 전환 리다이렉트 하는 상대경로 
			
		}
		
		ra.addFlashAttribute("message", message); 
		
		return "redirect:" + path; 
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
}
