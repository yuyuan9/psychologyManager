package com.ht.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.product.ShoppingCart;

public interface ShoppingCartMapper extends BaseMapper<ShoppingCart>{
	public List<PageData> findList(MyPage<ShoppingCart> page);
}
