package com.zxj.mall.product.controller;

import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.R;
import com.zxj.mall.product.entity.AttrEntity;
import com.zxj.mall.product.entity.AttrGroupEntity;
import com.zxj.mall.product.service.AttrAttrgroupRelationService;
import com.zxj.mall.product.service.AttrGroupService;
import com.zxj.mall.product.service.AttrService;
import com.zxj.mall.product.service.CategoryService;
import com.zxj.mall.product.vo.AttrGroupRelationVo;
import com.zxj.mall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 属性分组
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrService attrService;

    @Autowired
    AttrAttrgroupRelationService relationService;

    /**
     * 新增关联关系
     */
    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> vos){

        relationService.saveBatch(vos);
        return R.ok();
    }

//    @GetMapping("/{catelogId}/withattr")
//    public R getAttrGroupWithAttrs(@PathVariable("catelogId")Long catelogId){
//
//        //1、查出当前分类下的所有属性分组，
//        //2、查出每个属性分组的所有属性
//        List<AttrGroupWithAttrsVo> vos =  attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId);
//        return R.ok().put("data",vos);
//    }

    /**
     * 分组与属性关联的功能 获取属性分组的关联的所有属性
     */
    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId){
        List<AttrEntity> entities =  attrService.getRelationAttr(attrgroupId);  // 根据分组id，找到组内关联的所有属性
        return R.ok().put("data",entities);
    }

    /**
     * 查询属性分组没有关联的其他属性
     */
    @GetMapping("/{attrgroupId}/noattr/relation")
    public R attrNoRelation(@PathVariable("attrgroupId") Long attrgroupId,
                            @RequestParam Map<String, Object> params){
        PageUtils page = attrService.getNoRelationAttr(params,attrgroupId);
        return R.ok().put("page",page);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));
        return R.ok();
    }

    /**
     * 删除属性与属性分组的关联关系
     * @param vos 请求参数vo ： attrId、attrGroupId
     */
    @PostMapping("attr//relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] vos){
        attrService.deleteRelation(vos);
        return R.ok();
    }

    /**
     * 商品系统-平台属性-属性分组  三级分类树分页查询所有属性 & 第三级指定类目的所有属性分页查询 & 属性列表的模糊匹配&分页查询
     */
    @GetMapping("/list/{catelogId}")    // @RequestParam用于将我请求路径中指定的请求参数赋值给方法中的形参。
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("catelogId") Long catelogId) {  // @PathVariable用于取出路径变量/list/{catelogId}中的参数值
        // 返回的分页数据被封装成PageUtils
        PageUtils page = attrGroupService.queryPage(params, catelogId); // params分页请求参数  catelogId 三级分类id
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long catelogId = attrGroup.getCatelogId();
        Long[] path = categoryService.findCatelogPath(catelogId);       // 根据当前所属分了id找出完整的路径
        attrGroup.setCatelogPath(path);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);
        return R.ok();
    }



}
