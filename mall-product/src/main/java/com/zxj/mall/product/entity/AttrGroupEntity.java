package com.zxj.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 属性分组
 */
@Data  //lombok
@TableName("pms_attr_group")  //对应的表
public class AttrGroupEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分组id
     * MyBatis-Plus中的注解@TableId，使用后去表中寻找拼接表名后的id
     * 即：不加寻找id字段，加了寻找attr_group_id字段
     */
    @TableId
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;
    /**
     * 完整路径
     * 注解@TableField设置参数exist为false代表该字段在对应表中无字段映射
     * 为业务需要的临时字段，不会被传入数据库做持久化
     */
    @TableField(exist = false)    // 在数据库中不存在
    private Long[] catelogPath;

}
