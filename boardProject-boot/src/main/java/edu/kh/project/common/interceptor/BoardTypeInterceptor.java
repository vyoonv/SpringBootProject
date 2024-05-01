package edu.kh.project.common.interceptor;

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



/* Interceptor : 요청/응답 가로채는 객체 (Spring 지원) 
 * 
 * Client <-> Filter <-> Dispatcher Servlet <-> Interceptor <-> Controller
 * 
 *  HandlerInterceptor 인터페이스를 상속받아 구현해야 함 
 * 
 * - preHandle (전처리) : Dispatcher Servlet -> Controller 사이 수행 
 * 
 * - postHandle (후처리) : Controller -> Dispatcher Servlet 사이 수행 
 * 
 * - afterCompletion (view 완성 후:forward 코드 해석 후) : View Resolver -> Dispatcher Servlet 사이 수행 
 *   
 * */


@Slf4j
public class BoardTypeInterceptor implements HandlerInterceptor {
	
	
	@Autowired
	private BoardService service; 
	// -> requiredArgsConstructor 사용 x 
	// -> interceptor 설정해주는 config 생성해서 사용해야 하는데 여기서 기본 생성자 사용해야 해서 
	// -> requiredArgsConstructor 는 매개변수 생성자만 사용 가능해서 
	
	
	
	

	// 전처리 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// application scope : 서버 종료시까지 유지되는 Servlet 내장 객체 
		// -> 서버 내 only 1개 존재
		// -> 모든 클라이언트가 공용으로 사용 
		
		// application scope 객체 얻어오기 
		ServletContext application = request.getServletContext();
		
		// application scope에 boardTypeList가 없을 경우 
		if(application.getAttribute("boardTypeList") == null) {
			
			log.info("boardTypeInterceptor - preHandle(전처리)동작 실행");
			
			// boardTypeList 조회 서비스 호출 
			List<Map<String, Object>> boardTypeList = service.selectBoardTypeList(); 
			// map을 list의 요소로 -> list 안에 인덱스 안에 map 형태로 존재 
			
			// 조회 결과를 application scope에 추가 
			application.setAttribute("boardTypeList", boardTypeList);
					
			
		}
		
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	

}
