package com.ht.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.sys.MainRoster;
import com.ht.entity.biz.sys.Rosterinfo;

import java.util.List;


public interface RosterinfoService extends IService<Rosterinfo> {


    List<PageData> sysfindall(MyPage page);

    PageData getpagedataById(int id);

    List<Rosterinfo> finbyrostervo();
}
