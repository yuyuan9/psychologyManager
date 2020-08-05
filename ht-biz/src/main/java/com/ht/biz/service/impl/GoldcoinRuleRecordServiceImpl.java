package com.ht.biz.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.GoldcoinRuleRecordMapper;
import com.ht.biz.service.GoldcoinRuleRecordService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honeymanager.GoldcoinRuleRecord;
@Service("goldcoinRuleRecordService")
public class GoldcoinRuleRecordServiceImpl extends ServiceImpl<GoldcoinRuleRecordMapper, GoldcoinRuleRecord> implements GoldcoinRuleRecordService {

	@Override
	public List<PageData> findGoldcoin(MyPage page) {
		// TODO Auto-generated method stub
		return baseMapper.findGoldcoin(page);
	}

	@Override
	public Double findCounts(PageData pd) {
		// TODO Auto-generated method stub
		return baseMapper.findCounts(pd);
	}

	@Override
	public Double mygolds(String userId) {
		return baseMapper.mygolds(userId);
	}

}
