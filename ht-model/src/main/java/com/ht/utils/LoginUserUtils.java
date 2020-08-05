package com.ht.utils;

import java.io.Serializable;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.SpringContextUtil;
import com.ht.entity.shiro.SysUser;
import com.ht.vo.shiro.UserVO;

public class LoginUserUtils {
	
	public static SysUser getLoginUser() {
		 Subject subject = SecurityUtils.getSubject();
		// SysUser user=(SysUser)subject.getPrincipal();

		 Object obj=subject.getPrincipal();
		 if(null!=obj){
			 SysUser sysUser = new SysUser();
			 if(obj instanceof SysUser) {
				 sysUser = (SysUser) obj;
			 } else {
				 sysUser = JSON.parseObject(JSON.toJSON(obj).toString(), SysUser.class);
			 }
			 return sysUser;
		 }
		return null;
	}
	
	public static UserVO getUserVO(Serializable token) {
		UserVO vo = new UserVO();
		SysUser loginUser = getLoginUser();
		BeanUtils.copyProperties(loginUser, vo);
		vo.setToken((String)token);
		return vo;
	}
	
	public static String getUserId() {
		return getLoginUser().getUserId();
	}
	
	public static void serUser(){
		String userId=getUserId();
		IService uwrService=(IService) SpringContextUtil.getBean("sysUserService");
		SysUser user=(SysUser) uwrService.getById(userId);
		Subject subject = SecurityUtils.getSubject();
    	PrincipalCollection principalCollection = subject.getPrincipals(); 
    	String realmName = principalCollection.getRealmNames().iterator().next();
    	PrincipalCollection newPrincipalCollection = 
    			new SimplePrincipalCollection(user, realmName);
    	subject.runAs(newPrincipalCollection);
	}

}
