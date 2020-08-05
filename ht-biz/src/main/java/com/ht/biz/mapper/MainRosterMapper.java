package com.ht.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.sys.MainRoster;

import java.util.List;

public interface MainRosterMapper  extends BaseMapper<MainRoster> {
    List<PageData> sysfindallmainroster(MyPage page);
}
