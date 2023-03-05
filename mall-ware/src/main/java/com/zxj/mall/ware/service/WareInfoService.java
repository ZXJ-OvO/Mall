package com.zxj.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxj.common.utils.PageUtils;
import com.zxj.mall.ware.entity.WareInfoEntity;

import java.util.Map;

/**
 * 仓库信息
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

