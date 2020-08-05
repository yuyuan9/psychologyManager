package com.ht.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.sys.Rosterinfo;

import java.util.List;


public interface RosterinfoMapper extends BaseMapper<Rosterinfo> {
    List<PageData> sysfindall(MyPage page);

    PageData getpagedataById(int id);
    List<Rosterinfo> finbyrostervo();
}
