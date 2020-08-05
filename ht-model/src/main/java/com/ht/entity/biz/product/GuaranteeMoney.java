package com.ht.entity.biz.product;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.entity.base.BaseEntity;

//保障金管理
@TableName("t_guarantee_money")
public class GuaranteeMoney extends BaseEntity{
	private String project;//项目
	private Double cast=0d;//费用
	private Integer status=2;//状态(1保障中，2已过期,3未支付 )
	//保障时间
	@TableField(value="beginDate")
	private Date beginDate;//开始时间
	@TableField(value="endDate")
	private Date endDate;//结束时间
	@TableField(value="paymentOrderId")
	private String paymentOrderId;//对应支付id
	@TableField(exist=false)
	private Integer days=0;//时间差（计算天数）
	public String getProject() {
		return project;
	}
	public Double getCast() {
		return cast;
	}
	public Integer getStatus() {
		return status;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public void setCast(Double cast) {
		this.cast = cast;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getPaymentOrderId() {
		return paymentOrderId;
	}
	public void setPaymentOrderId(String paymentOrderId) {
		this.paymentOrderId = paymentOrderId;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public static void main(String[] args) {
		long l=DateUtil.getDaySub("2019-12-03", "2017-12-03");
		System.out.println(l);
	}
}
