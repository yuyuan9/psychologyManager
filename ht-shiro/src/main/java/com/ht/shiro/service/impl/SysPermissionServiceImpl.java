package com.ht.shiro.service.impl;


import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.commons.support.query.MyQueryBuilder;
import com.ht.commons.utils.MyPage;
import com.ht.entity.shiro.SysPermission;
import com.ht.shiro.mapper.SysPermissionMapper;
import com.ht.shiro.service.SysPermissionService;
import com.ht.vo.shiro.AdminMenuVO;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huitu123
 * @since 2018-01-23
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

	@Override
	public boolean save(SysPermission entity) {
		if(entity.getId()==null) {
			entity.setCreatedate(new Date());
			entity.setCreateid(null);
		}else {
			entity.setLastmodified(new Date());
		}
		return super.save(entity);
	}

	@Override
	public Page<SysPermission> findListpage(MyPage mypage) {
		/*QueryWrapper queryWrapper = new QueryWrapper();
    	queryWrapper.orderByDesc("createdate");*/
		QueryWrapper queryWrapper = new MyQueryBuilder(mypage.getPd()).builder();
    	return (Page<SysPermission>)baseMapper.selectPage(mypage, queryWrapper);
	}

	@Override
	public List<SysPermission> findPermsByRoleId(Integer roleid) {
		return baseMapper.findPermsByRoleId(roleid);
	}

	@Override
	public List<SysPermission> findallPermsByRoleId(Integer roleid) {
		return baseMapper.findallPermsByRoleId(roleid);
	}

	@Override
	public List<SysPermission> findbuttonPermsByRoleId(Integer roleid) {
		return baseMapper.findbuttonPermsByRoleId(roleid);
	}


	public List<SysPermission> findPermsMenuByUserId(String userid){
		return baseMapper.findPermsMenuByUserId(userid);
	}

	@Override
	public List<AdminMenuVO> convertTreeMenu(List<SysPermission> list) {
		List<AdminMenuVO> listvo= new ArrayList<AdminMenuVO>();
		for(SysPermission sp: list) {
			AdminMenuVO vo = new AdminMenuVO();
			BeanUtils.copyProperties(sp, vo);
			vo.getMeta().setIcon(sp.getIcon());
			vo.getMeta().setTitle(sp.getTitle());
			listvo.add(vo);
		}
		
		return listvo;
	}


	@Override
	public List<SysPermission> findList(MyPage mypage) {
		mypage.setSize(Long.MAX_VALUE);
		return (List<SysPermission>)baseMapper.findList(mypage);
	}
	
	

  
}
