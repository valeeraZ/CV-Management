package com.sylvain.cvmanagement.system.exception;

import java.util.Map;

/**
 * Created by Wenzhuo Zhao on 20/10/2021.
 */
public class BadFormatException extends BaseException{
    public BadFormatException(Map<String, Object> data){
        super(ErrorCode.BAD_FILE_EXTENSION, data);
    }
}
