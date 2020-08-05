package com.ht.entity.policydig;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
@TableName("t_policy_package_send")
public class PolicyPackageSend extends BaseEntity{
	
	@TableField(value="packageId")
	private String packageId;
	
	@TableField(value="customerId")
	private String customerId;
	
	@TableField(value="sendStatus")
	private Integer sendStatus=0;//0失败1成功
	
	@TableField(value="sendDate")
	private Date sendDate;
	
	@TableField(value="pTemplateId")
	private String pTemplateId;
	
	@TableField(value="message")
	private String message;//发送错误信息
	
	@TableField(value="msgcode")
	private String msgcode;//发送错误码
	public String getPackageId() {
		return packageId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public Integer getSendStatus() {
		return sendStatus;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public String getpTemplateId() {
		return pTemplateId;
	}
	public String getMessage() {
		return message;
	}
	public String getMsgcode() {
		return msgcode;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public void setpTemplateId(String pTemplateId) {
		this.pTemplateId = pTemplateId;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setMsgcode(String msgcode) {
		this.msgcode = msgcode;
	}
}
