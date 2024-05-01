package edu.kh.project.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.board.model.dto.Comment;

@Mapper
public interface CommentMapper {

	/** 댓글 목록 조회 
	 * @param boardNo
	 * @return commentList 
	 */
	List<Comment> select(int boardNo);

	/** 댓글 등록 
	 * @param comment
	 * @return
	 */
	int insert(Comment comment);

}
