package com.zxj.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxj.common.utils.PageUtils;
import com.zxj.mall.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 */
public interface BrandService extends IService<BrandEntity> {

    /**
     * 根据传递进来的分页信息的Map集合
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 更新中间表的冗余字段确保数据的一致性
     */
    void updateDetail(BrandEntity brand);
}

