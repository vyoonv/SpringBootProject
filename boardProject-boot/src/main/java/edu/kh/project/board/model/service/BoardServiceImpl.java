package edu.kh.project.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.board.model.dto.Pagination;
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
		Pagination pagination = new Pagination(cp, listCount); 
		
		
		// 3. 특정 게시판의 지정된 페이지 목록 조회
		/* ROWBOUNDS 객체 (Mybatis 제공 객체) 
		 * - 지정된 크기(offset) 만큼 건너뛰고 (offset) 
		 * 	 제한된 크기(limit) 만큼의 행을 조회하는 객체
		 * 
		 *  --> 페이징 처리가 간단해짐!
		 * */
		int limit = pagination.getLimit(); 
		int offset = (cp - 1) * limit; 
		RowBounds rowBounds = new RowBounds(offset, limit); 
		
		
		/* Mapper 메서드 호출시 
		 * - 첫 번째 매개변수 -> sql에 전달할 파라미터 값 
		 * - 두 번째 매개변수 -> RowBounds 객체만 전달 가능 
		 * 
		 * */
		List<Board> boardList = mapper.selectBoardList(boardCode, rowBounds); 
		
				
		// 4. 목록 조회 결과 + Pagination 객체를 Map으로 묶음 
		Map<String, Object> map = new HashMap<>(); 
		
		map.put("pagination", pagination); 
		map.put("boardList", boardList); 
		
		
		// 5. 결과 반환 
		return map;
		
		// 반환형은 1개만 가능하기 때문에 묶어두고 controller에서는 풀어서 사용 
	}



	// 게시글 상세 조회 
	@Override
	public Board selectOne(Map<String, Integer> map) {
		
		// 여러 SQL을 실행하는 방법 
		// 1. 하나의 service 메서드에서 여러 mapper메서드를 호출하는 방법 
		
		// 2. 수행하려는 SQL이
		// 	 1) 모두 SELECT이면서 
		// 	 2) 먼저 조회된 결과 중 일부를 이용해서 
		// 		나중에 수행되는 SQL의 조건으로 삼을 수 있을 때 
		// --> MyBatis의 <resultMap>, <collection> 태그 이용해서 
		// Mapper 메서드 1회 호출로 여러 SELECT 한 번에 수행 가능 
		
		
		return mapper.selectOne(map);
	} 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
