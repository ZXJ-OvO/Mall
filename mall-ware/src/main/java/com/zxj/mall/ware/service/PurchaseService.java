package com.zxj.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxj.common.utils.PageUtils;
import com.zxj.mall.ware.entity.PurchaseEntity;
import com.zxj.mall.ware.vo.MergeVo;
import com.zxj.mall.ware.vo.PurchaseDoneVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceivePurchase(Map<String, Object> params);


    void mergePurchase(MergeVo mergeVo);


    void received(List<Long> ids);


    void done(PurchaseDoneVo doneVo);
}

