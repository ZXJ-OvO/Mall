package com.zxj.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.Query;
import com.zxj.mall.product.dao.CategoryDao;
import com.zxj.mall.product.entity.CategoryEntity;
import com.zxj.mall.product.service.CategoryBrandRelationService;
import com.zxj.mall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    /* 关于
        因为在本类中CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity>
        查看源码可知：
            public class ServiceImpl<M extends BaseMapper<T>, T>
            @Autowired
            protected M baseMapper;
        所以；等价于 CategoryDao baseMapper
        因此不用再声明并自动装配一个categoryDao
     */

    /**
     * 查出所有分类、组装成父子结构
     */
    @Override
    public List<CategoryEntity> listWithTree() {
        // 1. 查出所有分类，baseMapper即为@Autowired CategoryDao categoryDao；参数queryWrapper为查询条件，没有即为null
        List<CategoryEntity> entities = baseMapper.selectList(null);
        // 2. 组装成父子结构
        // 2.1 找出所有的一级分类（一级分类的表数据中parent_cid为0）
        return entities.stream().filter(categoryEntity ->
                categoryEntity.getParentCid() == 0
        ).map((menu) -> {
            menu.setChildren(getChildren(menu, entities));
            return menu;
        }).sorted((menu1, menu2) -> (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort())).collect(Collectors.toList());
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        // TODO 检查当前删除的菜单是否被别的地方引用

        // 逻辑删除
        baseMapper.deleteBatchIds(asList);
    }

    /**
     * 根据当前所属分类的id查出当前对应的完整路径
     */
    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);

        // 收集进来的路径是逆序的，使用Collections工具类的方法reverse对集合中的元素进行反转
        Collections.reverse(parentPath);
        return parentPath.toArray(new Long[parentPath.size()]);
    }

    /**
     * 级联更新所有关联数据
     */
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
    }

    /**
     * 迭代收集每次查到节点数据，传入要查找的分类id和收集路径的容器paths
     */
    private List<Long> findParentPath(Long catelogId, List<Long> paths) {
        paths.add(catelogId);        // 收集当前节点
        CategoryEntity byId = this.getById(catelogId);
        if (byId.getParentCid() != 0) {
            // 递归判断有没有父id，有的话把父id收集进来
            findParentPath(byId.getParentCid(), paths);
        }
        // 递归到最后发现没有父id了就将所有的父id组合起来与当前的分类id一起返回就是当前所属分类的完整路径
        return paths;
    }

    /**
     * 递归查找所有菜单的子菜单
     */
    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {
        return all.stream().filter(categoryEntity ->
                categoryEntity.getParentCid() == root.getCatId()
        ).map(categoryEntity -> {
            // 找到子菜单
            categoryEntity.setChildren(getChildren(categoryEntity, all));
            return categoryEntity;
        }).sorted((menu1, menu2) -> (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort())
        ).collect(Collectors.toList());
    }
}