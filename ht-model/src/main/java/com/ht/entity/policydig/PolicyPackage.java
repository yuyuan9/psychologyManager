package com.ht.entity.policydig;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;

@TableName("t_policy_package")
public class PolicyPackage extends BaseEntity{
	/*
	 * 政策包名
	 */
	@TableField(value="name")
	private String name;
	
	@TableField(value="templateMsgId")
	private String templateMsgId;//短信模板
	
	@TableField(value="templateEmailId")
	private String templateEmailId;//邮箱模板
	
	@TableField(value="email")
	private Boolean email=false;//是否邮箱
	
	@TableField(value="msg")
	private Boolean msg=false;//是否短信
	
	@TableField(value="send")
	private Boolean send=false;//是否定时发送
	
	@TableField(value="sendTime")
	private Date sendTime;//发送时间
	
	@TableField(value="province")
	private String province;//省
	
	@TableField(value="city")
	private String city;//市
	
	@TableField(value="area")
	private String area;//区
	
	@TableField(value="shortUrl")
	private String shortUrl;//短信短码
	
	@TableField(value="status")
	private Integer status=3;//3未发送1全部发送成功2部分用户发送失败
	
	@TableField(exist=false)
	private String policydigIds;//关联政策ids
	
	@TableField(exist=false)
	private String customerIds;//关联客户ids
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplateMsgId() {
		return templateMsgId;
	}

	public void setTemplateMsgId(String templateMsgId) {
		this.templateMsgId = templateMsgId;
	}

	public String getTemplateEmailId() {
		return templateEmailId;
	}

	public void setTemplateEmailId(String templateEmailId) {
		this.templateEmailId = templateEmailId;
	}

	public Boolean getEmail() {
		return email;
	}

	public void setEmail(Boolean email) {
		this.email = email;
	}

	public Boolean getMsg() {
		return msg;
	}

	public void setMsg(Boolean msg) {
		this.msg = msg;
	}

	public Boolean getSend() {
		return send;
	}

	public void setSend(Boolean send) {
		this.send = send;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getProvince() {
		return province;
	}

	public String getCity() {
		return city;
	}

	public String getArea() {
		return area;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPolicydigIds() {
		return policydigIds;
	}

	public String getCustomerIds() {
		return customerIds;
	}

	public void setPolicydigIds(String policydigIds) {
		this.policydigIds = policydigIds;
	}

	public void setCustomerIds(String customerIds) {
		this.customerIds = customerIds;
	}
}
