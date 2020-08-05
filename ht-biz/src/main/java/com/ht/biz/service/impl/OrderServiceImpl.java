package com.ht.biz.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.OrderMapper;
import com.ht.biz.service.OrderService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.product.Order;
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService{

	@Override
	public List<PageData> findlist(MyPage page) {
		// TODO Auto-generated method stub
		return baseMapper.findlist(page);
	}

	@Override
	public Order findById(PageData pd) {
		// TODO Auto-generated method stub
		return baseMapper.findById(pd);
	}

	@Override
	public void updateOrder(Order order) {
		// TODO Auto-generated method stub
		baseMapper.updateOrder(order);
	}

	@Override
	public List<PageData> listCounts(PageData pd) {
		// TODO Auto-generated method stub
		return baseMapper.listCounts(pd);
	}

}
