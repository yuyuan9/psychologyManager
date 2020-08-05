package com.ht.shiro.service.usertype;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.shiro.usertype.CompanyUser;
import com.ht.vo.shiro.CompanyUserVO;


public interface CompanyUserService extends IService<CompanyUser>
{
    

	 Page<PageData> findlist(MyPage mypage)throws Exception;

	CompanyUser findBycompregcode(String code);

	PageData findById(PageData pd);
}
