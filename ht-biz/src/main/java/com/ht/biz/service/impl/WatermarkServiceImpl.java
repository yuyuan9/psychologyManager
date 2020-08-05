package com.ht.biz.service.impl;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.WatermarkMapper;
import com.ht.biz.service.WatermarkService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.sys.Watermark;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WatermarkServiceImpl extends ServiceImpl<WatermarkMapper, Watermark> implements WatermarkService {

    @Override
    public List<PageData> sysfindByPage(MyPage page) {
        return baseMapper.sysfindByPage(page);
    }

}
