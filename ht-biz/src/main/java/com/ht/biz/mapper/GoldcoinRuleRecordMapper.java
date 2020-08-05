package com.ht.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honeymanager.GoldcoinRuleRecord;

public interface GoldcoinRuleRecordMapper extends BaseMapper<GoldcoinRuleRecord>{
	public List<PageData> findGoldcoin(MyPage page);
	public Double findCounts(PageData pd);

    Double mygolds(String userId);
}
