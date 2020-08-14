package com.sylvain.chat.system.exception;

import java.util.Map;

public class UsernameNotFoundException extends BaseException {
    public UsernameNotFoundException(Map<String, Object> data) {
        super(ErrorCode.USERNAME_NOT_FOUND, data);
    }
}
