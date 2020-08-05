package com.solr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.entity.biz.solr.recore.ImportRecord;

public interface ImportRecordMapper extends BaseMapper<ImportRecord>{
	public MyPage<ImportRecord> findlist(MyPage<ImportRecord> page);
}
