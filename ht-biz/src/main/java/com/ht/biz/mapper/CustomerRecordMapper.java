package com.ht.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.entity.policydig.CustomerRecord;

public interface CustomerRecordMapper extends BaseMapper<CustomerRecord>{
	List<CustomerRecord> findlistPage(MyPage myPage);
}
