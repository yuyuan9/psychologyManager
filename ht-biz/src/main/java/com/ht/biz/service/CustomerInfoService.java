package com.ht.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.entity.policydig.CustomerInfo;

public interface CustomerInfoService extends IService<CustomerInfo>{
	CustomerInfo findById(String id);
	List<CustomerInfo> findlistPage(MyPage<CustomerInfo> mypage);
	void insert(CustomerInfo customerInfo);
	void edit(CustomerInfo customerInfo);
	public void deleteById(String id);
	CustomerInfo findByNameAndOrgcode(CustomerInfo CustomerInfo);
	void deleteByCustomerRecordId(String id);
}
