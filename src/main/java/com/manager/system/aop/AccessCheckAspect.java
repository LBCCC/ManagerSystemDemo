package com.manager.system.aop;

import com.manager.system.constant.HeaderConstants;
import com.manager.system.dto.RoleInfo;
import com.manager.system.exception.AccessDenyException;
import com.manager.system.exception.BusinessException;
import com.manager.system.exception.ErrorCode;
import com.manager.system.util.JsonUtils;
import com.manager.system.util.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

@Component
@Aspect
@Slf4j
@Order(2)
public class AccessCheckAspect {


    @Around("@annotation(accessCheck)")
    public Object accessCheck(ProceedingJoinPoint joinPoint, AccessCheck accessCheck) throws Throwable {
        RoleInfo roleInfo = getAuthInfo();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String logTag = className + "#" + methodName;
        log.info("user {} try to access {}", JsonUtils.toJson(roleInfo), logTag);
        if (roleInfo == null) {
            throw new BusinessException(ErrorCode.INVALID_AUTH_INFO);
        }

        if (Arrays.stream(accessCheck.roles()).noneMatch(role -> Objects.equals(role, roleInfo.getRole()))) {
            throw new AccessDenyException();
        }

        return joinPoint.proceed();
    }

    public RoleInfo getAuthInfo() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String headerValue = request.getHeader(HeaderConstants.ROLE_INFO);
            return UserUtils.extractAuthInfo(headerValue);
        }
        return null;
    }

}
