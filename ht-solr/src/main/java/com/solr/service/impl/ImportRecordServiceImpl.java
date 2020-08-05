package com.solr.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.commons.utils.MyPage;
import com.ht.entity.biz.solr.recore.ImportRecord;
import com.solr.mapper.ImportRecordMapper;
import com.solr.service.ImportRecordService;
@Service("importRecordService")
public class ImportRecordServiceImpl extends ServiceImpl<ImportRecordMapper, ImportRecord> implements ImportRecordService{

	@Override
	public MyPage<ImportRecord> findlist(MyPage<ImportRecord> page) {
		// TODO Auto-generated method stub
		return baseMapper.findlist(page);
	}

}
