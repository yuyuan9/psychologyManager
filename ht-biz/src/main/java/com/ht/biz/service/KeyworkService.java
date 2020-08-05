package com.ht.biz.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.sys.keywork.Keywork;

import java.util.List;


public interface KeyworkService extends IService<Keywork> {
    MyPage<PageData> syslistall(MyPage page);
    List<Keywork> findList();

    PageData getfindbyId(String id);
}
