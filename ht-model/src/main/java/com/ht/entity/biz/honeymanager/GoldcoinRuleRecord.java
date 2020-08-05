package com.ht.entity.biz.honeymanager;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;

/*
 * 金币奖励记录
 */
@TableName("t_gold_coin_rule_record")
public class GoldcoinRuleRecord extends BaseEntity {
	//规则id
	@TableField(value="goldcoinRuleId")
	private String goldcoinRuleId;
	//具体奖励金币值
	private Double golds=0d;
	//是否兑换过honey：//0表示没有兑换||1表示已兑换过
	private Boolean exchange=false;
	//奖励人
	@TableField(value="userId")
	private String userId;
	//备注
	private String remark;
	//目标id
	private String targetid;
	//流水单号
	private String flowcode;
	public String getGoldcoinRuleId() {
		return goldcoinRuleId;
	}
	public Boolean getExchange() {
		return exchange;
	}
	public String getUserId() {
		return userId;
	}
	public String getRemark() {
		return remark;
	}
	public void setGoldcoinRuleId(String goldcoinRuleId) {
		this.goldcoinRuleId = goldcoinRuleId;
	}
	public void setExchange(Boolean exchange) {
		this.exchange = exchange;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Double getGolds() {
		return golds;
	}
	public void setGolds(Double golds) {
		this.golds = golds;
	}
	public String getFlowcode() {
		return flowcode;
	}
	public void setFlowcode(String flowcode) {
		this.flowcode = flowcode;
	}
	public String getTargetid() {
		return targetid;
	}
	public void setTargetid(String targetid) {
		this.targetid = targetid;
	}
}
