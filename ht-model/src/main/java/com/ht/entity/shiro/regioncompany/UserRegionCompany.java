package com.ht.entity.shiro.regioncompany;

import com.ht.entity.base.BaseEntity;

/**
 * 用户区域分公司中间表
 * @author jied
 *
 */
public class UserRegionCompany extends BaseEntity{
	
	private String regioncompanyid;
	
	private String userid;

	public String getRegioncompanyid() {
		return regioncompanyid;
	}

	public void setRegioncompanyid(String regioncompanyid) {
		this.regioncompanyid = regioncompanyid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	

}
