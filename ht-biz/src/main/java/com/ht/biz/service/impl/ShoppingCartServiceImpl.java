package com.ht.biz.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.ShoppingCartMapper;
import com.ht.biz.service.ShoppingCartService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.product.ShoppingCart;
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService{

	@Override
	public List<PageData> findList(MyPage<ShoppingCart> page) {
		// TODO Auto-generated method stub
		return baseMapper.findList(page);
	}

}
