package com.zxj.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxj.common.utils.PageUtils;
import com.zxj.mall.order.entity.OrderSettingEntity;

import java.util.Map;

/**
 * 订单配置信息
 */
public interface OrderSettingService extends IService<OrderSettingEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

