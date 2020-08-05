package com.ht.biz.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.CustomerRecordMapper;
import com.ht.biz.service.CustomerRecordService;
import com.ht.commons.utils.MyPage;
import com.ht.entity.policydig.CustomerRecord;
@Service
public class CustomerRecordServiceImpl extends ServiceImpl<CustomerRecordMapper, CustomerRecord> implements CustomerRecordService {

	@Override
	public List<CustomerRecord> findlistPage(MyPage<CustomerRecord> myPage) {
		// TODO Auto-generated method stub
		return baseMapper.findlistPage(myPage);
	}

	@Override
	public void insert(CustomerRecord customerRecord) {
		// TODO Auto-generated method stub
		baseMapper.insert(customerRecord);
	}

	@Override
	public void deleted(String id) {
		// TODO Auto-generated method stub
		baseMapper.deleteById(id);
	}

}
