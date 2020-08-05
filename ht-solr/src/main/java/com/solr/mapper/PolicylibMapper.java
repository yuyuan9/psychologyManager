package com.solr.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.entity.biz.solr.policylib.Policylib;

public interface PolicylibMapper extends BaseMapper<Policylib>{
	public List<Policylib> findlistPage(MyPage page);
}
