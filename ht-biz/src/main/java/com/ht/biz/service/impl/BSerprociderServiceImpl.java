package com.ht.biz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.BserprociderMapper;
import com.ht.biz.mapper.SerprociderMapper;
import com.ht.biz.service.BserprociderService;
import com.ht.biz.service.SerprociderService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.shiro.usertype.BserviceProvider;
import com.ht.entity.shiro.usertype.ServiceProvider;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jied
 */
@Service("bserviceProvider")
public class BSerprociderServiceImpl extends ServiceImpl<BserprociderMapper, BserviceProvider> implements BserprociderService {


    @Override
    public PageData findById(PageData pd) {
        return baseMapper.findById(pd);
    }


    @Override
    public List<PageData> sysfindByPage(MyPage page) {
        return baseMapper.sysfindByPage( page );
    }
}
