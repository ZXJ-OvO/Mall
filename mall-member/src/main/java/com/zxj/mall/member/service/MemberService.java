package com.zxj.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxj.common.utils.PageUtils;
import com.zxj.mall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author zhouxinjie
 * @email 206269068@qq.com
 * @date 2022-10-30 17:13:35
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

