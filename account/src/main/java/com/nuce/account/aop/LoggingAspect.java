package com.nuce.account.aop;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;

@Aspect
@Slf4j
public class LoggingAspect {


    @Autowired
    private ObjectMapper objectMapper;

    @Pointcut("within(com.nuce.account.resource.*)")
    public void pointcutResource() {
        // Method is empty as this is just a Pointcut,
        // the implementations are in the advices.
    }

    @Before("pointcutResource()")
    public void logBeforeResource(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RequestMapping mapping = signature.getMethod().getAnnotation(RequestMapping.class);
        log.info("--------------------");
        log.info("=BeforeResource= path: {}, annotation: {}, method: {}(),",
                mapping.path(),
                mapping.method(),
                signature.getName()
        );
    }

    @AfterReturning(pointcut = "pointcutResource()", returning = "entity")
    public void logAfterResource(JoinPoint joinPoint, ResponseEntity<?> entity) throws JsonProcessingException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RequestMapping mapping = signature.getMethod().getAnnotation(RequestMapping.class);
        log.info("=AfterResource= path: {}, returning: {}",
                mapping.path(),
                objectMapper.writeValueAsString(entity)
        );
    }

    @AfterThrowing(pointcut = "pointcutResource()", throwing = "exception")
    public void logException(Exception exception){
         if (exception.getMessage() != null )
            log.error("=FailResource= message system: {}", exception.getMessage());
         else log.error("=FailResource= message system: {}", exception.toString());
    }

    @Around("@annotation(com.nuce.account.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object proceed = joinPoint.proceed();
        stopWatch.stop();
        log.info("=TimeResource= Method: {}() executed in {} ms",
                joinPoint.getSignature().getName(),
                stopWatch.getTotalTimeMillis());
        return proceed;
    }
}
