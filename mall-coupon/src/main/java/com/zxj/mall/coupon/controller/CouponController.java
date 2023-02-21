package com.zxj.mall.coupon.controller;

import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.R;
import com.zxj.mall.coupon.entity.CouponEntity;
import com.zxj.mall.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 优惠券信息
 */
@RefreshScope   // 动态刷新配置
@RestController
@RequestMapping("coupon/coupon")
public class  CouponController {

    @Autowired
    private CouponService couponService;

/*
如果使用参数在application.properties文件中读取时采用如下办法，同时必须添加注解@RefreshScope使得日后修改代码即时生效而不用重启服务
    @Value("${coupon.user.name}")//从application.properties中获取//不要写user.name，他是环境里的变量
    private String name;
    @Value("${coupon.user.age}")
    private Integer age;

*/

    /**
     * 在mall-coupon中提供本服务
     * 在mall-member的启动类引入@EnableFeignClients(basePackages = "com.zxj.mall.member.feign")
     * 在mall-member中创建feign包专门管理远程调用
     */
    @RequestMapping("/member/list")
    public R memberCoupons(){    //全系统的所有返回都返回R
        // 应该去数据库查用户拥有的优惠券，简化不去数据库查，构造了一个优惠券返回
        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setCouponName("满100-10");//优惠券的名字
        return R.ok(couponEntity.getCouponName());
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = couponService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        CouponEntity coupon = couponService.getById(id);

        return R.ok().put("coupon", coupon);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CouponEntity coupon) {
        couponService.save(coupon);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CouponEntity coupon) {
        couponService.updateById(coupon);
        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        couponService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}
