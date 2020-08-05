package com.ht.entity.biz.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/*
 * 科技型中小企业名单详细信息登记表
 */
@TableName(value = "t_smalltech_rosterinfo")
public class Rosterinfo {
    @TableId(type=IdType.AUTO)
	private int id;
	@TableField(value="roster_id")
	private int rosterId;//外键id

	private String enterprise; //企业名称

	private String address;//注册地址

	private String  registernum;//入库登记号


	private String revokeinfo ;//注销方式

	private String  socialcode; //统一社会信用代码


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRosterId() {
		return rosterId;
	}

	public void setRosterId(int rosterId) {
		this.rosterId = rosterId;
	}

	public String getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRegisternum() {
		return registernum;
	}

	public void setRegisternum(String registernum) {
		this.registernum = registernum;
	}


	public String getRevokeinfo() {
		return revokeinfo;
	}

	public void setRevokeinfo(String revokeinfo) {
		this.revokeinfo = revokeinfo;
	}

	public String getSocialcode() {
		return socialcode;
	}

	public void setSocialcode(String socialcode) {
		this.socialcode = socialcode;
	}
}
