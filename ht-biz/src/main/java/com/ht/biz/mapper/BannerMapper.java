package com.ht.biz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.Indexbanner;


import java.util.List;

public interface BannerMapper extends BaseMapper<Indexbanner> {
    List<PageData> sysfindByPage(MyPage page);

    List<PageData> findByPage(MyPage page);

    PageData findbyId(String id);
}
