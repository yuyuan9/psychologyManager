package com.ht.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.policymatch.PolicyMatch;

public interface PolicyMatchMapper extends BaseMapper<PolicyMatch>{
	public List<PolicyMatch> findlist(MyPage page);
	public PolicyMatch findById(PageData pd);
}
