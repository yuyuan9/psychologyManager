package com.ht.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.solr.policylib.Policylib;

public interface PolicylibMapper extends BaseMapper<Policylib>{
	public Integer dealReset(PageData pd);
}
