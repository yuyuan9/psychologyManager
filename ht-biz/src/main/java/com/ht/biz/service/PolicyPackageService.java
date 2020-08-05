package com.ht.biz.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.PolicyPackage;

public interface PolicyPackageService extends IService<PolicyPackage>{
	public List<PolicyPackage> findlistPage(MyPage<PolicyPackage> mypage);
	PolicyPackage findById(String id);
	void insert(PolicyPackage policyPackage);
    void edit(PolicyPackage policyPackage);
    void deleted(String id);
    void sendMsg(List<PageData> list,PolicyPackage p,PolicyPackageSendService policyPackageSendService,int flag,Map<String ,Object > map)throws Exception ;
}
