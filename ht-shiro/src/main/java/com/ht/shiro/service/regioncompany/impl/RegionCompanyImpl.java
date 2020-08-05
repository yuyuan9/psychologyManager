package com.ht.shiro.service.regioncompany.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.commons.utils.MyPage;
import com.ht.entity.shiro.regioncompany.RegionCompany;
import com.ht.shiro.mapper.regioncompany.RegionCompanyMapper;
import com.ht.shiro.service.regioncompany.RegionCompanyService;

@Service
public class RegionCompanyImpl  extends ServiceImpl<RegionCompanyMapper, RegionCompany> implements RegionCompanyService {

	@Override
	public Page<RegionCompany> findListPage(MyPage page) throws Exception {
	/*	QueryWrapper queryWrapper = new QueryWrapper();*/
		QueryWrapper<RegionCompany> queryWrapper=new QueryWrapper<RegionCompany>();
		String name=page.getPd().getString("name");
		String province=page.getPd().getString("province");
		String city=page.getPd().getString("city");
		String area=page.getPd().getString("area");
		if(StringUtils.isNotBlank(name)) {
			queryWrapper.and(i -> i.like("name", name).or().like("leader", name)
			.or().like( "tel" ,name).or().like( "number",name ));
		}
		if(StringUtils.isNotBlank(province)) {
			queryWrapper.eq("province", province);
		}
		if(StringUtils.isNotBlank(city)) {
			queryWrapper.eq("city", city);
		}
		if(StringUtils.isNotBlank(area)) {
			queryWrapper.eq("area", area);
		}
		
		return (Page<RegionCompany>)baseMapper.selectPage(page, queryWrapper);
		
	}

	@Override
	public RegionCompany getbynumber(String number) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq( "number",number );
		return baseMapper.selectOne( queryWrapper );
	}

}
