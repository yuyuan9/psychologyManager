package com.ht.shiro.service;


import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.entity.shiro.SysPermission;
import com.ht.vo.shiro.AdminMenuVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huitu123
 * @since 2018-01-23
 */
public interface SysPermissionService extends IService<SysPermission> {

	Page<SysPermission> findListpage(MyPage mypage);
	
	List<SysPermission> findList(MyPage mypage);
	
	List<SysPermission> findPermsByRoleId(Integer  roleid);
	List<SysPermission> findallPermsByRoleId(Integer  roleid);
	/**
	 * 根据个人id查询，所有菜单
	 * @param userid
	 * @return
	 */
	List<SysPermission> findPermsMenuByUserId(String userid);
	
	
	List<AdminMenuVO> convertTreeMenu(List<SysPermission> list);

	List<SysPermission> findbuttonPermsByRoleId(Integer roleid);
	
	/**
	 * 根据用户id 查询所有资源
	 * @param userid
	 * @return
	 */
	//List<SysPermission> findPermsByUserId(String userid);

}
