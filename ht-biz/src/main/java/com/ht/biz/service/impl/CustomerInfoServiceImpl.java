package com.ht.biz.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.CustomerInfoMapper;
import com.ht.biz.service.CustomerInfoService;
import com.ht.commons.utils.MyPage;
import com.ht.entity.policydig.CustomerInfo;

@Service
public class CustomerInfoServiceImpl extends ServiceImpl<CustomerInfoMapper, CustomerInfo> implements CustomerInfoService {

	@Override
	public List<CustomerInfo> findlistPage(MyPage<CustomerInfo> mypage) {
		// TODO Auto-generated method stub
		return baseMapper.findlistPage(mypage);
	}

	@Override
	public CustomerInfo findById(String id) {
		// TODO Auto-generated method stub
		return baseMapper.selectById(id);
	}

	@Override
	public void insert(CustomerInfo customerInfo) {
		// TODO Auto-generated method stub
		baseMapper.insert(customerInfo);
	}

	@Override
	public void edit(CustomerInfo customerInfo) {
		// TODO Auto-generated method stub
		baseMapper.updateById(customerInfo);
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		baseMapper.deleteById(id);
	}

	@Override
	public CustomerInfo findByNameAndOrgcode(CustomerInfo CustomerInfo) {
		// TODO Auto-generated method stub
		return baseMapper.findByNameAndOrgcode(CustomerInfo);
	}

	@Override
	public void deleteByCustomerRecordId(String id) {
		// TODO Auto-generated method stub
		baseMapper.deleteByCustomerRecordId(id);
	}

}
