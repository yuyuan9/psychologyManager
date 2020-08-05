package com.ht.shiro.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.shiro.SysUser;
import com.ht.vo.shiro.UserVO;

/**
 * Created by Administrator on 2018/1/10.
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
	
	void cancelUser(String userid)throws Exception;
	
	Page<UserVO> findList (MyPage page)throws Exception;
}
