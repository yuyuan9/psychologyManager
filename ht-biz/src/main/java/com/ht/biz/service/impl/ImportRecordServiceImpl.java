package com.ht.biz.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.ImportRecordMapper;
import com.ht.biz.service.ImportRecordService;
import com.ht.commons.utils.MyPage;
import com.ht.entity.biz.solr.recore.ImportRecord;
@Service("importRecordService")
public class ImportRecordServiceImpl extends ServiceImpl<ImportRecordMapper, ImportRecord> implements ImportRecordService{

	@Override
	public List<ImportRecord> findlist(MyPage<ImportRecord> page) {
		// TODO Auto-generated method stub
		return baseMapper.findlist(page);
	}

}
