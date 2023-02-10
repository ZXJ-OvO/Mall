package com.zxj.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxj.common.utils.PageUtils;
import com.zxj.mall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author zhouxinjie
 * @email 206269068@qq.com
 * @date 2022-10-30 17:11:41
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

