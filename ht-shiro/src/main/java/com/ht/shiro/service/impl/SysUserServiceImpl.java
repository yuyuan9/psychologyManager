package com.ht.shiro.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.commons.support.ip.entity.TaoBaoIp;
import com.ht.commons.support.query.MyQueryBuilder;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.RequestUtils;
import com.ht.commons.utils.SimpleHashUtil;
import com.ht.commons.utils.StringUtil;
import com.ht.entity.shiro.SysUser;
import com.ht.entity.shiro.constants.UserType;
import com.ht.shiro.mapper.SysUserMapper;
import com.ht.shiro.service.SysUserService;
import com.ht.vo.shiro.UserVO;

import static com.ht.utils.GaodeRegionUtil.getAddrName;
import static com.ht.utils.GaodeRegionUtil.getPublicIP;


/**
 * Created by Administrator on 2018/1/12.
 */

@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public Page<UserVO> findList(MyPage mypage) throws Exception {
    	return (Page<UserVO>)sysUserMapper.findList(mypage);
	}
    
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
   	public Page<SysUser> findListpage(MyPage mypage) throws Exception {
    	QueryWrapper queryWrapper = new MyQueryBuilder(mypage.getPd()).builder();
    	
       	return (Page<SysUser>)baseMapper.selectPage(mypage, queryWrapper);
   	}
    
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void cancelUser(String userid) throws Exception {
    	baseMapper.cancelUser(userid);
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public int deleteUser(String userid) throws Exception {
		return baseMapper.deleteById(userid);
	}

   
	@Override
	public SysUser findByUserName(String username) throws Exception {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("username", username);
		return baseMapper.selectOne(queryWrapper);
	}

	public SysUser registerSysUser(SysUser sysUser,UserType userType) throws Exception {
		try {
//			TaoBaoIp tbip = RequestUtils.getTaoBaoIp();
			Map<String,String> mapip = getAddrName(getPublicIP());
			sysUser.setActive(true);
			sysUser.setCreatedate(new Date());
			sysUser.setPassword(SimpleHashUtil.convertEncryptionPwd(sysUser.getUsername(), sysUser.getPassword()));
			sysUser.setRegcity(mapip.get( "city" ));
			sysUser.setPhone(StringUtil.checkPhoneValue(sysUser.getUsername()));
			sysUser.setTrueName(StringUtil.getPhoneForUserName(sysUser.getUsername()));
			sysUser.setLastip(getPublicIP());
			sysUser.setRegip(getPublicIP());
			sysUser.setRegprovince(mapip.get( "province" ));
			sysUser.setUserType(userType.name());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return sysUser;
	}
	
	public SysUser editSysUser(UserVO userVo,UserType userType)throws Exception{
		SysUser sysUser = new SysUser();
		sysUser.setUserId(userVo.getUserId());
		sysUser.setTrueName(userVo.getTrueName());
		sysUser.setActive(userVo.getActive());
		sysUser.setCompanyid(userVo.getCompanyid());
		sysUser.setHeadImg(userVo.getHeadimg());
		sysUser.setEmail(userVo.getEmail());
		sysUser.setPhone(userVo.getPhone());
		sysUser.setUsername(userVo.getUsername());
		sysUser.setUserType(userType.name());
		sysUser.setRegprovince(userVo.getRegprovince());
		sysUser.setRemark(userVo.getRemark());
		sysUser.setRegcity(userVo.getRegcity());
		sysUser.setRoleid( userVo.getRoleid() );
		if(!StringUtils.isBlank(userVo.getPassword())) {
			sysUser.setPassword(SimpleHashUtil.convertEncryptionPwd(sysUser.getUsername(), sysUser.getPassword()));
		}
		
		return sysUser;
		
	}
	
	public SysUser registerCompanyUser(SysUser sysUser)throws Exception{
		return registerSysUser(sysUser,UserType.COMPANY_USER);
	}
	
	public SysUser registerDefaultUser(SysUser sysUser)throws Exception{
		return registerSysUser(sysUser,UserType.DEFAULT_USER);
	}

	@Override
	public SysUser findByPhone(String phone) throws Exception {
		QueryWrapper querywrapper = new QueryWrapper();
		querywrapper.eq("phone", phone);
		return baseMapper.selectOne(querywrapper);
	}

	public boolean update(SysUser entity) {
		UpdateWrapper updateWrapper = new UpdateWrapper();
		if(!StringUtils.isBlank(entity.getTrueName())) {
			updateWrapper.set("trueName", entity.getTrueName());
		}
		if(!StringUtils.isBlank(entity.getEmail())) {
			updateWrapper.set("email", entity.getEmail());
		}
		if(entity.getActive()!=null) {
			updateWrapper.set("active", entity.getActive());
		}
		if(!StringUtils.isBlank(entity.getPhone())) {
			updateWrapper.set("phone", entity.getPhone());
		}
		if(!StringUtils.isBlank(entity.getLinkman())) {
			updateWrapper.set("linkman", entity.getLinkman());
		}
		if(!StringUtils.isBlank(entity.getRegionId())) {
			updateWrapper.set("region_id", entity.getRegionId());
		}
		
		return super.update(entity, updateWrapper);
	}
	
	

	

	
}
