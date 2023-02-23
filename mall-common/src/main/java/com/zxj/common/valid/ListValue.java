package com.zxj.common.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义的校验注解
 */
@Documented
@Constraint(validatedBy = {com.zxj.common.valid.ListValueConstraintValidator.class})      // 使用的自定义校验器，此处可以填写多个校验器（数组），自动适配可用的校验器进行工作
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})     // 该注解可以标志的位置
@Retention(RUNTIME) // 注解运行的时机
public @interface ListValue {
    // 一个校验注解必须满足JSR303规范，有如下三个属性

    // 当校验出错后，出错信息去指定的位置找
    String message() default "{com.zxj.common.valid.ListValue.message}";

    // 允许分组校验功能
    Class<?>[] groups() default {};

    // 允许自定义负载信息
    Class<? extends Payload>[] payload() default {};

    int[] vals() default {};
}
