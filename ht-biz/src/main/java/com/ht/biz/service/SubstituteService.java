package com.ht.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honey.Substitute;

import java.util.List;

public interface  SubstituteService  extends IService<Substitute> {

    List<PageData> findsubhoneybytype(MyPage page);
    Substitute getendsubtitute();
    Integer maxnumberadd();
}
