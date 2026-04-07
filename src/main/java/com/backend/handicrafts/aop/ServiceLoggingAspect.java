package com.backend.handicrafts.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ServiceLoggingAspect {

    @Before("execution(* com.backend.handicrafts.service..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.debug("Entering {}", joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "execution(* com.backend.handicrafts.service..*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.debug("Completed {} with result type {}",
                joinPoint.getSignature().toShortString(),
                result == null ? "void" : result.getClass().getSimpleName());
    }
}