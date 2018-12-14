package xin.yangshuai.spring.aop.xml;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * ValidationAspect
 *
 * @author shuai
 * @date 2018/12/14
 */
public class ValidationAspect {

	@Before("xin.yangshuai.spring.aop.LoggingAspect.declareJointPointExpression()")
	public void validateArgs(JoinPoint joinPoint) {
		System.out.println("validate : " + Arrays.asList(joinPoint.getArgs()));
	}
}
