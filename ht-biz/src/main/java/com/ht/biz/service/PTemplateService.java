package com.ht.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.entity.policydig.PTemplate;

public interface PTemplateService extends IService<PTemplate>{
	public List<PTemplate> findlistPage(MyPage<PTemplate> mypage);
	PTemplate findById(String id);
	void insert(PTemplate pTemplate);
    void edit(PTemplate pTemplate);
    void deleted(String id);
}
