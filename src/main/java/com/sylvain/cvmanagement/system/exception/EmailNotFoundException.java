package com.sylvain.cvmanagement.system.exception;

import java.util.Map;

public class EmailNotFoundException extends BaseException {
    public EmailNotFoundException(Map<String, Object> data) {
        super(ErrorCode.EMAIL_NOT_FOUND, data);
    }
}
