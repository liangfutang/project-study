package com.zjut.study.auth.oauth2.exception;

import com.zjut.study.common.convention.code.ResultCodeEnum;
import com.zjut.study.common.convention.result.Results;
import com.zjut.study.common.exception.ParameterException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常捕获
 * @author liangfu.tang
 */
@RestControllerAdvice
@Slf4j
public class WebExceptionHandlerCustomizer {

    @ExceptionHandler(ParameterException.class)
    public ResponseEntity<?> operateParameterException(ParameterException ex, HttpServletRequest request) {
        log.error("{} ParameterException:{},", request.getRequestURI(), ex.getMessage(), ex);
        return new ResponseEntity<>(Results.error(ex.getCode(), ex.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> operateException(Exception ex, HttpServletRequest request) {
        log.error("{} Exception:{},", request.getRequestURI(), ex.getMessage(), ex);
        String message = StringUtils.isBlank(ex.getLocalizedMessage()) ? ResultCodeEnum.INTERNAL_SERVER_ERROR.message() : ex.getLocalizedMessage();
        return new ResponseEntity<>(Results.error(ResultCodeEnum.INTERNAL_SERVER_ERROR.code(), message), HttpStatus.OK);
    }
}
