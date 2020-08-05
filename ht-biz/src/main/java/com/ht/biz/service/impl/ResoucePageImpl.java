package com.ht.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.ResoucePageMapper;
import com.ht.biz.service.ResoucePageService;
import com.ht.commons.support.geelink.entity.GeelinkResoupage;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.library.ResourcePage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResoucePageImpl extends ServiceImpl<ResoucePageMapper,ResourcePage>implements ResoucePageService {
    @Override
    public MyPage<PageData> findListPage(MyPage page) {
        return baseMapper.findListPage(page);
    }

    @Override
    public int maxsort() {
        return baseMapper.maxsort();
    }

    @Override
    public List<GeelinkResoupage> geelinkfindall() {
        return baseMapper.geelinkfindall();
    }
}
