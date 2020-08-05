package com.ht.shiro.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.shiro.mapper.SysLogMapper;
import com.ht.shiro.service.SysLogService;
import com.ht.utils.RewardUtil;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.SpringContextUtil;
import com.ht.entity.biz.honeymanager.RewardRule.Code;
import com.ht.entity.biz.honeymanager.RewardRule;
import com.ht.entity.biz.honeymanager.SysLog;
import com.ht.entity.biz.honeymanager.UserWaterReward;

@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

	@Override
	public void save(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		baseMapper.save(pd);
	}

	@Override
	public Integer findlistPage() throws Exception {
		// TODO Auto-generated method stub
		return baseMapper.findlistPage();
	}

	@Override
	public Integer findLoginDay(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return baseMapper.findLoginDay(pd);
	}

	@Override
	public boolean findLogin10Day(String userId) throws Exception {
		// TODO Auto-generated method stub
		PageData pd = getLoginDayPD(userId, 10);
		Integer count=findLoginDay(pd);
		count=count==null?0:count;
		if(count!=null && count==10){
    		return true;
    	}else{
    		return false;
    	}
	}

	@Override
	public boolean findLogin20Day(String userId) throws Exception {
		// TODO Auto-generated method stub
		PageData pd = getLoginDayPD(userId, 20);
		Integer count=findLoginDay(pd);
		count=count==null?0:count;
		if(count!=null && count==20){
    		return true;
    	}else{
    		return false;
    	}
	}

	@Override
	public boolean findLogin365Day(String userId) throws Exception {
		// TODO Auto-generated method stub
		PageData pd = getLoginDayPD(userId, 365);
		Integer count=findLoginDay(pd);
		count=count==null?0:count;
		if(count!=null && count==365){
    		return true;
    	}else{
    		return false;
    	}
	}

	@Override
	public PageData getLoginDayPD(String userId, int day) throws Exception {
		// TODO Auto-generated method stub
		PageData pd = new PageData();
    	pd.put("userId", userId);
        pd.put("starttime", DateUtil.getBeforeDay(day,"00:00:00"));
        pd.put("endtime", DateUtil.getBeforeDay(0,"23:59:59"));
		return pd;
	}

	@Override
	public void setLoginUser(String userId) throws Exception {
		// TODO Auto-generated method stub
		//UserWaterReward uwr=new UserWaterReward();
		
		IService uwrService=(IService) SpringContextUtil.getBean("pointRedemptionService");
		IService rewardService=(IService) SpringContextUtil.getBean("rewardRuleService");
		
		boolean qw10check =findLogin10Day(userId);
		QueryWrapper<RewardRule> qw110=new QueryWrapper<RewardRule>();
		qw110.eq("code", Code.login_day_total_10);
		RewardRule d110=(RewardRule) rewardService.getOne(qw110);
		QueryWrapper<UserWaterReward> qw10=new QueryWrapper<UserWaterReward>();
		qw10.eq("userId", userId);qw10.eq("rewardRuleId", d110.getId());
		Integer q10=uwrService.count(qw10);
		q10=q10==null?0:q10;
		if(qw10check&&q10==0){
			RewardUtil.disHoney(String.valueOf(Code.login_day_total_10), null, userId, userId, null);
		}
		
		boolean qw20check =findLogin20Day(userId);
		QueryWrapper<RewardRule> qw220=new QueryWrapper<RewardRule>();
		qw220.eq("code", Code.login_day_total_20);
		RewardRule d220=(RewardRule) rewardService.getOne(qw220);
		QueryWrapper<UserWaterReward> qw20=new QueryWrapper<UserWaterReward>();
		qw20.eq("userId", userId);qw20.eq("rewardRuleId", d220.getId());
		Integer q20=uwrService.count(qw20);
		q20=q20==null?0:q20;
		if(qw20check&&q20==0){
			RewardUtil.disHoney(String.valueOf(Code.login_day_total_20), null, userId, userId, null);
		}
		
		boolean qw365check =findLogin365Day(userId);
		QueryWrapper<RewardRule> qw3650=new QueryWrapper<RewardRule>();
		qw3650.eq("code", Code.login_day_total_365);
		RewardRule d3650=(RewardRule) rewardService.getOne(qw3650);
		QueryWrapper<UserWaterReward> qw365=new QueryWrapper<UserWaterReward>();
		qw365.eq("userId", userId);qw365.eq("rewardRuleId", d3650.getId());
		Integer q365=uwrService.count(qw365);
		q365=q365==null?0:q365;
		if(qw365check&&q365==0){
			RewardUtil.disHoney(String.valueOf(Code.login_day_total_365), null, userId, userId, null);
		}
		//boolean checkwater =uwrService.findByUserId(userId, Code.login_day_total_10);
	}


}
