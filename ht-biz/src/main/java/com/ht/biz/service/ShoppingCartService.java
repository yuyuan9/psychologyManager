package com.ht.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.product.ShoppingCart;

public interface ShoppingCartService extends IService<ShoppingCart>{
	public List<PageData> findList(MyPage<ShoppingCart> page);
}
