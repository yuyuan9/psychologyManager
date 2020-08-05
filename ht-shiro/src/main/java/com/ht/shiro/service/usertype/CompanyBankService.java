package com.ht.shiro.service.usertype;


import java.util.Collection;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.entity.shiro.usertype.CompanyBank;


public interface CompanyBankService extends IService<CompanyBank>
{
	public Page<CompanyBank> findListpage(MyPage mypage) throws Exception;



	
	
	
	
}
