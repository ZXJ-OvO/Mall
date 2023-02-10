package com.zxj.mall.product.exception;

import com.zxj.common.exception.BizCodeEnum;
import com.zxj.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 集中处理所有异常
 */
@Slf4j
// 指定包下出现的所有的异常都交给此类处理
//@ControllerAdvice(basePackages = "com.zxj.mall.product.controller")
//@ResponseBody
@RestControllerAdvice(basePackages = "com.zxj.mall.product.controller")
public class MallExceptionControllerAdvice {


    @ExceptionHandler(value = Exception.class)
    public R handleValidException(Exception e) {
        log.error("数据校验出现问题{}，异常类型：{}", e.getMessage(), e.getClass());
        return R.error(BizCodeEnum.UNKNOW_EXCEPTION.getCode(),BizCodeEnum.UNKNOW_EXCEPTION.getMsg());
    }
}
