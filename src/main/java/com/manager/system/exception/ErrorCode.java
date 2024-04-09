package com.manager.system.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    SYSTEM_ERROR(500000, "SYSTEM_ERROR"),
    ACCESS_DENY(500001, "do not have access"),
    INVALID_AUTH_INFO(500002, "auth info invalid"),
    ;

    private final int errorCode;

    private final String errorMsg;

    ErrorCode(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
