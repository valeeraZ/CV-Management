package com.sylvain.chat.system.exception;

import java.util.Map;
@Deprecated
public class UsernameNotExistedException extends BaseException {
    public UsernameNotExistedException(Map<String, Object> data) {
        super(ErrorCode.USERNAME_NOT_FOUND, data);
    }
}
