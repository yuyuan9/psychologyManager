package com.ht.shiro.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.entity.shiro.SysRolePermission;
import com.ht.shiro.mapper.SysRolePermissionMapper;
import com.ht.shiro.service.SysRolePermissionService;

@Service
public class SysRolePermissionImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {

	@Override
	public boolean saveRolePermission(String roleid, String... permissionid) throws Exception {
		//删除roleid下所有资源
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("roleid", roleid);
		super.remove(queryWrapper);
		
		List<SysRolePermission> list = new ArrayList<SysRolePermission>();
		SysRolePermission srp=null;
		for(String perid:permissionid) {
			srp=new SysRolePermission();
			srp.setRoleid(Integer.valueOf(roleid));
			srp.setSysPermissionId(Integer.valueOf(perid));
			list.add(srp);
		}
		return super.saveBatch(list);
	}
   
}
