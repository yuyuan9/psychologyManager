package com.ht.biz.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.PolicyPackageSendMapper;
import com.ht.biz.service.PolicyPackageSendService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.PolicyPackageSend;
@Service
public class PolicyPackageSendServiceImpl extends ServiceImpl<PolicyPackageSendMapper, PolicyPackageSend> implements PolicyPackageSendService {

	@Override
	public List<PageData> findlistPage(MyPage<PageData> mypage) {
		// TODO Auto-generated method stub
		return baseMapper.findlistPage(mypage);
	}

	@Override
	public void insert(PolicyPackageSend policyPackageSend) {
		// TODO Auto-generated method stub
		baseMapper.insert(policyPackageSend);
	}

	@Override
	public void deleted(String id) {
		// TODO Auto-generated method stub
		baseMapper.deleteById(id);
	}

	@Override
	public PolicyPackageSend findByPidAndCid(PolicyPackageSend policyPackageSend) {
		// TODO Auto-generated method stub
		return baseMapper.findByPidAndCid(policyPackageSend);
	}

	@Override
	public void edit(PolicyPackageSend policyPackageSend) {
		// TODO Auto-generated method stub
		baseMapper.updateById(policyPackageSend);
	}
	

}
