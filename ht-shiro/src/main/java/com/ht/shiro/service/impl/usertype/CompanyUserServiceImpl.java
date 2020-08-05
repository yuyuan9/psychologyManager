package com.ht.shiro.service.impl.usertype;



import com.ht.commons.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.commons.support.query.MyQueryBuilder;
import com.ht.commons.utils.MyPage;
import com.ht.entity.shiro.usertype.CompanyUser;
import com.ht.shiro.mapper.SysUserMapper;
import com.ht.shiro.mapper.usertype.CompanyUserMapper;
import com.ht.shiro.service.usertype.CompanyUserService;
import com.ht.vo.shiro.CompanyUserVO;


/**
 * Created by Administrator on 2018/1/12.
 */
@Service
public class CompanyUserServiceImpl extends ServiceImpl<CompanyUserMapper,CompanyUser> implements CompanyUserService {

    @Autowired
	private CompanyUserMapper companyUserMapper;


	@Override
	public Page<PageData> findlist(MyPage mypage) throws Exception {
		return baseMapper.findlist(mypage);
	}

	@Override
	public CompanyUser findBycompregcode(String code) {
		QueryWrapper<CompanyUser>  ew = new QueryWrapper<CompanyUser>(  );
		ew.eq( "compregcode",code );
		return baseMapper.selectOne(ew );
	}

	@Override
	public PageData findById(PageData pd) {
		return baseMapper.findById(pd);
	}


}
