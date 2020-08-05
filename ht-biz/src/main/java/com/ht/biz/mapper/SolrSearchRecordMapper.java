package com.ht.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.entity.biz.solr.recore.SolrSearchRecord;

public interface SolrSearchRecordMapper extends BaseMapper<SolrSearchRecord>{
	public List<String> selectUser(SolrSearchRecord ssr);
	public void updateStatus(SolrSearchRecord ssr);
}
