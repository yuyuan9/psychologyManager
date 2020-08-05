package com.ht.biz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.BannerMapper;
import com.ht.biz.service.BannerService;

import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.Indexbanner;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Indexbanner> implements BannerService {

    @Override
    public List<PageData> sysfindByPage(MyPage page) {
        return baseMapper.sysfindByPage(page);
    }

    @Override
    public List<PageData> findByPage(MyPage page) {
        return baseMapper.findByPage(page);
    }

    @Override
    public PageData findbyId(String id) {
        return baseMapper.findbyId(id);
    }
}
