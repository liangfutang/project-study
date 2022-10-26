package com.zjut.study.boot.exception;

import com.zjut.study.common.convention.code.ResultCodeEnum;
import com.zjut.study.common.convention.result.Result;
import com.zjut.study.common.convention.result.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局异常捕获
 * @author jack
 */
@RestControllerAdvice
@Slf4j
public class WebExceptionHandlerCustomizer {

    /**
     * 捕获并处理参数异常结果
     * @param ex 异常
     * @param request 请求request
     * @return 返回结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex, HttpServletRequest request) {
        StringBuilder errorMessage = new StringBuilder("Invalid Request:\n");
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorMessage.append(fieldError.getDefaultMessage()).append("\n");
        }
        log.error("处理uri:{}参数异常MethodArgumentNotValidException:{}", request.getRequestURI(), errorMessage);
        return Results.error(ResultCodeEnum.PARAMETER_ILLEGAL.code(), errorMessage.toString());
    }

    /**
     * ConstraintViolationException是原始层的校验异常
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> serviceMethodArgumentNotValidExceptionHandler(ConstraintViolationException ex, HttpServletRequest request) {
        StringBuilder errorMessage = new StringBuilder("Service Invalid Request:\n");
        ex.getConstraintViolations().forEach(one -> errorMessage.append(one.getPropertyPath()).append(one.getMessage()));
        log.error("处理uri:{},service参数异常ConstraintViolationException:{}", request.getRequestURI(), errorMessage);
        return Results.error(ResultCodeEnum.PARAMETER_ILLEGAL.code(), errorMessage.toString());
    }
}
