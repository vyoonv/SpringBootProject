package edu.kh.project.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.board.model.dto.BoardImg;

@Mapper
public interface EditBoardMapper {

	/** 게시글 작성 
	 * @param inputBoard
	 * @return result
	 */
	int boardInsert(Board inputBoard);

	/** 게시글 이미지 모두 삽입 
	 * @param uploadList
	 * @return result
	 */
	int insertUploadList(List<BoardImg> uploadList);

	/** 게시글 이미지 수정 
	 * @param img
	 * @return result
	 */
	int updateImage(BoardImg img);

	/** 게시글 이미지 삽입(1행) 
	 * @param img
	 * @return result
	 */
	int insertImage(BoardImg img);

	/** 게시글 부분 수정 (제목/내용) 
	 * @param inputBoard
	 * @return
	 */
	int boardUpdate(Board inputBoard);

	/** 게시글 이미지 삭제 
	 * @param map
	 * @return
	 */
	int deleteImage(Map<String, Object> map);

}
