package com.manager.system.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final int errorCode;

    private final String errorMsg;

    public BusinessException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusinessException(int errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getErrorMsg());
        this.errorCode = errorCode.getErrorCode();
        this.errorMsg = errorCode.getErrorMsg();
    }

    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getErrorMsg(), cause);
        this.errorCode = errorCode.getErrorCode();
        this.errorMsg = errorCode.getErrorMsg();
    }

}
