package ru.itis.javalab.trello.impl.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("within(ru.itis.javalab.trello.impl.services.UserServiceImpl)")
    public void logMethod(){}

    @Before("logMethod()")
    public void logMethodBefore(JoinPoint joinPoint) {
        log.info("Method start " + joinPoint.getSignature());
    }

    @Around("@annotation(Logging)")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("Passed parameters: " + Arrays.toString(proceedingJoinPoint.getArgs()));
        Object object = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        log.info("Method end");
        return object;
    }
}
