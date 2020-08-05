package com.ht.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honeymanager.GoldcoinRuleRecord;

public interface GoldcoinRuleRecordService extends IService<GoldcoinRuleRecord> {
	public List<PageData> findGoldcoin(MyPage page);
	public Double findCounts(PageData pd);

	Double mygolds(String userId);
}
