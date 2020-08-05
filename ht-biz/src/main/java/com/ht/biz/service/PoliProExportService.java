package com.ht.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.solr.policylib.PoliProExport;

public interface PoliProExportService extends IService<PoliProExport>{
	public Integer selectCounts(PageData pd);
}
