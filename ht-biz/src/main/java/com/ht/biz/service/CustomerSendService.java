package com.ht.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.CustomerSend;

public interface CustomerSendService extends IService<CustomerSend>{
	public List<PageData> findlistPage(MyPage<PageData> mypage);
	void insert(CustomerSend customerSend);
	void deleted(String id);
}
