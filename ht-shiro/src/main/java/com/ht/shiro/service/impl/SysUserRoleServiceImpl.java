package com.ht.shiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.commons.support.query.MyQueryBuilder;
import com.ht.commons.utils.MyPage;
import com.ht.entity.shiro.SysUserRole;
import com.ht.shiro.mapper.SysUserRoleMapper;
import com.ht.shiro.service.SysUserRoleService;


/**
 * Created by Administrator 
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper,SysUserRole> implements SysUserRoleService {

    
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public Page<SysUserRole> findListpage(MyPage mypage) throws Exception {
    	QueryWrapper query = new MyQueryBuilder(mypage.getPd()).builder();
    	return (Page<SysUserRole>)baseMapper.selectPage(mypage,  query);
	}

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public List<SysUserRole> findByUid(String userid) throws Exception {
    	QueryWrapper queryWrapper = new QueryWrapper();
    	queryWrapper.eq("userid", userid);
    	List<SysUserRole> list =baseMapper.selectList(queryWrapper);
		return list;
	}

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public SysUserRole findUserIdAndRoleId(String userid, Integer roleid) throws Exception {
    	QueryWrapper queryWrapper = new QueryWrapper();
    	queryWrapper.eq("userid", userid);
    	queryWrapper.eq("roleid", roleid);
    	SysUserRole sysuserrole =baseMapper.selectOne(queryWrapper);
		return sysuserrole;
	}
   


   
	

	
}
