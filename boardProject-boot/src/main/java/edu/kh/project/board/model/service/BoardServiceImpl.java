package edu.kh.project.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.board.model.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Transactional
@Service
public class BoardServiceImpl implements BoardService{

	
	private final BoardMapper mapper;

	
	
	/**
	 * 게시판 종류 조회 
	 */
	@Override
	public List<Map<String, Object>> selectBoardTypeList() {
		
		return mapper.selectBoardTypeList();
	}



	/**
	 * 특정 게시판의 지정된 페이지 목록 조회 
	 */
	@Override
	public Map<String, Object> selectBoardList(int boardCode, int cp) {
		
		// 1. 지정된 게시판(boardCode) 에서 삭제되지 않은 게시글 수 조회
		int listCount = mapper.getListCount(boardCode); 
				
		// 2. 1번의 결과 + cp(현재페이지)를 이용해서 Pagination 객체를 생성 
		// Pagination : 게시글 목록 구성에 필요한 값을 저장한 객체 
		
		
		// 3. 특정 게시판의 지정된 페이지 목록 조회 
		
		
		// 4. 목록 조회 결과 + Pagination 객체를 Map으로 묶음 
		
		
		// 5. 결과 반환 
		
		
		return null;
	} 
	
	
	
	
	
}
