package com.sylvain.chat.system.exception;

import java.util.Map;

public class FriendRequestIdNotExistsException extends BaseException{
    public FriendRequestIdNotExistsException(Map<String, Object> data){
        super(ErrorCode.REQUEST_NOT_FOUND, data);
    }
}
