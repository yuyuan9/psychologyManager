package com.ht.biz.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.OrderUserCompMapper;
import com.ht.biz.service.OrderUserCompService;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.product.OrderUserComp;
@Service
public class OrderUserCompServiceImpl extends ServiceImpl<OrderUserCompMapper, OrderUserComp> implements OrderUserCompService{

	@Override
	public void updateDefaults(PageData pd) {
		// TODO Auto-generated method stub
		baseMapper.updateDefaults(pd);
	}

}
