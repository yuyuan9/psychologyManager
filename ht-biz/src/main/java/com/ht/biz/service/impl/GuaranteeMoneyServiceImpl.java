package com.ht.biz.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.GuaranteeMoneyMapper;
import com.ht.biz.service.GuaranteeMoneyService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.product.GuaranteeMoney;
@Service
public class GuaranteeMoneyServiceImpl extends ServiceImpl<GuaranteeMoneyMapper, GuaranteeMoney> implements GuaranteeMoneyService {

	@Override
	public List<PageData> findlist(MyPage page) {
		// TODO Auto-generated method stub
		return baseMapper.findlist(page);
	}

	@Override
	public GuaranteeMoney getoneById(String createid) {
		// TODO Auto-generated method stub
		return baseMapper.getoneById(createid);
	}

	@Override
	public void updateStatus(PageData pd) {
		// TODO Auto-generated method stub
		baseMapper.updateStatus(pd);
	}

	@Override
	public List<GuaranteeMoney> findusergtm(MyPage page) {
		// TODO Auto-generated method stub
		return baseMapper.findusergtm(page);
	}

	@Override
	public List<GuaranteeMoney> quarzlist(PageData pd) {
		// TODO Auto-generated method stub
		return baseMapper.quarzlist(pd);
	}

}
