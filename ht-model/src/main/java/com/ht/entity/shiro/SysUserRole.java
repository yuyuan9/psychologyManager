package com.ht.entity.shiro;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 用户角色表
 * @author jied
 *
 */
@TableName(value="t_sys_user_role")
public class SysUserRole {
	
	 @TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	
    private String userid;
    
    private Integer roleid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	
    
}
