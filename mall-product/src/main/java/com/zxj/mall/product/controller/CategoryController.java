package com.zxj.mall.product.controller;

import com.zxj.common.utils.R;
import com.zxj.mall.product.entity.CategoryEntity;
import com.zxj.mall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 商品三级分类
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查出所有分类以及子分类，以树形结构组装起来
     */
    @GetMapping("/list/tree")
    public R list() {
        List<CategoryEntity> entities = categoryService.listWithTree();
        return R.ok().put("data", entities);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId) {
        CategoryEntity category = categoryService.getById(catId);
        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody CategoryEntity category) {
        categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody CategoryEntity category) {
        categoryService.updateCascade(category);
        return R.ok();
    }

    /**
     * 批量修改单个目标的属性
     */
    @PutMapping("/update/sort")
    public R updateSort(@RequestBody CategoryEntity[] category){
        // 传递进来的数组category包含了所有修改的信息，对于没有的信息即为不修改，使用iService自带的批量修改函数，传递一个集合，将数组转化成集合
        categoryService.updateBatchById(Arrays.asList(category));
        return R.ok();
    }

    /**
     * 菜单批量逻辑删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] catIds) {
        // 检查当前删除的菜单，是否被别的地方引用 Arrays.asList(数组)将数组转化为List集合
        categoryService.removeMenuByIds(Arrays.asList(catIds));
        return R.ok();
    }

    // RequestBody意味着从请求体中获取数据，要求必须是POST请求
    // SpringMVC自动将请求体的数据JSON转为对应的对象
}
