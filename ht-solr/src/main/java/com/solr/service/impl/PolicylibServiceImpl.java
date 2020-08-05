package com.solr.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.commons.utils.MyPage;
import com.ht.entity.biz.solr.policylib.Policylib;
import com.solr.mapper.PolicylibMapper;
import com.solr.service.PolicylibService;
@Service("policylibService")
public class PolicylibServiceImpl extends ServiceImpl<PolicylibMapper, Policylib> implements PolicylibService{

	@Override
	public List<Policylib> findlistPage(MyPage page) {
		// TODO Auto-generated method stub
		return baseMapper.findlistPage(page);
	}

}
