package com.ht.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.entity.policydig.PTemplate;

public interface PTemplateMapper  extends BaseMapper<PTemplate>{
	public List<PTemplate> findlistPage(MyPage<PTemplate> mypage);
}
