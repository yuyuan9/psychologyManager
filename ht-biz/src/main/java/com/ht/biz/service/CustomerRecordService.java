package com.ht.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.entity.policydig.CustomerRecord;

public interface CustomerRecordService extends IService<CustomerRecord>{
	List<CustomerRecord> findlistPage(MyPage<CustomerRecord> myPage);
	void insert(CustomerRecord customerRecord);
	void deleted(String id);
}
