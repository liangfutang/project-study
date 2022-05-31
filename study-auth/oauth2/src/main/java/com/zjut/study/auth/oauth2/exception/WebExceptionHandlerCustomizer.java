package com.zjut.study.auth.oauth2.exception;

import com.zjut.common.enums.ResultCodeEnum;
import com.zjut.common.exception.ParameterException;
import com.zjut.common.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        return ResponseUtil.wrapFailureResult(null, ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> operateException(Exception ex, HttpServletRequest request) {
        log.error("{} Exception:{},", request.getRequestURI(), ex.getMessage(), ex);
        String message = StringUtils.isBlank(ex.getLocalizedMessage()) ? ResultCodeEnum.INTERNAL_SERVER_ERROR.getMessage() : ex.getLocalizedMessage();
        return ResponseUtil.wrapFailureResult(null, ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode(), message);
    }
}
