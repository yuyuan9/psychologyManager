package com.ht.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.product.Order;

public interface OrderMapper extends BaseMapper<Order>{
	public List<PageData> findlist(MyPage page);
	public Order findById(PageData pd);
	public void updateOrder(Order order);
	public List<PageData> listCounts(PageData pd);
}
