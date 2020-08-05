package com.ht.entity.biz.honeymanager;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;


/*
 * 支付订单类
 */
@TableName("t_paymentorder")
public class PaymentOrder extends BaseEntity{
	
	/*
	 * 订单编号
	 */
	@TableField(value="orderNo")
	private String orderNo;
	
	/*
	 * 支付订单编号
	 */
	@TableField(value="paymentNo")
	private String paymentNo;
	
	/*
	 * 支付类型
	 * 支付宝，银联等
	 * alipay:支付宝
	 * WeChat：微信
	 * 银联
	 * 线下支付
	 */
	@TableField(value="paymentType")
	private String paymentType;
	
	/*
	 * 支付金额
	 */
	@TableField(value="amount")
	private Double amount=0d; 
	
	/*
	 * 业务类型（直接保存表名）
	 */
	@TableField(value="businessType")
	private String businessType;
	
	/*
	 * 业务id（直接记录表中记录id） 以便支付完成后更新表记录
	 */
	@TableField(value="businessId")
	private String businessId;
	
	/*
	 * 支付状态
	 * 0  已下单  未支付   1 已支付
	 */
	@TableField(value="status")
	private Integer status=0;

	/**
	 * 支付标题
	 */
	@TableField(value="paymenTtitle")
	private String paymenTtitle;
	
	//交易订单号
	@TableField(value="transactionNo")
	private String transactionNo;
	
	//支付日期
	@TableField(value="paymentDate")
	private Date paymentDate;
	//支付金额
	@TableField(value="paymentAmount")
	private Double paymentAmount=0d;
	
	//下线支付手机号码联系对方
	@TableField(exist=false)
	private String phone;
	
	
	//1:预付款|2：尾款|3：保证金|4：honey充值
	@TableField(value="paymentCash")
	private String paymentCash;
	
	//充值honey
	@TableField(value="honeys")
	private Integer honeys;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPaymenTtitle() {
		return paymenTtitle;
	}

	public void setPaymenTtitle(String paymenTtitle) {
		this.paymenTtitle = paymenTtitle;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getPaymentCash() {
		return paymentCash;
	}

	public void setPaymentCash(String paymentCash) {
		this.paymentCash = paymentCash;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getHoneys() {
		return honeys;
	}

	public void setHoneys(Integer honeys) {
		this.honeys = honeys;
	}

	public enum bustype{
		t_facilitator("t_facilitator","加入保障计划"),
		t_sys_service("t_sys_service","加入保障计划"),
		t_order("t_order","订单"),
		t_recharge("t_recharge","充值${honeys}honey"),
		t_paymentorder("t_paymentorder","支付"),
		amou("amou","其他"),
		;
		
		public String name;
		public String value;
		bustype(String name,String value){
			this.name=name;
			this.value=value;
		}
		public String getName() {
			return name;
		}
		public String getValue() {
			return value;
		}
		public static String getValue(String value,String suffix,String str) {
				value=value.replace(suffix, str);
				return value;
		}
		
	}
	public static void main(String[] args) {
		System.out.println(bustype.valueOf("t_facilitator"));
		System.out.println(bustype.valueOf("t_facilitator").value);
	}
}
