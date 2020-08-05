package com.ht.shiro.service.impl.usertype;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.commons.utils.MyPage;
import com.ht.entity.shiro.usertype.CompanyBank;
import com.ht.shiro.mapper.usertype.CompanyBankMapper;
import com.ht.shiro.service.usertype.CompanyBankService;


/**
 * Created by Administrator on 2018/1/12.
 */
@Service
public class CompanyBankServiceImpl extends ServiceImpl<CompanyBankMapper,CompanyBank> implements CompanyBankService {

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public Page<CompanyBank> findListpage(MyPage mypage) throws Exception {
    	QueryWrapper<CompanyBank> query = new QueryWrapper<CompanyBank>();
    	return (Page<CompanyBank>)baseMapper.selectPage(mypage, query);
	}

    
    
    

	
    
   

	

	
}
