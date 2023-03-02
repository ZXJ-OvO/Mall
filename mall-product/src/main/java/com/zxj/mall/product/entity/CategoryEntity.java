package com.zxj.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 商品三级分类
 */
@Data
@TableName("pms_category")
public class CategoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    // 序列化：https://www.bilibili.com/video/BV1yP4y1f7mJ/?spm_id_from=pageDriver&vd_source=36f9db23bd6da1f70f47f7bd79a858d4

    /**
     * 分类id
     */
    @TableId
    private Long catId;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 父分类id
     */
    private Long parentCid;
    /**
     * 层级
     */
    private Integer catLevel;
    /**
     * 是否显示[0不显示，1显示] 用于配置表字段和设定相反的情况，建议表值和配置一致，这样可以避免在此处配置
     * 注解@TabLogic用于逻辑删除（执行delete操作后并不是真正删除数据，而是修改属性值使得其不显示）
     * value=未删除的值，默认为0，显示
     * delval=删除后的值，默认是1，不显示
     */
    @TableLogic(value = "1", delval = "0")  // 当showStatus==0时进行逻辑删除，隐藏该数据
    private Integer showStatus;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 图标地址
     */
    private String icon;
    /**
     * 计量单位
     */
    private String productUnit;
    /**
     * 商品数量
     */
    private Integer productCount;
    /**
     * 子节点
     * 注解@JsonInclude的作用：指定实体类在序列化时的策略
     * 当商品系统-平台属性-属性分组-新增-所属分类 选择到第三级分类时，第三级分类不应该有子节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)    // 值不为空的时候，返回对象时才会带上该属性（在序列化时，值不为空才进行序列化持久化存储）
    @TableField(exist = false)  // 该字段在表中不存在，为业务需要临时创建，该属性值的数据不会被持久化
    private List<CategoryEntity> children;
}
