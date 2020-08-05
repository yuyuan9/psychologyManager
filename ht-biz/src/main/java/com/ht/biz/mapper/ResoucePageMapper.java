package com.ht.biz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.support.geelink.entity.GeelinkResoupage;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.citytree.Citytree;
import com.ht.entity.biz.library.ResourcePage;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huitu123
 * @since 2018-01-23
 */
public interface ResoucePageMapper extends BaseMapper<ResourcePage> {


    MyPage<PageData> findListPage(MyPage page);

    int maxsort();

    List<GeelinkResoupage> geelinkfindall();
}
