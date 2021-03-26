package com.sise.oj.exception;

import com.sise.oj.enums.ResultCode;

/**
 * 自定义乐观锁异常
 *
 * @author Cijee
 * @version 1.0
 */
public class TryAgainException extends RuntimeException {

    private Integer status = ResultCode.ERROR.code();

    public TryAgainException(String message) {
        super(message);
    }

    public TryAgainException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
