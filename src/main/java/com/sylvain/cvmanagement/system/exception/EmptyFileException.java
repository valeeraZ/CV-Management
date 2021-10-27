package com.sylvain.cvmanagement.system.exception;

import java.util.Map;

/**
 * Created by Wenzhuo Zhao on 20/10/2021.
 */
public class EmptyFileException extends BaseException{
    public EmptyFileException(Map<String, Object> data) {
        super(ErrorCode.EMPTY_FILE, data);
    }
}
