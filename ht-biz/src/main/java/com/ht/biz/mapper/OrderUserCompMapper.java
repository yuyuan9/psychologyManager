package com.ht.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.product.OrderUserComp;

public interface OrderUserCompMapper extends BaseMapper<OrderUserComp>{
	public void updateDefaults(PageData pd);
}
