package com.ht.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.solr.policylib.PoliProExport;

public interface PoliProExportMapper extends BaseMapper<PoliProExport>{
	public Integer selectCounts(PageData pd);
}
