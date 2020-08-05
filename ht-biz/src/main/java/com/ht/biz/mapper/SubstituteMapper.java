package com.ht.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honey.Substitute;

import java.util.List;

public interface SubstituteMapper extends BaseMapper<Substitute> {
    List<PageData> findsubhoneybytype(MyPage page);
    Substitute getendsubtitute();
    Integer maxnumberadd();
}
