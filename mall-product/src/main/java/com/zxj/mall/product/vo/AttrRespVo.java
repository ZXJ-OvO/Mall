package com.zxj.mall.product.vo;

import lombok.Data;

/**
 * VO：用于封装后端传给前端的值对象
 * 用于响应
 */
@Data   //lombok
public class AttrRespVo extends AttrVo {
    /**
     * 所属分类名字
     */
    private String catelogName;
    /**
     * 所属分组的名字
     */
    private String groupName;
    /**
     * 所属分类的完整路径
     */
    private Long[] catelogPath;
}
