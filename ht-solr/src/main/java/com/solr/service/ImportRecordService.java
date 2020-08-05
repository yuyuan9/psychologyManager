package com.solr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.entity.biz.solr.recore.ImportRecord;

public interface ImportRecordService extends IService<ImportRecord>{
	public MyPage<ImportRecord> findlist(MyPage<ImportRecord> page);
}
