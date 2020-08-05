package com.ht.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.support.geelink.entity.GeelinkResoupage;
import com.ht.commons.support.geelink.entity.Librarygelink;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.library.ResourcePage;

import java.util.List;

public interface ResoucePageService extends IService<ResourcePage> {
    MyPage<PageData> findListPage(MyPage page);

    int maxsort();

    List<GeelinkResoupage> geelinkfindall();
}
