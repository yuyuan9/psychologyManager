package com.ht.utils;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.SpringContextUtil;
import com.ht.entity.biz.msg.WorkReminder;
import com.ht.entity.biz.msg.WorkReminderRecord;

public class MsgUtil {
	public static void save(String name,String work,String url,String userId,String createId,String regionid){
		IService wrService=(IService) SpringContextUtil.getBean("workReminderService");
		IService wrrService=(IService) SpringContextUtil.getBean("workReminderRecordService");
		WorkReminderRecord wrr=new WorkReminderRecord();
		QueryWrapper<WorkReminder> qw=new QueryWrapper<WorkReminder>();
		qw.eq("code", work);
		WorkReminder wr=(WorkReminder) wrService.getOne(qw, false);
		wrr.setCode(wr.getCode());
		String expression=wr.getExpression();
		if(StringUtils.isNotBlank(url)){
			expression=expression.replace("${url}", url);
		}
		if(StringUtils.isNotBlank(name)){
			expression=expression.replace("${name}", name);
		}
		wrr.setContent(expression);
		wrr.setUserId(userId);
		wrr.setRead0(0);
		wrr.setTargetId(wr.getId());
		wrr.setDeleted(0);
		wrr.setCreatedate(new Date());
		wrr.setCreateid(createId);
		wrr.setRegionid(regionid);
		wrrService.save(wrr);
	}
	/*
	 * 添加通知消息
	 * name 指定名称替换
	 * work 消息规则
	 * userId 被添加人id
	 * url 用于生成页面超链接
	 * createId 操作人id
	 * 登录人regionid 地区id
	 */
	public static void addMsg(String work,String url,String userId,String createId,String regionid){
		save(null,work,url,userId,createId,regionid);
	}
	public static void addMsg(String name,String work,String url,String userId,String createId,String regionid){
		save(name,work,url,userId,createId,regionid);
	}
}
