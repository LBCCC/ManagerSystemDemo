package com.manager.system.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Aspect
@Slf4j
public class ValidAspect {
    @Around("execution(* com.manager.system.controller.ManagerController.*(..)) && args(..,bindingResult)")
    public Object validateParam(ProceedingJoinPoint joinPoint, BindingResult bindingResult) throws Throwable {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            log.error(errors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining()));
            throw new RuntimeException("invalid param");
        }
        return joinPoint.proceed();
    }
}
