package com.sylvain.chat.security.exception;

import com.sylvain.chat.security.utils.JsonErrorHandler;
import com.sylvain.chat.system.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * The access of resources needs authorization
     * send 403 error in the response
     * @param request
     * @param response
     * @param accessDeniedException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("AccessDeniedHandler: " + accessDeniedException.getMessage());
        ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
        JsonErrorHandler.printJson(errorCode,request,response);

        //response.sendError(HttpServletResponse.SC_FORBIDDEN, ErrorCode.ACCESS_DENIED.getMessage());
    }
}
