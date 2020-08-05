package com.ht.biz.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.PTemplateMapper;
import com.ht.biz.service.PTemplateService;
import com.ht.commons.utils.MyPage;
import com.ht.entity.policydig.PTemplate;

@Service
public class PTemplateServiceImpl extends ServiceImpl<PTemplateMapper, PTemplate> implements PTemplateService {

	@Override
	public List<PTemplate> findlistPage(MyPage<PTemplate> mypage) {
		// TODO Auto-generated method stub
		return baseMapper.findlistPage(mypage);
	}

	@Override
	public PTemplate findById(String id) {
		// TODO Auto-generated method stub
		return baseMapper.selectById(id);
	}

	@Override
	public void insert(PTemplate pTemplate) {
		// TODO Auto-generated method stub
		baseMapper.insert(pTemplate);
	}

	@Override
	public void edit(PTemplate pTemplate) {
		// TODO Auto-generated method stub
		baseMapper.updateById(pTemplate);
	}

	@Override
	public void deleted(String id) {
		// TODO Auto-generated method stub
		baseMapper.deleteById(id);
	}

}
