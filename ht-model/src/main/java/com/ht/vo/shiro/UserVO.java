package com.ht.vo.shiro;

import java.util.Date;

public class UserVO {

	private String userId;

	private String username;// 登录名 唯一（手机/统一信用代码）
	
	private String password;//密码
	
	private String headimg;

	
	private String trueName;// 登录后显示名称

	private String email;// 电子邮件

	private String regip;// 注册时ip地址

	private String lastip;// 记录最后登录ip地址

	private Boolean active = true;// 1 可用 0 不可用 是否账号可用

	private Integer logincount;

	private String regcity;// 注册城市（辅助，通过ip地址拿取）

	private String regprovince;// 注册的省份（辅助，通过ip地址拿取）

	private String regarec;

	private String phone;

	private String linkman;// 联系人

	private String userType;

	private String regionId; // 所属区域
	
	private String token;
	
	private String roleid;
	
	private String companyid;
	
	private String cname;
	
	private Date createdate;
	
	private String remark;
	
	
	private String verificacode;//重新输入密码
	private String rolename;

	private String honey; //用户拥有的honey值

	public String getHoney() {
		return honey;
	}

	public void setHoney(String honey) {
		this.honey = honey;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegip() {
		return regip;
	}

	public void setRegip(String regip) {
		this.regip = regip;
	}

	public String getLastip() {
		return lastip;
	}

	public void setLastip(String lastip) {
		this.lastip = lastip;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getLogincount() {
		return logincount;
	}

	public void setLogincount(Integer logincount) {
		this.logincount = logincount;
	}

	public String getRegcity() {
		return regcity;
	}

	public void setRegcity(String regcity) {
		this.regcity = regcity;
	}


	public String getRegprovince() {
		return regprovince;
	}

	public void setRegprovince(String regprovince) {
		this.regprovince = regprovince;
	}

	public String getRegarec() {
		return regarec;
	}

	public void setRegarec(String regarec) {
		this.regarec = regarec;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	
	public String getVerificacode() {
		return verificacode;
	}

	public void setVerificacode(String verificacode) {
		this.verificacode = verificacode;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	

}
