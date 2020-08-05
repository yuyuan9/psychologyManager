package com.ht.entity.shiro.usertype;

import com.ht.entity.base.BaseEntity;

/**
 * 专家关联用户类
 * @author jied
 *
 */
public class Expert extends BaseEntity{
	
	private String userId;//外键 用户表

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
