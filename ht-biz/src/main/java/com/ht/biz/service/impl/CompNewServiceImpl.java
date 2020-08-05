package com.ht.biz.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.CompNewMapper;
import com.ht.biz.service.CompNewService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.solr.companypolicy.CompNew;
@Service
public class CompNewServiceImpl extends ServiceImpl<CompNewMapper, CompNew> implements CompNewService{

	@Override
	public List<PageData> findlist(MyPage<PageData> page) {
		// TODO Auto-generated method stub
		return baseMapper.findlist(page);
	}

	@Override
	public void editCompany(PageData pd) {
		// TODO Auto-generated method stub
		baseMapper.editCompany(pd);
	}

	@Override
	public void deleted(String id) {
		// TODO Auto-generated method stub
		baseMapper.deleted(id);
	}

}
