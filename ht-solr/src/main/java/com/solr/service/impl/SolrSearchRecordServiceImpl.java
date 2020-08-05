package com.solr.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.entity.biz.solr.recore.SolrSearchRecord;
import com.solr.mapper.SolrSearchRecordMapper;
import com.solr.service.SolrSearchRecordService;

@Service("solrSearchRecordService")
public class SolrSearchRecordServiceImpl extends ServiceImpl<SolrSearchRecordMapper, SolrSearchRecord> implements SolrSearchRecordService{

}
