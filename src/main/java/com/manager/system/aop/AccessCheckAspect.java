package com.manager.system.aop;

import com.manager.system.dto.AuthInfo;
import com.manager.system.dto.UserInfo;
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

import java.util.Base64;
import java.util.Objects;

@Component
@Aspect
@Slf4j
@Order(2)
public class AccessCheckAspect {


    @Around("@annotation(accessCheck)")
    public Object around(ProceedingJoinPoint joinPoint, AccessCheck accessCheck) throws Throwable {
        AuthInfo authInfo = getAuthInfo();
        log.info("check authInfo: {}", JsonUtils.toJson(authInfo));
        if (authInfo == null) {
            throw new BusinessException(ErrorCode.INVALID_AUTH_INFO);
        }
        String userId = authInfo.getUserId();
        UserInfo user = UserUtils.getUser(userId);
        log.info("check user: {}", JsonUtils.toJson(user));
        if (user == null) {
            throw new BusinessException(ErrorCode.INVALID_AUTH_INFO);
        }

        if ("admin".equals(user.getRole())) {
            // admin have all access
        } else {
            if ("admin".equals(accessCheck.role())) {
                throw new AccessDenyException();
            }
            if (!user.getEndpoint().contains(accessCheck.endpoint())) {
                throw new AccessDenyException();
            }
        }

        return joinPoint.proceed();
    }

    public AuthInfo getAuthInfo() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String headerValue = request.getHeader("authInfo");
                if (Objects.isNull(headerValue)) {
                    return null;
                }
                String decode = new String(Base64.getDecoder().decode(headerValue));
                return JsonUtils.fromJson(decode, AuthInfo.class);
            }
        } catch (Exception e) {
            log.error("getAuthInfo error", e);
            throw e;
        }
        return null;
    }

}
