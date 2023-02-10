package com.zxj.mall.order.dao;

import com.zxj.mall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author zhouxinjie
 * @email 206269068@qq.com
 * @date 2022-10-30 17:11:41
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
