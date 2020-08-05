package com.ht.biz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.shiro.usertype.BserviceProvider;
import com.ht.entity.shiro.usertype.ServiceProvider;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huitu123
 * @since 2018-01-23
 */
public interface BserprociderMapper extends BaseMapper<BserviceProvider> {

    List<PageData> sysfindByPage(MyPage page);


    PageData findById(PageData pd);
}
