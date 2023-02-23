package com.zxj.mall.product.exception;

import com.zxj.common.exception.BizCodeEnum;
import com.zxj.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理类：集中处理指定包下的所有异常
 */
@Slf4j  //日志注解，该注解需要搭配lombok依赖使用
//指定包下出现的所有的异常都交给此类处理
//@ControllerAdvice(basePackages = "com.zxj.mall.product.controller")
//@ResponseBody
//RestControllerAdvice是一个关于切面的注解，本身作用上代替了上面的两个注解
@RestControllerAdvice(basePackages = "com.zxj.mall.product.controller")
public class MallExceptionControllerAdvice {


    //@ExceptionHandler的value属性指定此处可以处理什么类型的异常
    @ExceptionHandler(value = Exception.class)
    public R handleValidException(MethodArgumentNotValidException exception) {
        Map<String, String> map = new HashMap<>();
        // 获取数据校验的错误结果
        BindingResult bindingResult = exception.getBindingResult();
        // 处理错误
        bindingResult.getFieldErrors().forEach(fieldError -> {
            String message = fieldError.getDefaultMessage();
            String field = fieldError.getField();
            map.put(field, message);
        });

        log.error("数据校验出现问题{}，异常类型：{}", exception.getMessage(), exception.getClass());
        // return R.error(BizCodeEnum.UNKNOW_EXCEPTION.getCode(), BizCodeEnum.UNKNOW_EXCEPTION.getMsg());
        return R.error(400, "数据校验出现问题").put("data", map);
    }

    /*
        每一个切面注解都有自己的作用域
        @RestControllerAdvice的作用域就是单个项目中所有使用了@RequestMapping的类

        @RestControllerAdvice搭配@ExceptionHandler被用作项目的全局异常处理

        其他搭配及作用
        https://juejin.cn/post/7025484367539470344
     */

    @ExceptionHandler(value = Throwable.class)//异常的范围更大
    public R handleException(Throwable throwable){
        log.error("未知异常{},异常类型{}",
                throwable.getMessage(),
                throwable.getClass());
        return R.error(BizCodeEnum.UNKNOW_EXCEPTION.getCode(), BizCodeEnum.UNKNOW_EXCEPTION.getMsg());
    }
}
