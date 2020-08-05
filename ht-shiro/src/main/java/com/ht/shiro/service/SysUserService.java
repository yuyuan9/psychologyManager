package com.ht.shiro.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.shiro.SysUser;
import com.ht.entity.shiro.constants.UserType;
import com.ht.vo.shiro.UserVO;

/**
 * Created by Administrator on 2018/1/12.
 */
public interface SysUserService extends IService<SysUser>{
	
	/**
	 * 查询用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<SysUser> findListpage(MyPage mypage)throws Exception;
	
	Page<UserVO> findList(MyPage mypage)throws Exception;
	
	
	
	/**
	 * 注销用户
	 * @param userid
	 * @throws Exception
	 */
	void cancelUser(String userid)throws Exception;
	
	/**
	 * 删除用户
	 * @param userid
	 * @throws Exception
	 */
	int deleteUser(String userid)throws Exception;
	
	/**
	 * 根据用户名查找用户对象
	 * @param username
	 * @return
	 * @throws Exception
	 */
	SysUser findByUserName(String username)throws Exception;
	
	/**
	 * 根据手机号码查询用户
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	SysUser findByPhone(String phone)throws Exception;
	
	
	/**
	 * 注册用户
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	SysUser registerSysUser(SysUser sysUser,UserType userType)throws Exception;
	
	SysUser editSysUser(UserVO userVo,UserType userType)throws Exception;
	//注册企业用户
	SysUser registerCompanyUser(SysUser sysUser)throws Exception;
	//注册普通用户
	SysUser registerDefaultUser(SysUser sysUser)throws Exception;
	
	
    boolean update(SysUser sysUser)throws Exception;	
	


}
