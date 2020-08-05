package com.ht.biz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.RosterinfoMapper;
import com.ht.biz.service.RosterinfoService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.sys.MainRoster;
import com.ht.entity.biz.sys.Rosterinfo;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RosterInfoServiceImpl extends ServiceImpl<RosterinfoMapper, Rosterinfo> implements RosterinfoService {


    @Override
    public List<PageData> sysfindall(MyPage page) {
        return baseMapper.sysfindall(page);
    }

    @Override
    public PageData getpagedataById(int id) {
        return baseMapper.getpagedataById(id);
    }

    @Override
    public List<Rosterinfo> finbyrostervo() {
        return baseMapper.finbyrostervo();
    }
}
