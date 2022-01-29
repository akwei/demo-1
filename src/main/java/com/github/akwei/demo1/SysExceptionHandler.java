package com.github.akwei.demo1;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class SysExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        return this.handleExceptionInternal(ex, null, headers, HttpStatus.UNAUTHORIZED, request);
    }


    @ExceptionHandler(value = {BizException.class})
    public ResponseEntity<Object> handleBizException(BizException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        return this.handleExceptionInternal(ex, null, headers, ex.getHttpStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("http_code", status.value());
        map.put("http_message", status.getReasonPhrase());
        if (ex instanceof BizException) {
            BizException bizEx = (BizException) ex;
            map.put("error_code", bizEx.getErrorCode());
            map.put("error_message", bizEx.getErrorMessage());
        }
        return ResponseEntity.status(status).body(map);
    }
}
