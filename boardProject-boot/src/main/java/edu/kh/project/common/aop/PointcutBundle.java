package edu.kh.project.common.aop;

import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

// Pointcut 모아두는 클래스 
// Pointcut : 실제 Advice가 적용될 지점 


public class PointcutBundle {

	// 작성하기 어려운 Pointcut을 미리 작성해놓고 
	// 필요한 곳에서 클래스명.메서드명()으로 호출해서 사용 가능 
	
	//  ex) @Before("execution(* edu.kh.project..*Controller*.*(..))")
	// == @Before("PointcutBundle.controllerPointcut()")
	
	@Pointcut("execution(* edu.kh.project..*Controller*.*(..))")
	public void controllerPointcut(){}
	
	@Pointcut("execution(* edu.kh.project..*ServiceImpl*.*(..))")
	public void serviceImplPointcut() {}
	
}
