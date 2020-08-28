package com.sylvain.chat.security.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sylvain.chat.system.exception.ErrorCode;
import com.sylvain.chat.system.exception.ErrorResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonErrorHandler {

    public static void printJson(ErrorCode errorCode, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(errorCode,request.getRequestURI(),null);

        ObjectMapper mapper = new ObjectMapper();
        String error = mapper.writeValueAsString(errorResponse);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getStatus().value());
        response.getWriter().print(error);
        response.getWriter().flush();
    }
}
