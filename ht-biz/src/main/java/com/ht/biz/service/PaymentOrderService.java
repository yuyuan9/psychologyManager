package com.ht.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honeymanager.PaymentOrder;

public interface PaymentOrderService extends IService<PaymentOrder>{
	public List<PageData> findList(MyPage page);
	public Integer selectAmount(String paymentType);
	public PageData findById(PageData pd);
	public void edit(PageData pd);
}
