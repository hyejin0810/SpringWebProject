package kr.or.com.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect // 간접적 부름 해당하는 클래스 POINTCUT + 메소드를 합친다
@Log4j
@Component
public class LogAdvice {

	@Before("execution(* kr.or.com.service.SampleService*.*(..))")
	public void logBefore() {
		log.info("===============");
	}

	@AfterThrowing(pointcut = "execution(* kr.or.com.service.SampleService*.*(..))", throwing = "exception")
	public void logException(Exception exception) {
		log.info("Exception...!!!");
		log.info("exception:" + exception);
	}

	@Around("execution(* kr.or.com.service.SampleService*.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.currentTimeMillis();
		log.info("Target:" + pjp.getTarget());
		log.info("Param:" + Arrays.toString(pjp.getArgs()));
		Object result = null;

		result = pjp.proceed();

		long end = System.currentTimeMillis();
		log.info("Time:" + (end - start));
		return result;
	}

	@Before("execution(* kr.or.com.service.SampleService*.*(..)) && args(str1,str2)")
	public void logBeforeWithParam(String str1, String str2) {
		log.info("str1 : " + str1);
		log.info("str2 : " + str2);

	}

	@AfterReturning(value = "execution(* kr.or.com.service.SampleService*.*(..))", returning = "returnValue")
	public void writeSuccessLog(JoinPoint joinPoint, Object returnValue) throws RuntimeException {
		// logging
		// returnValue 는 해당 메서드의 리턴객체를 그대로 가져올 수 있다.
		log.info("정상작동");
	}

}
