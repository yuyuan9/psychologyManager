package com.ht.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.PolicyPackageSend;

public interface PolicyPackageSendService extends IService<PolicyPackageSend>{
	public List<PageData> findlistPage(MyPage<PageData> mypage);
	void insert(PolicyPackageSend policyPackageSend);
	void edit(PolicyPackageSend policyPackageSend);
	void deleted(String id);
	public PolicyPackageSend findByPidAndCid(PolicyPackageSend policyPackageSend);
}
