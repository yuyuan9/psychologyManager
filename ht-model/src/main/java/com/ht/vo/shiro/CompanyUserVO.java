package com.ht.vo.shiro;

import com.ht.entity.shiro.usertype.CompanyUser;

public class CompanyUserVO extends CompanyUser{
	
	private String phone;
	
	private String email;
	
	private String username;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	

}
