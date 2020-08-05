package com.ht.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.Indexbanner;
import java.util.List;

public interface BannerService  extends IService<Indexbanner> {

    List<PageData> sysfindByPage(MyPage page);
    List<PageData> findByPage(MyPage page);

    PageData findbyId(String id);
}
