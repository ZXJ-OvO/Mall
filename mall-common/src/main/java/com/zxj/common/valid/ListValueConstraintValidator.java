package com.zxj.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义的校验器   实现ConstraintValidator<校验注解名 , 校验的数据类型>
 */
public class ListValueConstraintValidator implements ConstraintValidator<ListValue, Integer> {

    private final Set<Integer> set = new HashSet<>();

    /**
     * 初始化方法（收取ListValue注解的全部信息）
     */
    @Override
    public void initialize(ListValue constraintAnnotation) {
        // 将所有的满足正确结果的值全部装入Set中

        int[] vals = constraintAnnotation.vals();
        for (int val : vals) {
            set.add(val);
        }

    }

    /**
     * 判断是否校验成功
     * value是需要校验的值
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {

        return set.contains(value); // set包含了value则返回出去
    }
}
