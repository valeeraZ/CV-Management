package com.sylvain.chat.system.exception;

import java.util.Map;

public class FriendshipAlreadyExistsException extends BaseException {
    public FriendshipAlreadyExistsException(Map<String, Object> data) {
        super(ErrorCode.FRIENDSHIP_ALREADY_EXISTS, data);
    }
}
