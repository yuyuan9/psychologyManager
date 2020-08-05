package com.ht.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.solr.companypolicy.CompNew;

public interface CompNewMapper extends BaseMapper<CompNew> {
	public List<PageData> findlist(MyPage<PageData> page);
	public void editCompany(PageData pd);
	public void deleted(String id);
}
