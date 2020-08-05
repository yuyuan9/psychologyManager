package com.ht.utils;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.constants.Const;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.SpringContextUtil;
import com.ht.entity.biz.honeymanager.GoldcoinRule;
import com.ht.entity.biz.honeymanager.GoldcoinRuleRecord;
import com.ht.entity.biz.honeymanager.PaymentOrder;
import com.ht.entity.biz.honeymanager.RewardRule;
import com.ht.entity.biz.honeymanager.UserWaterReward;

/*
 * 奖励规则到后台开辟线程执行
 */
public class RewardUtil {
	public static void disHoney(RewardRule rr,String code,Double returnValue,String userId,String createId,String regionid){
		IService uwrService=(IService) SpringContextUtil.getBean("pointRedemptionService");
		IService rewardService=(IService) SpringContextUtil.getBean("rewardRuleService");
		if(rr==null){
			QueryWrapper<RewardRule> qw=new QueryWrapper<RewardRule>();
			qw.eq("code", code);
			rr=(RewardRule) rewardService.getOne(qw,false);
		}
		UserWaterReward uwr=new UserWaterReward();
		uwr.setRewardRuleId(rr.getId());
		uwr.setPoint(returnValue==null?Double.valueOf(rr.getReturnValue()):returnValue);
		uwr.setWaterType(0);
		uwr.setType(0);
		uwr.setUserId(userId);
		uwr.setCreateid(createId);
		uwr.setRegionid(regionid);
		uwr.setDeleted(false);
		uwr.setCreatedate(new Date());
		uwrService.save(uwr);
	}
	/*
	 * 添加honey值
	 * code 奖励规则code值
	 * returnValue 奖励honey值不一定按照奖励规则处理（若returnValue不为null按照returnValue处理）
	 * userId 被添加人id
	 * createId 操作人id
	 * 登录人regionid 地区id（可备注其他信息）
	 */
	public static void disHoney(String code,Double returnValue,String userId,String createId,String regionid){
		disHoney(null,code,returnValue,userId,createId,regionid);
	}
	/*
	 * 添加honey值
	 * rr 奖励规则信息提前传入
	 * userId 被添加人id
	 * createId 操作人id
	 * 登录人regionid 地区id（可备注其他信息）
	 */
	public static void disHoney(RewardRule rr,String userId,String createId,String regionid){
		disHoney(rr,null,null,userId,createId,regionid);
	}
	
	/*
	 * 检测用户登录天数奖励honey值
	 */
	public static Boolean getHoney(String userId){
		Boolean reward=false;
		IService uwrService=(IService) SpringContextUtil.getBean("pointRedemptionService");
		QueryWrapper<UserWaterReward> qw=new QueryWrapper<UserWaterReward>();
		qw.eq("userId", userId);
		qw.like("createdate", DateUtil.dateToStr(new Date(), 11));
		UserWaterReward uwr=(UserWaterReward) uwrService.getOne(qw, false);
		if(uwr!=null){
			reward=true;
		}
		return reward;
	}
	public static void disGoldcoin(GoldcoinRule gcr,String code,Double golds,String userId,String createId,String regionid,String remark,String targetid,String flowcode){
		IService goldcoinRuleRecordService=(IService) SpringContextUtil.getBean("goldcoinRuleRecordService");
		IService goldcoinRuleService=(IService) SpringContextUtil.getBean("goldcoinRuleService");
		if(gcr==null){
			QueryWrapper<GoldcoinRule> qw=new QueryWrapper<GoldcoinRule>();
			qw.eq("code", code);
			gcr=(GoldcoinRule) goldcoinRuleService.getOne(qw,false);
		}
		GoldcoinRuleRecord gcrr=new GoldcoinRuleRecord();
		gcrr.setGoldcoinRuleId(gcr.getId());
		gcrr.setGolds(golds==null?gcr.getGolds():golds);
		gcrr.setExchange(false);
		gcrr.setUserId(userId);
		gcrr.setRemark(remark);
		gcrr.setTargetid(targetid);
		gcrr.setCreatedate(new Date());
		gcrr.setCreateid(createId);
		gcrr.setRegionid(regionid);
		goldcoinRuleRecordService.save(gcrr);
	}
	
	/*
	 * 添加金币值
	 * code 奖励规则code值
	 * golds 奖励金币值不一定按照奖励规则处理（若golds不为null按照golds处理）
	 * userId 被添加人id
	 * createId 操作人id
	 * 登录人regionid 地区id（可备注其他信息）
	 * remark 添加备注，可忽略
	 * targetid 目标id
	 * flowcode 流水单号
	 */
	public static void disGoldcoin(String code,Double golds,String userId,String createId,String regionid,String remark){
		disGoldcoin(null,code,golds,userId,createId,regionid,remark,null,null);
	}
	public static void disGoldcoin(String code,Double golds,String userId,String createId,String regionid,String remark,String targetid,String flowcode){
		disGoldcoin(null,code,golds,userId,createId,regionid,remark,targetid,flowcode);
	}
	/*
	 * 添加金币值
	 * gcr 奖励规则信息提前传入
	 * userId 被添加人id
	 * createId 操作人id
	 * 登录人regionid 地区id（可备注其他信息）
	 * remark 添加备注，可忽略
	 * targetid 目标id
	 * flowcode 流水单号
	 */
	public static void disGoldcoin(GoldcoinRule gcr,String userId,String createId,String regionid,String remark){
		disGoldcoin(gcr,null,null,userId,createId,regionid,remark,null,null);
	}
	public static void disGoldcoin(GoldcoinRule gcr,String userId,String createId,String regionid,String remark,String targetid,String flowcode){
		disGoldcoin(gcr,null,null,userId,createId,regionid,remark,targetid,flowcode);
	}
	/*
	 * 添加支付订单
	 * PaymentOrder相关属性
	 */
	public static void addPaymentOrder(String paymentType,String businessType,String transactionNo,Double amount,Integer honeys,Integer status,String userId){
		IService paymentOrderService=(IService) SpringContextUtil.getBean("paymentOrderServiceImpl");
		PaymentOrder po=new PaymentOrder();
		po.setPaymentNo(Const.getOrderNo());
		po.setPaymentType(paymentType);
		po.setBusinessType(businessType);
		po.setTransactionNo(transactionNo);
		po.setAmount(amount);
		po.setHoneys(honeys);
		po.setStatus(status);
		po.setCreatedate(new Date());
		po.setCreateid(userId);
		po.setRegionid(userId);
		paymentOrderService.save(po);
	}
}
