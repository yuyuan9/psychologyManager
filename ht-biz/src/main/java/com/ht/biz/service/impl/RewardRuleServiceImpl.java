package com.ht.biz.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.RewardRuleMapper;
import com.ht.biz.service.RewardRuleService;
import com.ht.entity.biz.honeymanager.RewardRule;
@Service("rewardRuleService")
public class RewardRuleServiceImpl extends ServiceImpl<RewardRuleMapper,RewardRule>implements RewardRuleService {

}
