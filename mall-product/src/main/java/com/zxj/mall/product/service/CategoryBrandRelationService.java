package com.zxj.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxj.common.utils.PageUtils;
import com.zxj.mall.product.entity.CategoryBrandRelationEntity;

import java.util.Map;

/**
 * 品牌分类关联
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存详细细节
     */
    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);
    /**
     * 更新关联表
     */
    void updateBrand(Long brandId, String name);

    /**
     * 传入三级分类的id，需要更新的最新的名字
     */
    void updateCategory(Long catId, String name);
}

