package com.zxj.mall.coupon.dao;

import com.zxj.mall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
