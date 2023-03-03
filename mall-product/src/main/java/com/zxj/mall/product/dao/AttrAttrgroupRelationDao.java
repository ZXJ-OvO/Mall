package com.zxj.mall.product.dao;

import com.zxj.mall.product.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性&属性分组关联
 * 注解@Mapper的作用：使该接口被识别为mybatis的Mapper接口，交给Spring管理，由启动类的MapperScanner扫描到后生成动态代理类
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {
    void deleteBatchRelation(@Param("entities") List<AttrAttrgroupRelationEntity> entities);
    // Mybatis-Plus：BaseMapper<接口对应的entity> 封装了一系列的CRUD操作
}
