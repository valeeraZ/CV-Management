package com.sylvain.chat.system.exception;

import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

abstract class BaseException extends RuntimeException {
    private final ErrorCode errorCode;
    private final transient HashMap<String, Object> data = new HashMap<>();


    public BaseException(ErrorCode errorCode, Map<String, Object> data) {
        this.errorCode = errorCode;
        if(!ObjectUtils.isEmpty(data)){
            this.data.putAll(data);
        }
    }



    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
