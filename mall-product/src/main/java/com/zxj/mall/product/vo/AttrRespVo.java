package com.zxj.mall.product.vo;

import lombok.Data;

/**
 * 用于响应
 */
@Data
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
