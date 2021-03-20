package com.sise.oj.exception;

import com.sise.oj.enums.ResultCode;

/**
 * 业务异常
 *
 * @author Cijee
 * @version 1.0
 */
public class BusinessException extends RuntimeException {

    private ResultCode code = ResultCode.ERROR;

    public BusinessException(String msg){
        super(msg);
    }

    public BusinessException(ResultCode code, String msg){
        super(msg);
        this.code = code;
    }
}
