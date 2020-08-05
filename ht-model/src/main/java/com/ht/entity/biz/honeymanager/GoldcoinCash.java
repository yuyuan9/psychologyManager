package com.ht.entity.biz.honeymanager;

import com.ht.entity.base.BaseEntity;
//金币提现记录
public class GoldcoinCash extends BaseEntity{
	//金币余额
	private Double golds=0d;
	//提现金币
	private Double cashGolds=0d;
	//提现类型(1银行卡)
	private Integer cashType=1;
	//缴税额
	private Double taxpaid=0d;
	//提现人民币
	private Double rmb=0d;
	//银行卡号
	private String bankcard;
	//账户银行
	private String bankName;
	//收款人姓名
	private String payeeName;
	//用户账号
	private String userAccount;
	//用户名称
	private String username;
	//手机号
	private String phone;
	//转账单号
	private String TransferNumber;
	//状态(1待处理2处理中3转账成功4转账失败)
	private Integer status=1;
	//转账失败原因
	private String failreson;
	public Double getGolds() {
		return golds;
	}
	public Double getCashGolds() {
		return cashGolds;
	}
	public Integer getCashType() {
		return cashType;
	}
	public Double getTaxpaid() {
		return taxpaid;
	}
	public Double getRmb() {
		return rmb;
	}
	public String getBankcard() {
		return bankcard;
	}
	public String getBankName() {
		return bankName;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public String getUsername() {
		return username;
	}
	public String getPhone() {
		return phone;
	}
	public String getTransferNumber() {
		return TransferNumber;
	}
	public Integer getStatus() {
		return status;
	}
	public String getFailreson() {
		return failreson;
	}
	public void setGolds(Double golds) {
		this.golds = golds;
	}
	public void setCashGolds(Double cashGolds) {
		this.cashGolds = cashGolds;
	}
	public void setCashType(Integer cashType) {
		this.cashType = cashType;
	}
	public void setTaxpaid(Double taxpaid) {
		this.taxpaid = taxpaid;
	}
	public void setRmb(Double rmb) {
		this.rmb = rmb;
	}
	public void setBankcard(String bankcard) {
		this.bankcard = bankcard;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setTransferNumber(String transferNumber) {
		TransferNumber = transferNumber;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setFailreson(String failreson) {
		this.failreson = failreson;
	}
	
}
