package com.ht.biz.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.GoldcoinRuleMapper;
import com.ht.biz.service.GoldcoinRuleService;
import com.ht.entity.biz.honeymanager.GoldcoinRule;
@Service("goldcoinRuleService")
public class GoldcoinRuleServiceImpl extends ServiceImpl<GoldcoinRuleMapper, GoldcoinRule> implements GoldcoinRuleService {
	
}
