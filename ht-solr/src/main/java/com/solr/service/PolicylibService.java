package com.solr.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.entity.biz.solr.policylib.Policylib;

public interface PolicylibService extends IService<Policylib>{
	public List<Policylib> findlistPage(MyPage page);
}
