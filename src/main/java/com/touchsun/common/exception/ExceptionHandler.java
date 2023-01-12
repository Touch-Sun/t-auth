package com.touchsun.common.exception;

import com.touchsun.common.app.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 *
 * @author lee
 */
@ControllerAdvice
@Component
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<String> handle(Exception e) {
        log.error("Server Error: {}", e.toString());
        e.printStackTrace();
        return Result.failed(e.getMessage());
    }
}
