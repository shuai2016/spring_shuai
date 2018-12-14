package xin.yangshuai.spring.aop.xml;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * LoggingAspect
 *
 * @author shuai
 * @date 2018/12/14
 */
public class LoggingAspect {

	@Pointcut("execution(* xin.yangshuai.spring.aop.*.*(..))")
	public void declareJointPointExpression(){}

	@Before("declareJointPointExpression()")
	public void beforeMethod(JoinPoint joinPoint){
		String methodName = joinPoint.getSignature().getName();
		List<Object> args = Arrays.asList(joinPoint.getArgs());
		System.out.println("The method "+ methodName +" begins with " + args);
	}

	@After("declareJointPointExpression()")
	public void afterMethod(JoinPoint joinPoint){
		String methodName = joinPoint.getSignature().getName();
		System.out.println("The method "+ methodName +" ends");
	}
}
