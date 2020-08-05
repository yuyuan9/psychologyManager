package com.ht.biz.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.PaymentOrderMapper;
import com.ht.biz.service.PaymentOrderService;
import com.ht.biz.service.RechargeService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honeymanager.PaymentOrder;
import com.ht.entity.biz.honeymanager.Recharge;
@Service
public class PaymentOrderServiceImpl extends ServiceImpl<PaymentOrderMapper, PaymentOrder> implements PaymentOrderService {
	
	@Autowired
	RechargeService rechargeService;
	
	@Override
	public List<PageData> findList(MyPage page) {
		// TODO Auto-generated method stub
		return baseMapper.findList(page);
	}

	@Override
	public Integer selectAmount(String paymentType) {
		// TODO Auto-generated method stub
		return baseMapper.selectAmount(paymentType);
	}

	@Override
	public PageData findById(PageData pd) {
		// TODO Auto-generated method stub
		return baseMapper.findById(pd);
	}

	@Override
	public void edit(PageData pd) {
		// TODO Auto-generated method stub
		baseMapper.edit(pd);
	}
	
}
