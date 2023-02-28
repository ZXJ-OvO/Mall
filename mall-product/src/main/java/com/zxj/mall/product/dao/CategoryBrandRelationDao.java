package com.zxj.mall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxj.mall.product.entity.CategoryBrandRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌分类关联
 */
@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelationEntity> {

    // mybatis声明的mapper接口如果有两个参数必须使用@Param注解为每一个参数拟定一个名字，使得获取这些值时更加方便
    // @Param注解用来命名参数名，该参数名对应着xml文件中的SQL语句里的参数名
    void updateCategory(@Param("catId") Long catId, @Param("name") String name);
}
