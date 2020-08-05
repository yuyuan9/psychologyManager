package com.ht.entity.policydig;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
@TableName("t_customer_send")
public class CustomerSend extends BaseEntity{
	
	@TableField(value="ip")
	private String ip;
	
	@TableField(value="packageId")
	private String packageId;
	
	@TableField(value="interfaceName")
	private String interfaceName;
	
	@TableField(value="customerId")
	private String customerId;
	
	public String getIp() {
		return ip;
	}
	public String getPackageId() {
		return packageId;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}
