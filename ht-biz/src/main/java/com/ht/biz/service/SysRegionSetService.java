package com.ht.biz.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.citytree.SysRegionSet;
import com.ht.entity.shiro.regioncompany.RegionCompany;


public interface SysRegionSetService extends IService<SysRegionSet> {

	Page<SysRegionSet> findListPage(MyPage mypage);

}
