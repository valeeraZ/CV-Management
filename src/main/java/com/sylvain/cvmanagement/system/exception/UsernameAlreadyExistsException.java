package com.sylvain.cvmanagement.system.exception;

import java.util.Map;

public class UsernameAlreadyExistsException extends BaseException {
    public UsernameAlreadyExistsException(Map<String, Object> data) {
        super(ErrorCode.USERNAME_ALREADY_EXISTS, data);
    }
}
