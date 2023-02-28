package com.zxj.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.Query;
import com.zxj.mall.product.dao.BrandDao;
import com.zxj.mall.product.entity.BrandEntity;
import com.zxj.mall.product.service.BrandService;
import com.zxj.mall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 品牌模糊查询
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // params带的key值有可能是字符串
        String key = (String) params.get("key");
        // 传过来的key有值，说明需要进行模糊检索
        QueryWrapper<BrandEntity> queryWrapper = new QueryWrapper<>();
        //如果key值不为空
        if (!StringUtils.isEmpty(key)) {
            // 模糊匹配要么就是id等于传过来的key值，要么就是name等于传过来的值
            queryWrapper.eq("brand_id", key).or().like("name", key);
        }

        IPage<BrandEntity> page = this.page(new Query<BrandEntity>().getPage(params), queryWrapper);

        return new PageUtils(page);
    }

    /**
     * 更新冗余字段设计的中间表，确保因为字段中涉及的其他表中的字段发发生了修改后影响了数据一致性
     */
    @Transactional  // 添加事务注解
    @Override
    public void updateDetail(BrandEntity brand) {
        // 把当前自己表中的品牌更新掉
        this.updateById(brand);
        // 如果此次更新的品牌里面有更新品牌名字（即传进来的包含了修改信息brand对象中存在brandName字段）
        if (!StringUtils.isEmpty(brand.getName())) {
            // 同步更新其他关联的表数据
            categoryBrandRelationService.updateBrand(brand.getBrandId(), brand.getName());

            // TODO 更新其他关联
        }
    }

}