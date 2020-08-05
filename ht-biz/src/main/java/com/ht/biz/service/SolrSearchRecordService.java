package com.ht.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.entity.biz.solr.recore.SolrSearchRecord;

public interface SolrSearchRecordService extends IService<SolrSearchRecord>{
	public List<String> selectUser(SolrSearchRecord ssr);
	public void updateStatus(SolrSearchRecord ssr);
}
