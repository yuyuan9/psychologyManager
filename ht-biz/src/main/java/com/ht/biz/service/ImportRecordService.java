package com.ht.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.entity.biz.solr.recore.ImportRecord;

public interface ImportRecordService extends IService<ImportRecord>{
	public List<ImportRecord> findlist(MyPage<ImportRecord> page);
}
