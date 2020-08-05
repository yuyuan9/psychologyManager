package com.ht.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.entity.policydig.CustomerInfo;

public interface CustomerInfoMapper extends BaseMapper<CustomerInfo>{
	List<CustomerInfo> findlistPage(MyPage<CustomerInfo> mypage);
	CustomerInfo findByNameAndOrgcode(CustomerInfo CustomerInfo);
	void deleteByCustomerRecordId(String id);
}
