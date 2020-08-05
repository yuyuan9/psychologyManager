package com.ht.biz.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.SolrSearchRecordMapper;
import com.ht.biz.service.SolrSearchRecordService;
import com.ht.entity.biz.solr.recore.SolrSearchRecord;

@Service("solrSearchRecordService")
public class SolrSearchRecordServiceImpl extends ServiceImpl<SolrSearchRecordMapper, SolrSearchRecord> implements SolrSearchRecordService{

	@Override
	public List<String> selectUser(SolrSearchRecord ssr) {
		// TODO Auto-generated method stub
		return baseMapper.selectUser(ssr);
	}

	@Override
	public void updateStatus(SolrSearchRecord ssr) {
		// TODO Auto-generated method stub
		baseMapper.updateStatus(ssr);
	}

}
