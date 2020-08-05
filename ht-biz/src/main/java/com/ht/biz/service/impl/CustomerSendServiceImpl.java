package com.ht.biz.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.CustomerSendMapper;
import com.ht.biz.service.CustomerSendService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.CustomerSend;
@Service
public class CustomerSendServiceImpl extends ServiceImpl<CustomerSendMapper, CustomerSend> implements CustomerSendService {

	@Override
	public List<PageData> findlistPage(MyPage<PageData> mypage) {
		// TODO Auto-generated method stub
		return baseMapper.findlistPage(mypage);
	}

	@Override
	public void insert(CustomerSend customerSend) {
		// TODO Auto-generated method stub
		baseMapper.insert(customerSend);
	}

	@Override
	public void deleted(String id) {
		// TODO Auto-generated method stub
		baseMapper.deleteById(id);
	}

}
