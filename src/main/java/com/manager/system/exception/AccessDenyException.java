package com.manager.system.exception;

public class AccessDenyException extends BusinessException {
    public AccessDenyException() {
        super(ErrorCode.ACCESS_DENY);
    }
}
