package com.employee_management.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    //before advice for all methods in EmployeeController
    @Before("execution(* com.employee_management.controller.EmployeeController.*(..))")
    public void logBefore(){
        logger.info("Entering method in employee controller");
    }

    //After returning advice
    @AfterReturning("execution(* com.employee_management.controller.EmployeeController.*(..))")
    public void logAfterReturning() {
        logger.info("Method in EmployeeController executed successfully");
    }

    // After throwing advice
    @AfterThrowing(pointcut = "execution(* com.employee_management.controller.EmployeeController.*(..))", throwing = "ex")
    public void logAfterThrowing(Exception ex) {
        logger.error("An exception occurred: {}", ex.getMessage());
    }

    // Around advice to log execution time
    @Around("execution(* com.employee_management.controller.EmployeeController.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;

        logger.info(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return proceed;
    }
}
