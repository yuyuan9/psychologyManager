package com.ht.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.entity.policydig.PolicyPackage;

public interface PolicyPackageMapper extends BaseMapper<PolicyPackage>{
	public List<PolicyPackage> findlistPage(MyPage<PolicyPackage> mypage);
}
