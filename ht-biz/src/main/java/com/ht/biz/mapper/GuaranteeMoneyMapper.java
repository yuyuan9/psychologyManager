package com.ht.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.product.GuaranteeMoney;

public interface GuaranteeMoneyMapper extends BaseMapper<GuaranteeMoney>{
	public List<PageData> findlist(MyPage page);
	public GuaranteeMoney getoneById(String createid);
	public void updateStatus(PageData pd);
	public List<GuaranteeMoney> findusergtm(MyPage page);
	public List<GuaranteeMoney> quarzlist(PageData pd);
}
