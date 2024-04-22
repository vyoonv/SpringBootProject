package edu.kh.project.board.model.dto;

/* Pagination 뜻 : 목록을 일정 페이지로 분할해서 
 * 				   원하는 페이지를 볼 수 있게 하는 것
 * 				   == 페이징 처리  
 * 
 * Pagination 객체 : 페이징 처리에 필요한 값을 모아두고, 계산하는 객체 
 * */
public class Pagination {
	
	private int currentPage;		// 현재 페이지 번호
	private int listCount;			// 전체 게시글 수
	
	private int limit = 10;			// 한 페이지 목록에 보여지는 게시글 수
	private int pageSize = 10;		// 보여질 페이지 번호 개수
	
	private int maxPage;			// 마지막 페이지 번호
	private int startPage;			// 보여지는 맨 앞 페이지 번호
	private int endPage;			// 보여지는 맨 뒤 페이지 번호
	
	private int prevPage;			// 이전 페이지 모음의 마지막 번호
	private int nextPage;			// 다음 페이지 모음의 시작 번호


}
