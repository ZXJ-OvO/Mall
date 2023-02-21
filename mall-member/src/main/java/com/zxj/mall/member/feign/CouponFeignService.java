package com.zxj.mall.member.feign;

import com.zxj.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * feign包用于存放远程调用的内容
 */
//告诉spring cloud这个接口是一个远程客户端，要调用mall-coupon服务(nacos中找到)
@FeignClient("mall-coupon")
public interface CouponFeignService {

    // 具体是调用coupon服务的/coupon/coupon/member/list对应的方法
    @RequestMapping("/coupon/coupon/member/list")
    R memberCoupons();  // 得到一个R对象
}
