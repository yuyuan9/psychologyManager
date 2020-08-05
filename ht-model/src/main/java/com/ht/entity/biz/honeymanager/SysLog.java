package com.ht.entity.biz.honeymanager;

import java.util.Date;

public class SysLog {
	private Integer id;
	private String userId;
	private String loginIp;
	private Date loginTime;
	private Date createtime;
	private String logtype;
	public Integer getId() {
		return id;
	}
	public String getUserId() {
		return userId;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public String getLogtype() {
		return logtype;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}
}
