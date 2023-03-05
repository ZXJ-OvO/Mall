package com.zxj.mall.order.dao;

import com.zxj.mall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
