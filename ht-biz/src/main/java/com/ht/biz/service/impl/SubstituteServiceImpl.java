package com.ht.biz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.SubstituteMapper;
import com.ht.biz.service.SubstituteService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honey.Substitute;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SubstituteServiceImpl extends ServiceImpl<SubstituteMapper, Substitute> implements SubstituteService {

    @Override
    public List<PageData> findsubhoneybytype(MyPage page) {
        return baseMapper.findsubhoneybytype(page);
    }

    @Override
    public Substitute getendsubtitute() {
        return baseMapper.getendsubtitute();
    }

    @Override
    public Integer maxnumberadd() {
        return baseMapper.maxnumberadd();
    }
}
