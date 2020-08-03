package com.zmz.exception;

import com.zmz.response.ServerResponse;
import com.zmz.response.error.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;




@ControllerAdvice
@Slf4j
public class GLobalExceptionHandler {

    //声明要捕获的异常
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ServerResponse<Object> BusinessExceptionHandler(BusinessException e) {
        log.info("Business Exception ", e);
        return ServerResponse.error(e.getErrorCode(), e.getErrorMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ServerResponse<Object> unknownExceptionHandler(Exception e)
    {
        log.info("Unknown Exception", e);
        return ServerResponse.error(5000, "未知错误");
    }

}
