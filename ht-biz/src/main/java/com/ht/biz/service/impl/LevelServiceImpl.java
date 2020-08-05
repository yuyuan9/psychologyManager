package com.ht.biz.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.CityTreeMapper;
import com.ht.biz.mapper.LevelMapper;
import com.ht.biz.service.CityTreeService;
import com.ht.biz.service.LevelService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.citytree.Citytree;
import com.ht.entity.biz.sys.Level;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jied
 */
@Service
public class LevelServiceImpl extends ServiceImpl<LevelMapper, Level> implements LevelService {


	@Override
	public Page<Level> findAllList(MyPage page) {
		QueryWrapper<Level> queryWrapper = new QueryWrapper<Level>();
		PageData pd = page.getPd();
		if(StringUtils.isNotBlank(pd.getString( "keyword" )  )){
			queryWrapper.like( "name",pd.getString( "keyword" )  ).or().like( "code",pd.getString( "keyword" )  );
		}
		queryWrapper.orderByAsc("type","point");
		return (Page<Level>) baseMapper.selectPage(page, queryWrapper);
	}

	@Override
	public Level findByName(String name) {
		QueryWrapper<Level> queryWrapper = new QueryWrapper<Level>();
		queryWrapper.eq( "name",name );
		return baseMapper.selectOne(queryWrapper  );
	}
}
