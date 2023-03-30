package cn.tedu.camper.portal.controller;

import cn.tedu.camper.portal.Vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.server.ServerCloneException;

//@RestControllerAdvice 此為統一處理異常的類加的註解
@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {
    /**
     * ServiceException 處理方法
     * @param e
     */
    @ExceptionHandler
    public R handleServiceException(ServerCloneException e){
        log.debug("處理業務異常");
        log.debug("業務異常處理",e);
        return R.failed(e);
    }
    /**
     * Exception 處理方法
     * 處理其他異常
     * @param e
     * @return
     */
    @ExceptionHandler
    public R handleException(Exception e){
        log.error("一般異常處理",e);
        return R.failed(e);
    }

}
