package com.library.online_library_spring_app.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class Logging {
    private static final Logger logger = LoggerFactory.getLogger(Logging.class);
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String rocket = "\uD83D\uDE80";
    private static final String error = "\u274C";

    @Pointcut("execution(* com.library.online_library_spring_app.service.*.*(..))")
    public void pointCutServices() {
    }

    @AfterThrowing(pointcut = "pointCutServices()", throwing = "exception")
    public void logAfterThrowing(Exception exception) {
        logger.error(error + ANSI_RED + "ERROR!!! " + exception.getMessage() + error);
    }

    @Before("pointCutServices()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info(rocket + ANSI_RED + "This method started :" + ANSI_RESET + "-->" + joinPoint.getSignature().getName());
    }

    @After("pointCutServices()")
    public void logAfter(JoinPoint joinPoint) {
        logger.info(ANSI_RED + " This method ended :" + ANSI_RESET + "-->" + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "pointCutServices()", returning = "result")
    public void logAfterReturning(Object result) {
        try {
            logger.info(ANSI_RED + "The result returned by the method : " + ANSI_RESET + "-->" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
