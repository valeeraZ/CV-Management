package com.sylvain.chat.system.exception;

import java.util.Map;

public class PersonNotFoundException extends BaseException{
    public PersonNotFoundException(Map<String, Object> data)  {
        super(ErrorCode.PERSON_NOT_FOUND, data);
    }
}
