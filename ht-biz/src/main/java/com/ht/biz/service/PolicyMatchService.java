package com.ht.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.policymatch.PolicyMatch;

public interface PolicyMatchService extends IService<PolicyMatch>{
	public List<PolicyMatch> findlist(MyPage page);
	public PolicyMatch findById(PageData pd);
	public String getSql(String province,String city,String area);
}
