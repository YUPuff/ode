package com.example.ode.util;


import com.example.ode.common.BusinessException;
import com.example.ode.common.Result;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 用于捕捉校验字段产生的异常并返回给前端具体错误信息
     * @param exception
     * @return
     */
    @ExceptionHandler(BindException.class)
    public Result validExceptionHandler(BindException exception) {
        return Result.failure(exception.getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result businessExceptionHandler(BusinessException exception){
        return Result.failure(exception.getMessage());
    }
}
