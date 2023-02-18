package com.zxj.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zxj.common.valid.AddGroup;
import com.zxj.common.valid.ListValue;
import com.zxj.common.valid.UpdateGroup;
import com.zxj.common.valid.UpdateStatusGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 品牌
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     * 通过设置groups属性设定注解生效的触发条件（数组）
     */
    @NotNull(message = "修改必须指定品牌id", groups = {UpdateGroup.class})
    @Null(message = "新增不能指定id", groups = {AddGroup.class})
    @TableId
    private Long brandId;


    /**
     * 品牌名
     */
    // @NotNull	 不能为null，可以为empty
    // @NotEmpty 不能为null且长度必须大于0
    // @NotBlank 必须有实际字符(只能作用于String)
    @NotBlank(message = "品牌名必须提交", groups = {AddGroup.class, UpdateGroup.class})    //message属性用于自定义校验信息
    private String name;


    /**
     * 品牌logo地址
     */
    @NotEmpty(groups = {AddGroup.class})
    @URL(message = "logo必须是一串合法的url地址", groups = {AddGroup.class, UpdateGroup.class})    // logo校验必须满足是一串url地址
    private String logo;


    /**
     * 介绍
     */
    private String descript;


    /**
     * 显示状态[0-不显示；1-显示]
     */
    //@Pattern()
    @NotNull(groups = {AddGroup.class, UpdateStatusGroup.class})
    @ListValue(vals = {1, 0}, groups = {AddGroup.class, UpdateStatusGroup.class})
    private Integer showStatus;


    /**
     * 检索首字母
     */
    @NotEmpty(groups = {AddGroup.class})
    @Pattern(regexp = "^[a-zA-Z]$", message = "检索首字母必须是一个字母", groups = {AddGroup.class, UpdateGroup.class})
    // 自定义校验规则使用@Pattern注解，规则满足正则表达式
    private String firstLetter;


    /**
     * 排序
     */
    @NotNull(groups = {AddGroup.class})
    @Min(value = 0, message = "排序必须大于等于0", groups = {AddGroup.class, UpdateGroup.class})    // 最小值为0
    private Integer sort;

}
