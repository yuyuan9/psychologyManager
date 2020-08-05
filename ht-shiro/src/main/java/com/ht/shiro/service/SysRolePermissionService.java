package com.ht.shiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.entity.shiro.SysRolePermission;

public interface SysRolePermissionService extends IService<SysRolePermission>{
	
	boolean saveRolePermission(String roleid,String ...permissionid)throws Exception;
	

}
