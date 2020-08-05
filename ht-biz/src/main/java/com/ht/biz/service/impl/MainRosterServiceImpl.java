package com.ht.biz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.MainRosterMapper;
import com.ht.biz.service.MainRosterService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.sys.MainRoster;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MainRosterServiceImpl extends ServiceImpl<MainRosterMapper, MainRoster> implements MainRosterService {


    @Override
    public List<PageData> sysfindallmainroster(MyPage page) {
         return baseMapper.sysfindallmainroster(page);
    }
}
