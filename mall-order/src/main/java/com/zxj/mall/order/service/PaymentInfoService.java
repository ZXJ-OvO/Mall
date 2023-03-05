package com.zxj.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxj.common.utils.PageUtils;
import com.zxj.mall.order.entity.PaymentInfoEntity;

import java.util.Map;

/**
 * 支付信息表
 */
public interface PaymentInfoService extends IService<PaymentInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

