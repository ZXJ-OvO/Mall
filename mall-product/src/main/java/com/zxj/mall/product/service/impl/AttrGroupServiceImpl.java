package com.zxj.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.Query;
import com.zxj.mall.product.dao.AttrGroupDao;
import com.zxj.mall.product.entity.AttrGroupEntity;
import com.zxj.mall.product.service.AttrGroupService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

/*
    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        if (catelogId == 0) {
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params),
                    new QueryWrapper<>());
            return new PageUtils(page);
        } else {
            String key = (String) params.get("key");
            QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId);
            if (!StringUtils.isEmpty(key)) {
                wrapper.and((obj) -> obj.eq("attr_group_id", key).or().like("attr_group_name", key));
            }

            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params),
                    new QueryWrapper<>());
            return new PageUtils(page);
        }
    }*/

    /**
     * （商品系统-平台属性-属性分组）三级分类的分页查询
     *
     * @param params    pageUtils原本的分页请求参数
     * @param catelogId 从请求路径中解析得到的分类id
     * @return 分页查询结果封装到PageUtils
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        // 如果没有三级分类就传0，0代表查所有

        String key = (String) params.get("key");
        //select * from pms_attr_group where catelog_id=? and (attr_group_id=key or attr_group_name like %key%)
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((obj) -> obj.eq("attr_group_id", key).or().like("attr_group_name", key));
        }


        // 没有选中三级分类就传0，0代表查所有；否则查询给定的
        if (catelogId == 0) {
            /*
             this 可以代表任何对象，当 this 出现在某个方法体中时，它所代表的对象是不确定的，但它的类型是确定的，它所代表的只能是当前类的实例。
             只有当这个方法被调用时，它所代表的对象才被确定下来，谁在调用这个方法，this 就代表谁。
             */
            // this.page(IPage分页信息, 查询条件信息)   通过new Query<xxxEntity>().getPage(Map分页信息参数)得到一个IPage分页信息对象
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);

            // PageUtils封装了查询结果
            return new PageUtils(page);
        } else {
            // 按照给定的不为0的CatelogId查询（模糊查询）
            // select * from pms_attr_group where cateLog_id=? and (attr_group_id=key or attr_group_name like %key%)
            wrapper.eq("catelog_id", catelogId); // eq(a,b) --> a == b

            // 如果传递进来的页面模糊查询key值不为空
            if (!StringUtils.isEmpty(key)) {
                // wrapper.and()用于串联条件，允许函数函数式编程
                wrapper.and((obj) -> {
                    // eq()：等于   or()：或者   like：%模糊匹配%  likeRight()、likeLeft()
                    obj.eq("attr_group_id", key).or().like("attr_group_name", key);
                });
            }

            // 结果封装成IPage对象
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);

            return new PageUtils(page);
        }

    }

}