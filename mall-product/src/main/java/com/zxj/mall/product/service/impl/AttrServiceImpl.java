package com.zxj.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxj.common.constant.ProductConstant;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.Query;
import com.zxj.mall.product.dao.AttrAttrgroupRelationDao;
import com.zxj.mall.product.dao.AttrDao;
import com.zxj.mall.product.dao.AttrGroupDao;
import com.zxj.mall.product.dao.CategoryDao;
import com.zxj.mall.product.entity.AttrAttrgroupRelationEntity;
import com.zxj.mall.product.entity.AttrEntity;
import com.zxj.mall.product.entity.AttrGroupEntity;
import com.zxj.mall.product.entity.CategoryEntity;
import com.zxj.mall.product.service.AttrService;
import com.zxj.mall.product.service.CategoryService;
import com.zxj.mall.product.vo.AttrGroupRelationVo;
import com.zxj.mall.product.vo.AttrRespVo;
import com.zxj.mall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    AttrAttrgroupRelationDao relationDao;

    @Autowired
    AttrGroupDao attrGroupDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();   // PO
        // BeanUtils提供了如下方法复制属性，将参数一的属性复制到参数二中
        // 前提是参数一的属性在参数二中都是一一对应的
        BeanUtils.copyProperties(attr, attrEntity); // 将VO（attr）中的值复制到PO（attrEntity）中
        // 保存基本数据
        this.save(attrEntity);

        // 保存关联关系
        if (attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() && attr.getAttrGroupId() != null) {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationDao.insert(relationEntity);
        }

    }

    /**
     * 商品系统-平台属性-商品属性  三级分类第三级分类所有属性分页查询 & 第三级分类指定类目分页查询 & -查询按键模糊&分页查询
     *
     * @param params    前端传递进来的分页查询数据
     * @param catelogId 所属分类id
     * @param type      属性的类型
     */
    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type) {
        // 查询条件  无论任何情况都要检查类型，因此在此拼装eq判断base的类型
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>()
                .eq("attr_type", "base".equalsIgnoreCase(type)
                        ? ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() : ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        // equalsIgnoreCase忽略大小写;

        // 查询指定的三级分类id下的所有属性
        if (catelogId != 0) {
            queryWrapper.eq("catelog_id", catelogId);
        }

        // 在传递进来的请求参数params中获取前端的key即模糊查询条件
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and((wrapper) -> wrapper.eq("attr_id", key).or().like("attr_name", key));
        }

        // 包装成IPage，封装给PageUtils
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), queryWrapper);
        PageUtils pageUtils = new PageUtils(page);

        List<AttrEntity> records = page.getRecords();   // getRecords 分页对象记录列表
        List<AttrRespVo> respVos = records.stream().map((attrEntity) -> {
            AttrRespVo attrRespVo = new AttrRespVo();

            BeanUtils.copyProperties(attrEntity, attrRespVo);   //将attrEntity中的属性复制到attrRespVo

            if ("base".equalsIgnoreCase(type)) {
                // 设置attrRespVo中特有的属性：分类和分组的名字
                // 在中间表根据属性的id查出分组的id
                AttrAttrgroupRelationEntity attrId = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
                // 设置分组信息
                if (attrId != null && attrId.getAttrGroupId() != null) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrId.getAttrGroupId());
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());    // 查出当前分组的信息，然后根据分组的信息给他设置分组名
                }
            }
            // 设置分了信息
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }

            return attrRespVo;  // 此时attrRespVo的所有属性都有了值去做响应
        }).collect(Collectors.toList());    // 收集成一个集合

        pageUtils.setList(respVos);
        return pageUtils;

    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrRespVo respVo = new AttrRespVo();
        AttrEntity attrEntity = this.getById(attrId);   // 查询当前attrEntity的详细信息
        BeanUtils.copyProperties(attrEntity, respVo);   // 将查询出来的attrEntity中的数据拷贝到respVo里面

        // respVo中除了包含attrEntity中的数据还应该有分组id和所属的分类的完整路径

        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            //1、设置分组信息
            AttrAttrgroupRelationEntity attrgroupRelation = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));
            if (attrgroupRelation != null) {
                respVo.setAttrGroupId(attrgroupRelation.getAttrGroupId());
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupRelation.getAttrGroupId());
                if (attrGroupEntity != null) {
                    respVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
        }


        //2、设置分类信息
        Long catelogId = attrEntity.getCatelogId();
        Long[] catelogPath = categoryService.findCatelogPath(catelogId);    // findCatelogPath 根据当前所属分类的id找出当前的完整路径
        respVo.setCatelogPath(catelogPath);

        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        if (categoryEntity != null) {
            respVo.setCatelogName(categoryEntity.getName());
        }

        return respVo;
    }


    @Transactional
    @Override
    public void updateAttr(AttrVo attr) {
        // 基本信息的修改
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.updateById(attrEntity);

        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            //1、修改分组关联
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();

            // 设置新提交来的数据
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attr.getAttrId());

            // 根据 Wrapper 条件，查询总记录数
            Integer count = relationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
            // 根据记录数来判断当前的操作是新增还是更新
            if (count > 0) {    // 存在记录，当前操作为更新
                // 成功更新关联关系
                relationDao.update(relationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
            } else {    // 不存在记录，当前的操作为新增
                relationDao.insert(relationEntity);
            }
        }
    }

    /**
     * 根据分组id查找关联的所有基本属性
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        // 查询多个，找到所有的属性关联，最终返回所有的关联的信息（attrId、attrGroupId、attrSort）
        List<AttrAttrgroupRelationEntity> entities = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId));
        // 收集所有的属性id
        List<Long> attrIds = entities.stream().map((attr) -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());

        if (attrIds == null || attrIds.size() == 0) {    // 条件 'attrIds == null' 始终为 'false' stream不可能返回能null
            return null;
        }
        // 根据收集到的attrIds属性id集合查询到对应的所有entity的信息
        Collection<AttrEntity> attrEntities = this.listByIds(attrIds);

        return (List<AttrEntity>) attrEntities;
    }

    /**
     * 删除关联关系
     */
    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {
        //relationDao.delete(new QueryWrapper<>().eq("attrId",1L).eq("attr_group_id",1L));
        // 只发一次删除请求，批量删除
        // 将页面收集来的vos转换成list收集
        List<AttrAttrgroupRelationEntity> entities = Arrays.asList(vos).stream().map((item) -> {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, attrAttrgroupRelationEntity); // 将数据复制到attrAttrgroupRelationEntity
            return attrAttrgroupRelationEntity;
        }).collect(Collectors.toList()); // attrAttrgroupRelationEntity返回成list
        relationDao.deleteBatchRelation(entities);
    }

    /**
     * 获取当前分组没有关联的属性
     */
    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {
        //1、当前分组只能关联自己所属的分类里面的所有属性
        // 根据分组id查出所有信息，然后根据信息获取当前所属的分类的id
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();
        //2、当前分组只能关联别的分组没有引用的属性
        //2.1)、当前分类下的其他分组（查询条件是当前所有catelogId中的attrGroupId排除当前传进来的（正在关联的）attrGroupId）
        // 拿到已经关联的所有属性的id
        List<AttrGroupEntity> group = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().
                eq("catelog_id", catelogId));
        List<Long> collect = group.stream().map(item -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());

        //2.2)、其他分组里包含的所有的分组关联的属性
        List<AttrAttrgroupRelationEntity> groupId = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().
                in("attr_group_id", collect));
        List<Long> attrIds = groupId.stream().map(item -> {
            return item.getAttrId();
        }).collect(Collectors.toList());

        //2.3)、从当前分类的所有属性中移除这些属性；
        // 当前分组可以关联的其他属性
        //List<AttrEntity> attrEntities = this.baseMapper.selectList(new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId).notIn("attr_id", attrIds));
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>().
                eq("catelog_id", catelogId).
                eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if (attrIds != null && attrIds.size() > 0) {
            // attrIds不为空再拼接此条件
            wrapper.notIn("attr_id", attrIds);
        }
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((w) -> {
                w.eq("attr_id", key).or().like("attr_name", key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), wrapper);

        PageUtils pageUtils = new PageUtils(page);

        return pageUtils;
    }

}