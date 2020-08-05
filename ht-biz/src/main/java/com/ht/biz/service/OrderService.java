package com.ht.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.product.Order;

public interface OrderService extends IService<Order> {
	public List<PageData> findlist(MyPage page);
	public Order findById(PageData pd);
	public void updateOrder(Order order);
	public List<PageData> listCounts(PageData pd);
}
