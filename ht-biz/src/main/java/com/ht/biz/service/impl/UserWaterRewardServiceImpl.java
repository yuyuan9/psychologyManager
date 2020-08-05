package com.ht.biz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.UserWaterRewardMapper;
import com.ht.biz.service.PointRedemptionService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honeymanager.UserWaterReward;

import java.util.List;

import org.springframework.stereotype.Service;


@Service("pointRedemptionService")
public class UserWaterRewardServiceImpl extends ServiceImpl<UserWaterRewardMapper, UserWaterReward> implements PointRedemptionService {


	@Override
	public Double getWaterTotal(String userId) {
		return baseMapper.getWaterTotal(userId);
	}

	@Override
	public List<PageData> findList(MyPage page) {
		// TODO Auto-generated method stub
		return baseMapper.findList(page);
	}

	@Override
	public Integer findUserIdByDayCount(PageData pd) {
		// TODO Auto-generated method stub
		return baseMapper.findUserIdByDayCount(pd);
	}

	@Override
	public boolean findByUserId(String userId, String rewardRuleId) throws Exception {
		// TODO Auto-generated method stub
		return findCondition(userId,rewardRuleId,null,null);
	}

	@Override
	public boolean findCondition(String userId, String rewardRuleId, String starttime, String endtime)
			throws Exception {
		// TODO Auto-generated method stub
		PageData pd =new PageData();
		pd.put("userId", userId);
		pd.put("starttime", starttime);
		pd.put("endtime", endtime);
		pd.put("rewardRuleId", rewardRuleId);
		Integer count =baseMapper.findUserIdByDayCount(pd);
		if(count==null||count==0){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public List<PageData> selectRewrd(MyPage page) {
		// TODO Auto-generated method stub
		return baseMapper.selectRewrd(page);
	}
}
