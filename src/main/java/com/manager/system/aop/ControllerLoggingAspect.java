package com.manager.system.aop;

import com.manager.system.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
@Order(1)
public class ControllerLoggingAspect {

    @Around("execution(* com.manager.system.controller.*.*(..))")
    public Object logControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String logTag = className + "#" + methodName;
        Object result = null;
        try {
            result = joinPoint.proceed();
            log.error("{} success, param: {}, result: {}", logTag, Arrays.toString(joinPoint.getArgs()), JsonUtils.toJson(result));
        } catch (Exception e) {
            log.error("{} error, param: {}", logTag, Arrays.toString(joinPoint.getArgs()), e);
            throw e;
        }
        return result;

    }
}