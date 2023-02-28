package com.zxj.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxj.common.utils.PageUtils;
import com.zxj.mall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查出所有分类，组装成父子结构
     */
    List<CategoryEntity> listWithTree();

    /**
     * 菜单批量逻辑删除
     */
    void removeMenuByIds(List<Long> asList);

    /**
     * 根据当前所属分类的id找出当前的完整路径
     */
    Long[] findCatelogPath(Long catelogId);

    void updateCascade(CategoryEntity category);
}

