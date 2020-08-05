package com.ht.biz.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.PoliProExportMapper;
import com.ht.biz.service.PoliProExportService;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.solr.policylib.PoliProExport;
@Service
public class PoliProExportServiceImpl extends ServiceImpl<PoliProExportMapper, PoliProExport> implements PoliProExportService {

	@Override
	public Integer selectCounts(PageData pd) {
		// TODO Auto-generated method stub
		return baseMapper.selectCounts(pd);
	}

}
