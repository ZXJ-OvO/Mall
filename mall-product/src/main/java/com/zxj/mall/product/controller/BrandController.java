package com.zxj.mall.product.controller;

import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.R;
import com.zxj.common.valid.AddGroup;
import com.zxj.common.valid.UpdateGroup;
import com.zxj.common.valid.UpdateStatusGroup;
import com.zxj.mall.product.entity.BrandEntity;
import com.zxj.mall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.Map;


/**
 * 品牌
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {

        // 品牌的模糊查询，查询结果都被封装成PageUtils类型
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{brandId}")
    public R info(@PathVariable("brandId") Long brandId) {
        BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     * Validated设定指定条件
     */
    @PostMapping("/save")   // @Validated开启分组校验功能，参数填写校验所属分组
    public R save(@Validated(AddGroup.class) @RequestBody BrandEntity brand/*,BindingResult result*/) {
/*
        给校验的bean后紧跟一个BindingResult，可以获取到校验的结果，result.hasErrors 返回值有错误
        if (result.hasErrors()) {
            Map<String, String> map = new HashMap<>();
            // 获取校验的错误结果
            result.getFieldErrors().forEach((item) -> {
                // FieldError获取错误提示
                String message = item.getDefaultMessage();
                // 获取发生错误的属性的名字
                String field = item.getField();
                map.put(field, message);
            });
            return R.error(400, "提交的数据不合法").put("data", map);
        } else {

        }*/
        brandService.save(brand);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@Validated(UpdateGroup.class) @RequestBody BrandEntity brand) {
        brandService.updateDetail(brand);

        return R.ok();
    }

    /**
     * 修改状态
     */
    @RequestMapping("/update/status")
    public R updateStatus(@Validated(UpdateStatusGroup.class) @RequestBody BrandEntity brand){
        brandService.updateById(brand);
        return R.ok();
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] brandIds) {
        brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
