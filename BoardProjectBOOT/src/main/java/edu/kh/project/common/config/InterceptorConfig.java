package edu.kh.project.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import edu.kh.project.common.intercepror.BoardTypeInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{  // 스프링 관련해서 각종 오버라이딩 기능 수행할 수 있게 해줌 

	//인터셉터 클래스 bean으로 등록
	@Bean      // 개발자가 만들어서 반환하는 객체를 bean으로 등록, 관리는 spring container가 수행 
	public BoardTypeInterceptor boardTypeInterceptor() {
		return new BoardTypeInterceptor(); 
	}
	
	@Override // WebMvcConfigurer 추가해줘야 사용 가능 
	public void addInterceptors(InterceptorRegistry registry) {
	// 동작할 인터셉터 객체를 추가하는 메서드 
		
		// Bean으로 등록된 BoardTypeInterceptor 얻어와서 매개변수로 저장 
		registry.addInterceptor( boardTypeInterceptor() )
		.addPathPatterns("/**")
		.excludePathPatterns("/css/**", "/js/**", "/images/**", "/favicon.ico"); 
		
	}
	
	
	
	
}
