package com.zxj.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxj.common.utils.PageUtils;
import com.zxj.mall.order.entity.OrderReturnReasonEntity;

import java.util.Map;

/**
 * ้่ดงๅๅ 
 */
public interface OrderReturnReasonService extends IService<OrderReturnReasonEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

