package com.ht.shiro.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.commons.support.query.MyQueryBuilder;
import com.ht.commons.utils.MyPage;
import com.ht.entity.shiro.SysRole;
import com.ht.shiro.mapper.SysRoleMapper;
import com.ht.shiro.service.SysRoleService;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huitu123
 * @since 2018-01-23
 */
@Service
public  class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public Page<SysRole> findListpage(MyPage page) throws Exception {
		/*QueryWrapper queryWrapper = new QueryWrapper();
    	queryWrapper.orderByDesc("createdate");*/
		QueryWrapper queryWrapper = new MyQueryBuilder(null,page.getPd()).builder();
    	return (Page<SysRole>)baseMapper.selectPage(page, queryWrapper);
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public boolean update(SysRole role) throws Exception {
		int update=baseMapper.updateById(role);
		return update>0?true:false;
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public boolean deleted(Integer id) throws Exception {
		int check=baseMapper.deleteById(id);
		return check>0?true:false;
	}

	@Override
	public boolean save(SysRole entity) {
		entity.setCreatedate(new Date());
		return super.save(entity);
	}

	
	
	

    
}
