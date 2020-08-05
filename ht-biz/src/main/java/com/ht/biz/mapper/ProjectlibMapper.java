package com.ht.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.solr.projectlib.Projectlib;

public interface ProjectlibMapper extends BaseMapper<Projectlib>{
	public Integer reset(PageData pd);
	public Integer dealReset(PageData pd);
}
