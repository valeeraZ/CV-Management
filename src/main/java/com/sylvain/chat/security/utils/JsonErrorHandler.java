package com.sylvain.chat.security.utils;

import com.alibaba.fastjson.JSONObject;
import com.sylvain.chat.system.exception.ErrorCode;
import com.sylvain.chat.system.exception.ErrorResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonErrorHandler {
    public static void printJson(ErrorCode errorCode, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(errorCode,request.getRequestURI(),null);

        JSONObject error = new JSONObject(true);
        error.put("timestamp",errorResponse.getTimestamp());
        error.put("status",errorResponse.getStatus());
        error.put("error",errorCode.getStatus().getReasonPhrase());
        error.put("message",errorResponse.getMessage());
        error.put("path",errorResponse.getPath());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getStatus().value());
        response.getWriter().print(error);
        response.getWriter().flush();
    }
}
