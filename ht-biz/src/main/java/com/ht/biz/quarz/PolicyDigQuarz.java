package com.ht.biz.quarz;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.PolicyDigService;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.entity.policydig.PolicyDig;

@Component
@EnableScheduling
public class PolicyDigQuarz {
	
	@Autowired
	private PolicyDigService policyDigService;
	
	public void task(){
		QueryWrapper<PolicyDig> qw=new QueryWrapper<PolicyDig>();
		qw.lt("datetime", DateUtil.dateToStr(DateUtil.addDay(new Date(), -7), 11)+" 00:00:00");
		qw.eq("status", 2);//清除回收站数据
		policyDigService.remove(qw);
	}
}
