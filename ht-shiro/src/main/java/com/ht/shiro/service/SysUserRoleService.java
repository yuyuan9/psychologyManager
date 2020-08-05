package com.ht.shiro.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.entity.shiro.SysUserRole;

/**
 * Created by Administrator
 */
public interface SysUserRoleService extends IService<SysUserRole>{
	
	List<SysUserRole> findByUid(String userid)throws Exception;
	
	SysUserRole findUserIdAndRoleId(String userid,Integer roleid)throws Exception;
	
}
