package com.ht.biz.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.KeyworkMapper;
import com.ht.biz.mapper.SysRegionSetMapper;
import com.ht.biz.service.KeyworkService;
import com.ht.biz.service.SysRegionSetService;
import com.ht.commons.support.query.MyQueryBuilder;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.citytree.SysRegionSet;
import com.ht.entity.biz.sys.keywork.Keywork;
import com.ht.entity.shiro.regioncompany.RegionCompany;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jied
 */
@Service
public class SysRegionSetServiceImpl extends ServiceImpl<SysRegionSetMapper, SysRegionSet> implements SysRegionSetService {

	@Override
	public Page<SysRegionSet> findListPage(MyPage page) {
		//QueryWrapper query = new QueryWrapper();
		//query.like("name", page.getPd().get("name"));
		QueryWrapper query = new MyQueryBuilder(page.getPd()).builder();
		query.orderByDesc( "sort" );
		return (Page<SysRegionSet>)baseMapper.selectPage(page, query);
	}

}
