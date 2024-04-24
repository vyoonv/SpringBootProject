package edu.kh.project.board.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.project.board.model.dto.Board;

public interface BoardService {

	/** 게시판 종류 조회 
	 * @return boardTypeList 
	 */
	List<Map<String, Object>> selectBoardTypeList();

	/** 특정 게시판의 지정된 목록 조회 
	 * @param boardCode
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectBoardList(int boardCode, int cp);

	/** 게시글 상세조회 
	 * @param map
	 * @return board
	 */
	Board selectOne(Map<String, Integer> map);

	/** 게시글 좋아요 체크/해제 
	 * @param map
	 * @return result
	 */
	int boardLike(Map<String, Integer> map);

	/** 조회수 증가 
	 * @param boardNo
	 * @return
	 */
	int updateReadCount(int boardNo);


}
