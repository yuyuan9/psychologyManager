package com.ht.biz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.sys.Watermark;

import java.util.List;

public interface WatermarkMapper extends BaseMapper<Watermark> {
    List<PageData> sysfindByPage(MyPage page);
}
