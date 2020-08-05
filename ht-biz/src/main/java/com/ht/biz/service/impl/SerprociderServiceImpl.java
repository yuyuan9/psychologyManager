package com.ht.biz.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ht.biz.mapper.SerprociderMapper;

import com.ht.biz.service.SerprociderService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.collection.CollectionDown;
import com.ht.entity.shiro.usertype.ServiceProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jied
 */
@Service("serviceProvider")
public class SerprociderServiceImpl extends ServiceImpl<SerprociderMapper, ServiceProvider> implements SerprociderService {


    @Override
    public PageData findById(PageData pd) {

        return baseMapper.findById(pd);
    }

    @Override
    public List<PageData> findByPage(MyPage page) {

        return  baseMapper.findByPage(page );
    }

    @Override
    public List<PageData> sysfindByPage(MyPage page) {
        return baseMapper.sysfindByPage( page );
    }

	@Override
	public void updateIsblond(PageData pd) {
		// TODO Auto-generated method stub
		baseMapper.updateIsblond(pd);
	}
}
