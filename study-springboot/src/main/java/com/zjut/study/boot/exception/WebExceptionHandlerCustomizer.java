package com.zjut.study.boot.exception;

import com.zjut.common.enums.ResultCodeEnum;
import com.zjut.common.utils.ResponseUtil;
import com.zjut.study.common.convention.result.Result;
import com.zjut.study.common.convention.result.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

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
        return Results.error(ResultCodeEnum.PARAMETER_ILLEGAL.getCode(), errorMessage.toString());
    }
}
