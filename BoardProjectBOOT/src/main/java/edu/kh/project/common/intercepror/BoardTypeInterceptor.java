package edu.kh.project.common.intercepror;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import edu.kh.project.board.model.service.BoardService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoardTypeInterceptor implements HandlerInterceptor{
	
	@Autowired
	private BoardService service; 

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// application scope 
		// 서버 종료시까지 유지되는 servlet  내장 객체
		// 서버 내 한개만 존재 -> 모든 클라이언트 공용 사용
		
		ServletContext application = request.getServletContext(); 
		
		if(application.getAttribute("boardTypeList") == null) {
			
			log.info("BoardTypeInterceptor - preHandle 전처리 동작 실행");
			
			// boardTypeList 조회 서비스 호출 
			List<Map<String, Object>> boardTypeList = service.selectBoardTypeList(); 
						
			// 조회결과를 application scope에 추가 
			application.setAttribute("boardTypeList", boardTypeList);
			
		}
		
		
		
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	
	
	
	
	
	
	
	
	

}
