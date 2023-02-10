package com.zxj.mall.product.dao;

import com.zxj.mall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author zhouxinjie
 * @email 206269068@qq.com
 * @date 2022-10-30 17:07:24
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
