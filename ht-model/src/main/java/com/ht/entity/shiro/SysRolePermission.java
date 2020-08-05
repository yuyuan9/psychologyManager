package com.ht.entity.shiro;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 *  角色权限中间表
 */
@TableName(value="t_sys_role_permission")
public class SysRolePermission {

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	
	@TableField(value="roleid")
	private Integer roleid;
	
	@TableField(value="sysPermissionId")
	private Integer sysPermissionId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public Integer getSysPermissionId() {
		return sysPermissionId;
	}

	public void setSysPermissionId(Integer sysPermissionId) {
		this.sysPermissionId = sysPermissionId;
	}
	
	
}
