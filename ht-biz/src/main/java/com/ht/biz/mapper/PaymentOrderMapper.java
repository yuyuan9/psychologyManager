package com.ht.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honeymanager.PaymentOrder;

public interface PaymentOrderMapper extends BaseMapper<PaymentOrder>{
	public List<PageData> findList(MyPage page);
	public Integer selectAmount(String paymentType);
	public PageData findById(PageData pd);
	public void edit(PageData pd);
}
