package com.zxj.mall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxj.mall.product.entity.CategoryBrandRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌分类关联
 *
 * @author zhouxinjie
 * @email 206269068@qq.com
 * @date 2022-10-30 17:07:24
 */
@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelationEntity> {

    void updateCategory(@Param("catId") Long catId, @Param("name") String name);
}
