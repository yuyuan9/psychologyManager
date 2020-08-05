package com.ht.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.product.OrderUserComp;

public interface OrderUserCompService extends IService<OrderUserComp>{
	public void updateDefaults(PageData pd);
}
