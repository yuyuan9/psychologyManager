package com.ht.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.PolicyPackageSend;

public interface PolicyPackageSendMapper extends BaseMapper<PolicyPackageSend>{
	public List<PageData> findlistPage(MyPage<PageData> mypage);
	public PolicyPackageSend findByPidAndCid(PolicyPackageSend policyPackageSend);
}
