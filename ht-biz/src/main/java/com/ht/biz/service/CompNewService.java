package com.ht.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.solr.companypolicy.CompNew;

public interface CompNewService extends IService<CompNew>{
	public List<PageData> findlist(MyPage<PageData> page);
	public void editCompany(PageData pd);
	public void deleted(String id);
}
